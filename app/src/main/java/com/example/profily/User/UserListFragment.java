package com.example.profily.User;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.profily.R;
import com.example.profily.Schema.User;
import com.example.profily.User.UserListAdapter;

import java.util.Locale;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UserListAdapter adapter;
    private Vector<User> searchedUsers = new Vector<>(); //TODO remove

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO remove
        // -------------------------------------
        User u1 = new User();
        u1.setId("1");
        u1.setUsername("Alex");
        // -------------------------------------
        User u2 = new User();
        u2.setId("2");
        u2.setUsername("Danon");
        // -------------------------------------
        User u3 = new User();
        u3.setId("3");
        u3.setUsername("Teleh");

        searchedUsers.add(u1);
        searchedUsers.add(u2);
        searchedUsers.add(u3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.user_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UserListAdapter(searchedUsers);
        recyclerView.setAdapter(adapter);

        return view;
    }



}
