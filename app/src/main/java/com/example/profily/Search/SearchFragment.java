package com.example.profily.Search;


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
import com.example.profily.Model.Schema.User.User;
import com.example.profily.User.UserListAdapter;

import java.util.Locale;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UserListAdapter adapter;
    private Vector<User> searchedUsers = new Vector<>(); //TODO remove

    private EditText searchBox;

    public SearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.user_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UserListAdapter(searchedUsers);
        recyclerView.setAdapter(adapter);

        searchBox = view.findViewById(R.id.search_text);
        setSearchTextListener();

        return view;
    }

    private void setSearchTextListener(){
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = searchBox.getText().toString().toLowerCase(Locale.getDefault());
                searchForMatch(text);
            }
        });
    }


    private void searchForMatch(String keyword){
        //update the users list view
        if(keyword.length() ==0){

        }
        else{
            Vector <User> newUsers = new Vector<>();
            for (User user: searchedUsers)
            {
                if (user.getUsername().contains(keyword)){
                    updateUsersList();
                }
            }

            searchedUsers = newUsers;
        }
    }

    private void updateUsersList(){

        adapter = new UserListAdapter(searchedUsers);

        recyclerView.setAdapter(adapter);

    }

}
