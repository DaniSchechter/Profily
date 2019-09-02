package com.example.profily.Model.Schema.Notification;

import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.User.User;

public class NotificationWrapper {
    public Notification notification;
    private User userForCurrentnotification;
    private Post effectedPost;

    public NotificationWrapper(Notification notification, User userForCurrentnotification) {
        this.notification = notification;
        this.userForCurrentnotification = userForCurrentnotification;
    }

    public void setUserForCurrentnotification(User userForCurrentnotification){
        this.userForCurrentnotification = userForCurrentnotification;
    }

    public User getUserForCurrentnotification(){ return userForCurrentnotification; }

    public Post getEffectedPost() {
        return effectedPost;
    }

    public void setEffectedPost(Post effectedPost) {
        this.effectedPost = effectedPost;
    }
}