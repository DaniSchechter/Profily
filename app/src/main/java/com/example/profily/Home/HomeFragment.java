package com.example.profily.Home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.profily.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PostListAdapter adapter;
    private ImageView loadMorePostsBtn;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PostListAdapter();
        recyclerView.setAdapter(adapter);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        //TODO add logic for something like pagination
        homeViewModel.getPostsList().observe(this, list -> adapter.setPosts(list) );

        loadMorePostsBtn = view.findViewById(R.id.add_more_posts_btn);

        loadMorePostsBtn.setOnClickListener(viewOnClick -> {
                    homeViewModel.loadMorePosts().observe(
                            HomeFragment.this, list -> adapter.setPosts(list)
                    );
                }

        );

        return view;
    }

}
