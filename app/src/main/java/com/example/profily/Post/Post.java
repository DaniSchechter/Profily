package com.example.profily.Post;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.profily.Home.HomeViewModel;
import com.example.profily.Profile.ProfileViewModel;
import com.example.profily.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Post extends Fragment {

    private ImageView profileImage;
    private ImageView mainImage;
    private ImageView likedImage;
    private ImageView commentImage;
    private ImageView editPostBtn;
    private ImageView deletePostBtn;
    private TextView username;
    private TextView numOfLikes;
    private TextView caption;
    private TextView comments;

    private ProfileViewModel profileViewModel;

    private PostViewModel postViewModel;

    private final String cRedImageTag = "red_image_tag";
    private final String cWhiteImageTag = "white_image_tag";

    public Post() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post, container, false);


        profileImage = view.findViewById(R.id.post_profile_image);
        mainImage = view.findViewById(R.id.post_main_image);
        username = view.findViewById(R.id.post_username);
        likedImage = view.findViewById(R.id.post_like_image);
        numOfLikes = view.findViewById(R.id.post_num_of_likes);
        caption = view.findViewById(R.id.post_caption);
        comments = view.findViewById(R.id.post_comments_link);
        editPostBtn = view.findViewById(R.id.post_edit_post_btn);
        deletePostBtn = view.findViewById(R.id.post_delete_post_btn);

        // Make sure that a parameter PostId was passed
        if (getArguments() == null || getArguments().size()==0)
        {
            return view;
        }

        String postId = PostArgs.fromBundle(getArguments()).getPostId();

        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        postViewModel.getPost(postId);

        postViewModel.getPost().observe(this, post->{

            caption.setText(post.post.getCaption());
            numOfLikes.setText("" + post.getNumOfLikes() + " likes");
            username.setText(post.getUser().getUsername());
            comments.setText("View comments");
            comments.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            PostDirections.actionPostToCommentsFragment(
                                    post.post.getPostId()
                            )
                    )
            );
            Glide.with(mainImage.getContext()).load(post.post.getImageURL()).into(mainImage);

            if(!post.getUser().getProfileImageURL().isEmpty()){
                Glide.with(profileImage.getContext()).load(post.getUser().getProfileImageURL()).into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.profile);
            }

            if(post.likeIdForCurrentUser() == null)
            {
                likedImage.setImageResource(R.drawable.ic_heart_white);
                likedImage.setTag(cWhiteImageTag);
            } else if( ! String.valueOf(likedImage.getTag()).equals(cRedImageTag)){
                likedImage.setImageResource(R.drawable.ic_heart_red);
                likedImage.setTag(cRedImageTag);
            }

            likedImage.setOnClickListener(likedImage -> HomeViewModel.likeToggle(post));

            if (HomeViewModel.checkEdit(post.post.getUserCreatorId())){
                editPostBtn.setVisibility(View.VISIBLE);
                deletePostBtn.setVisibility(View.VISIBLE);
            }
            else{
                editPostBtn.setVisibility(View.INVISIBLE);
                deletePostBtn.setVisibility(View.INVISIBLE);
            }

            editPostBtn.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            PostDirections.actionPostToEditPostFragment(
                                    post.post.getPostId()
                            )
                    )
            );

            deletePostBtn.setOnClickListener(viewOnClick -> {
                post.post.setWasDeleted(true);
                PostViewModel.updatePost(post.post, null);
                getFragmentManager().popBackStack();
            });
        });

        return view;
    }

}
