package com.example.profily.Model.Schema.Notification;

public class NotificationWrapper {
    public Notification notification;
    private String usernameForCurrentnotification;

    public NotificationWrapper(Notification notification, String usernameForCurrentNotification) {
        this.notification = notification;
        this.usernameForCurrentnotification = usernameForCurrentNotification;
    }

    public void setUsernameForCurrentnotification(String usernameForCurrentnotification){
        this.usernameForCurrentnotification = usernameForCurrentnotification;
    }

    public String usernameForCurrentnotification(){ return usernameForCurrentnotification; }
}