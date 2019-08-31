package com.example.profily.Notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Notification.Notification;
import com.example.profily.Model.Schema.Notification.NotificationWrapper;

import java.util.LinkedList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {
    private MutableLiveData<List<NotificationWrapper>> notificationsListLiveData;
    private List<NotificationWrapper> notificationsWrappersList;
    private String currentUser;

    public NotificationsViewModel() {
        notificationsListLiveData = new MutableLiveData<>();
        notificationsWrappersList = new LinkedList<>();
        currentUser = Model.instance.getConnectedUserId();
    }

    public void getNotifications(){

        // Get all notifications async
        Model.instance.getAllNotifications( currentUser, notificationsList ->{
            notificationsWrappersList.clear();

            for(Notification notification: notificationsList) {

                // Initialize the Notification Wrapper with the notification itself
                NotificationWrapper notificationWrapper = new NotificationWrapper(notification, null);
                notificationsWrappersList.add(notificationWrapper);

                // Get the username of the comment
                Model.instance.getUserNameById(notification.getTriggeringUserId(), username ->  {
                    notificationWrapper.setUsernameForCurrentnotification(username);
                    this.notificationsListLiveData.setValue(notificationsWrappersList);
                });
            }
        });
    }

    public LiveData<List<NotificationWrapper>> getNotificationsList() {
        return this.notificationsListLiveData;
    }
}
