package com.example.profily.Profile;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Vector<String> followingList = new Vector<>();
    private Vector<String> followersList = new Vector<>();

    private ImageGridAdapter adapter;
    private RecyclerView recyclerView;


    //Profile vars
    private TextView profileUsername,profileDescription;
    private ImageView profileImage;
    private Button editProfileBtn;
    private ImageButton logoutButton;
    private ImageView loadMorePostsBtn;

    //counting vars
    private TextView profileNumOfFollowers;
    private TextView profileNumOfFollowing;
    private TextView profileNumOfPosts;
    private GridLayoutManager layoutManager;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileUsername = view.findViewById(R.id.profile_username);
        profileDescription = view.findViewById(R.id.profile_description);
        profileNumOfFollowers = view.findViewById(R.id.profile_followers_count);
        profileNumOfFollowing = view.findViewById(R.id.profile_following_count);
        profileNumOfPosts = view.findViewById(R.id.profile_posts_count);
        profileImage = view.findViewById(R.id.profile_image);
        editProfileBtn = view.findViewById(R.id.profile_edit_profile_btn);
        logoutButton = view.findViewById(R.id.profile_logout);



        profileNumOfFollowers.setText("" + followersList.size());
        profileNumOfFollowing.setText("" + followingList.size());


        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.isAutoMeasureEnabled();
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ImageGridAdapter(postsList);
        recyclerView.setAdapter(adapter);

        String userId = null;
        if (getArguments() != null && getArguments().size()!=0)
        {
            userId = ProfileFragmentArgs.fromBundle(getArguments()).getUserId();
            Log.d("TAGUSER", userId);
        }

        if (userId == null)
        {
            userId = Model.instance.getConnectedUserId();
        }

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.getPosts(userId);
        profileViewModel.populateUserDetails(userId);

        logoutButton.setOnClickListener(view1 -> {
            Model.instance.logOut();
            ((MainActivity)getActivity()).displayAuthenticationActivity(false);
        });

        profileViewModel.getUser().observe(this, userData->{
            profileUsername.setText(userData.getUsername());
            profileDescription.setText(userData.getDescription());
        });

        //TODO add logic for something like pagination
        profileViewModel.getPostsList().observe(this, list -> {
            adapter.setPosts(list);
            profileNumOfPosts.setText("" + list.size());
        } );

        loadMorePostsBtn = view.findViewById(R.id.add_more_posts_to_profile_btn);

        String finaluserId = userId;
        loadMorePostsBtn.setOnClickListener(viewOnClick -> profileViewModel.loadMorePosts(finaluserId));



        return view;
    }



}
