package com.example.profily.Notifications;


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

import com.example.profily.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotificationsListAdapter adapter;
    private ImageView loadMoreNotificationBtn;

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

        adapter = new NotificationsListAdapter();
        recyclerView.setAdapter(adapter);

        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        notificationsViewModel.getNotificationsList().observe(this, list -> adapter.setNotifications(list));
        notificationsViewModel.getNotifications();

        return view;
    }

}
