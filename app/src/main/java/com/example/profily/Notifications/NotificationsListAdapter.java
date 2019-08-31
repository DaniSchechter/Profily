package com.example.profily.Notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profily.Model.Schema.Notification.NotificationWrapper;
import com.example.profily.R;
import com.example.profily.Model.Schema.Action.Action;
import com.example.profily.Model.Schema.Notification.Notification;
import com.example.profily.Utils.DateTimeUtils;

import java.util.LinkedList;
import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.NotificationRowViewHolder>  {

    private List<NotificationWrapper> notificationsList = new LinkedList<>();

    @NonNull
    @Override
    public NotificationRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row, parent, false);
        return new NotificationRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRowViewHolder holder, int position) {
        NotificationWrapper notification = notificationsList.get(position);
        holder.bind(notification);
    }

    void setNotifications(List<NotificationWrapper> notificationsList){
        this.notificationsList = notificationsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    static class NotificationRowViewHolder extends RecyclerView.ViewHolder {

        ImageView triggeringUserImage;
        ImageView effectedUserImage;
        TextView  triggeringUserUsername;
        TextView  description;
        TextView  actionElapsedTime;

        public NotificationRowViewHolder(@NonNull View itemView) {

            super(itemView);

            triggeringUserImage = itemView.findViewById(R.id.notification_trigger_user_image);
            effectedUserImage = itemView.findViewById(R.id.notifications_effected_user_image);
            triggeringUserUsername = itemView.findViewById(R.id.notification_trigger_user_username);
            description = itemView.findViewById(R.id.notification_description);
            actionElapsedTime = itemView.findViewById(R.id.notifications_elapsed_time);
        }

        public void bind(NotificationWrapper notification){
            triggeringUserUsername.setText(notification.usernameForCurrentnotification()); // TODO change
            description.setText(notification.notification.getAction().getDescription());
            actionElapsedTime.setText(DateTimeUtils.getFormattedElapsedTime(notification.notification.getActionDateTime()));


            // Navigate to the effected post, if it is not a subscription action
            effectedUserImage.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    NotificationsFragmentDirections.actionNotificationsFragmentToPost(
                            notification.notification.getEffectedPostId()
                    )
                )
            );

            // Navigate to Triggering user's profile
            triggeringUserImage.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    NotificationsFragmentDirections.actionNotificationsFragmentToProfileFragment(
                        notification.notification.getTriggeringUserId()
                    )
                )
            );

            triggeringUserUsername.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    NotificationsFragmentDirections.actionNotificationsFragmentToProfileFragment(
                            notification.notification.getTriggeringUserId()
                    )
                )
            );
        }
    }
}
