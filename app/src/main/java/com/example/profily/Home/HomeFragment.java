package com.example.profily.Home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profily.R;
import com.example.profily.Schema.Post;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PostListAdapter adapter;
    private Vector<Post> posts = new Vector<>(); //TODO remove

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO remove
        for (int i=0; i<10; i++)
        {
            Post post = new Post();
            post.setCaption("Caption number " + i);
            List<String> commentIds = new LinkedList<>();
            commentIds.add("1");
            commentIds.add("2");
            commentIds.add("2");
            post.setCommentsList(commentIds);
            List<String> likes = new LinkedList<>();
            likes.add("11");
            likes.add("22");
            likes.add("23");
            post.setLikedUsersList(likes);
            post.setUserCreatorId("" + i);
            post.setId("" + i);

            posts.add(post);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PostListAdapter(posts);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
