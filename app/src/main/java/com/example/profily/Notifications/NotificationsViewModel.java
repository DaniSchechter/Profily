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

    public NotificationsViewModel() {
        notificationsListLiveData = new MutableLiveData<>();
        currentUser = Model.instance.getConnectedUserId();
    }

    public void getNotifications(){

        // Get all notifications async
        Model.instance.getAllNotifications( currentUser, notificationsList ->
                this.notificationsListLiveData.setValue(notificationsList));
    }

    public LiveData<List<Notification>> getNotificationsList() {
        return this.notificationsListLiveData;
    }
}
