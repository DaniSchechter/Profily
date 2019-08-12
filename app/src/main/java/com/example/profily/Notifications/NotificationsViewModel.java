package com.example.profily.Notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Notification.Notification;

import java.util.List;

public class NotificationsViewModel extends ViewModel {
    private MutableLiveData<List<Notification>> notificationsListLiveData;
    private String currentUser;
    private static final int delta = 10;
    private int numOfNotifications = 10;

    public NotificationsViewModel() {
        notificationsListLiveData = new MutableLiveData<>();
        currentUser = Model.instance.getConnectedUserId();
    }

    public void getNotifications(){

        // Get all notifications async
        Model.instance.getAllNotifications( currentUser, numOfNotifications, notificationsList ->
                this.notificationsListLiveData.setValue(notificationsList));
    }

    public LiveData<List<Notification>> getNotificationsList() {
        return this.notificationsListLiveData;
    }

    public void loadMoreNotifications() {
        this.numOfNotifications += delta;
        Model.instance.getAllNotifications( currentUser, numOfNotifications, notificationsList ->
                this.notificationsListLiveData.setValue(notificationsList));
    }
}