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

import com.example.profily.Home.HomeFragmentDirections;
import com.example.profily.Model.Schema.Post.PostAsyncDao;
import com.example.profily.Profile.ProfileViewModel;
import com.example.profily.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Post extends Fragment {

    private ImageView profileImage;
    private ImageView mainImage;
    private TextView username;
    private ImageView likedImage;
    private ImageView commentImage;
    private TextView numOfLikes;
    private TextView caption;
    private TextView comments;

    private ProfileViewModel profileViewModel;


    private PostViewModel postViewModel;


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
        commentImage = view.findViewById(R.id.post_comment_image);
        numOfLikes = view.findViewById(R.id.post_num_of_likes);
        caption = view.findViewById(R.id.post_caption);
        comments = view.findViewById(R.id.post_comments_link);


        String postId = null;
        if (getArguments() != null && getArguments().size()!=0)
        {
            postId = PostArgs.fromBundle(getArguments()).getPostId();
        }
        if (postId != null)
        {
            postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
            postViewModel.getPost(postId);

            postViewModel.getPost().observe(this, post->{
                caption.setText(post.getCaption());
                numOfLikes.setText("" + 0);
                PostAsyncDao.getUserNameById(post.getUserCreatorId(), name -> {
                    username.setText(name);
                });
                comments.setText("View all " + post.getCommentsList().size() + " comments");
                comments.setOnClickListener(
                        Navigation.createNavigateOnClickListener(
                                PostDirections.actionPostToCommentsFragment(
                                        post.getPostId()
                                )
                        )
                );
            });
        }

        return view;
    }

}
