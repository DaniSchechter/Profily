package com.example.profily.Comments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profily.R;
import com.example.profily.Model.Schema.Comment.Comment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CommentsListAdapter adapter;
    private Vector<Comment> comments = new Vector<>(); //TODO remove

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO remove
        // -------------------------------------
        Comment c1 = new Comment();
        c1.setActionDateTime(new GregorianCalendar(2017, 6, 21, 8, 23, 45).getTime());
        c1.setContent("comment1");
        c1.setUserId("1");
        comments.add(c1);

        // -------------------------------------
        Comment c2 = new Comment();
        c2.setActionDateTime(new GregorianCalendar(2019, Calendar.AUGUST, 6, 20, 36, 0).getTime());
        c2.setContent("comment2");
        c2.setUserId("2");
        comments.add(c2);


        // -------------------------------------
        Comment c3 = new Comment();
        c3.setActionDateTime(new GregorianCalendar(2019, Calendar.AUGUST, 1, 0, 0, 0).getTime());
        c3.setContent("comment3");
        c3.setUserId("3");
        comments.add(c3);
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

        if (getArguments()!= null && getArguments().size()!=0)
        {
            String postId = CommentsFragmentArgs.fromBundle(getArguments()).getPostId();
        }


        return view;
    }

}
