package com.example.profily.Model.Schema.Notification;

import com.example.profily.Model.Schema.User.User;

public class NotificationWrapper {
    public Notification notification;
    private User userForCurrentnotification;

    public String getUserImgURL() {
        return userImgURL;
    }

    public void setUserImgURL(String userImgURL) {
        this.userImgURL = userImgURL;
    }

    private String userImgURL;

    public NotificationWrapper(Notification notification, User userForCurrentnotification) {
        this.notification = notification;
        this.userForCurrentnotification = userForCurrentnotification;
    }

    public void setUserForCurrentnotification(User userForCurrentnotification){
        this.userForCurrentnotification = userForCurrentnotification;
    }

    public User getUserForCurrentnotification(){ return userForCurrentnotification; }
}