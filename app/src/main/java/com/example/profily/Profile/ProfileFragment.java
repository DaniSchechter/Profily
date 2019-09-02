package com.example.profily.Profile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.profily.MainActivity;
import com.example.profily.Model.Model;
import com.example.profily.R;
import com.example.profily.Model.Schema.Post.Post;

import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Vector<Post> postsList = new Vector<>();
    private ProfileViewModel profileViewModel;

    private ImageGridAdapter adapter;
    private RecyclerView recyclerView;


    //Profile vars
    private TextView profileUsername,profileDescription;
    private ImageView profileImage;
    private Button editProfileBtn;
    private ImageButton logoutButton;
    private ProgressBar progressBar;
    private ProgressBar profileImageProgressBar;

    //counting vars
    private TextView profileNumOfPosts;
    private GridLayoutManager layoutManager;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileUsername = view.findViewById(R.id.profile_username);
        profileDescription = view.findViewById(R.id.profile_description);
        profileNumOfPosts = view.findViewById(R.id.profile_posts_count);
        profileImage = view.findViewById(R.id.profile_image);
        editProfileBtn = view.findViewById(R.id.profile_edit_profile_btn);
        logoutButton = view.findViewById(R.id.profile_logout);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        profileImageProgressBar = view.findViewById(R.id.profile_image_progress_bar);

        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.isAutoMeasureEnabled();
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ImageGridAdapter();
        recyclerView.setAdapter(adapter);

        String userId = null;
        if (getArguments() != null && getArguments().size()!=0)
        {
            userId = ProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        }
        if (userId == null)
        {
            userId = Model.instance.getConnectedUserId();
        }

        // Restrict edit operations
        if (userId.equals(Model.instance.getConnectedUserId())){
            // Display the edit button
            editProfileBtn.setVisibility(View.VISIBLE);
        }
        else{
            editProfileBtn.setVisibility(View.INVISIBLE);
        }


        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        progressBar.setVisibility(View.VISIBLE);

        profileViewModel.populateUserDetails(userId);

        logoutButton.setOnClickListener(view1 -> {
            Model.instance.logOut();
            ((MainActivity)getActivity()).displayAuthenticationActivity(true);
        });

        editProfileBtn.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(userId)
                )
        );

        profileImageProgressBar.setVisibility(View.VISIBLE);
        profileViewModel.getUser().observe(this, userData -> {
            profileUsername.setText(userData.getUsername());
            profileDescription.setText(userData.getDescription());
            progressBar.setVisibility(View.GONE);
            if(!profileViewModel.isImageLoading()) {
                if (!userData.getProfileImageURL().isEmpty()) {
                    Glide.with(this).load(userData.getProfileImageURL()).into(profileImage);
                } else {
                    profileImage.setImageResource(R.drawable.profile);
                }
                profileImageProgressBar.setVisibility(View.GONE);
            }
        });

        profileViewModel.getPostsList().observe(this, list -> {
            adapter.setPosts(list);
            profileNumOfPosts.setText("" + list.size());
            progressBar.setVisibility(View.GONE);
        } );
        profileViewModel.getPosts(userId);


        return view;
    }
}
