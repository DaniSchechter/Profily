package com.example.profily.Profile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.profily.R;
import com.example.profily.Schema.Post;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Vector<Post> postsList = new Vector<>();
    private Vector<String> followingList = new Vector<>();
    private Vector<String> followersList = new Vector<>();

    private ImageGridAdapter adapter;
    private RecyclerView recyclerView;


    //Profile vars
    private TextView profileUsername,profileDescription;
    private ImageView profileImage;
    private Button editProfileBtn;


    //counting vars
    private TextView profileNumOfFollowers;
    private TextView profileNumOfFollowing;
    private TextView profileNumOfPosts;
    private GridLayoutManager layoutManager;


    public ProfileFragment() {
        // Required empty public constructor
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

        for(int i=0; i<6; i++)
        {
            followingList.add(i + "");
            followersList.add("" + (i+20));
        }

        profileNumOfFollowers.setText("" + followersList.size());
        profileNumOfFollowing.setText("" + followingList.size());
        profileNumOfPosts.setText("" + postsList.size());
        profileUsername.setText("Alex");
        profileDescription.setText("Alex - some text");

        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.isAutoMeasureEnabled();
        recyclerView.setLayoutManager(layoutManager);

        //TODO remove
        for (int i=0; i<10; i++)
        {
            Post post = new Post();
            post.setId("" + i);
            post.setCaption("Caption number " + i);
            List<String> commentIds = new LinkedList<String>();
            commentIds.add("1");
            commentIds.add("2");
            commentIds.add("2");
            post.setCommentsList(commentIds);
            List<String> likes = new LinkedList<String>();
            likes.add("11");
            likes.add("22");
            likes.add("23");
            post.setLikedUsersList(likes);
            post.setUserCreatorId("123123123");
            postsList.add(post);
        }

        profileNumOfPosts.setText("" + postsList.size());

        adapter = new ImageGridAdapter(postsList);
        recyclerView.setAdapter(adapter);

        if (getArguments() != null && getArguments().size()!=0)
        {
            String userId = ProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        }


        return view;
    }



}
