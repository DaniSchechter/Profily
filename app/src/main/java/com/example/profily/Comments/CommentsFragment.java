package com.example.profily.Comments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.profily.Home.HomeViewModel;
import com.example.profily.R;
import com.example.profily.Model.Schema.Comment.Comment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {

    private CommentsViewModel commentsViewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CommentsListAdapter adapter;
    private Vector<Comment> comments = new Vector<>(); //TODO remove
    private ImageView loadMoreCommentsBtn;

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        recyclerView = view.findViewById(R.id.comments_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommentsListAdapter(comments);
        recyclerView.setAdapter(adapter);

        String postId = null;

        if (getArguments()!= null && getArguments().size()!=0)
        {
            postId = CommentsFragmentArgs.fromBundle(getArguments()).getPostId();
        }

        if (postId != null)
        {

            commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
            commentsViewModel.getComments(postId);

            //TODO add logic for something like pagination
            commentsViewModel.getCommentsList().observe(this, list -> adapter.setComments(list) );

            loadMoreCommentsBtn = view.findViewById(R.id.add_more_comments_btn);

            String finalPostId = postId;
            loadMoreCommentsBtn.setOnClickListener(viewOnClick -> commentsViewModel.loadMoreComments(finalPostId));
        }


        return view;
    }

}
