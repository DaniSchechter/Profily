package com.example.profily.Notifications;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profily.R;
import com.example.profily.Model.Schema.Action.CommentAction;
import com.example.profily.Model.Schema.Action.LikeAction;
import com.example.profily.Model.Schema.Action.SubscriptionAction;
import com.example.profily.Model.Schema.Notification.Notification;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotificationsListAdapter adapter;
    private Vector<Notification> notifications = new Vector<>(); //TODO remove

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.notifications_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotificationsListAdapter(notifications);
        recyclerView.setAdapter(adapter);


        return view;
    }

}
