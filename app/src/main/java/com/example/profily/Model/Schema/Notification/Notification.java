package com.example.profily.Model.Schema.Notification;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;
import com.example.profily.Model.Schema.Action.Action;
import com.example.profily.Model.Schema.Action.CommentAction;
import com.example.profily.Model.Schema.Action.LikeAction;
import com.example.profily.Model.Schema.Action.SubscriptionAction;
import com.example.profily.Model.Schema.User.User;
import com.example.profily.Post.Post;

import java.util.Date;
import java.util.HashMap;

@Entity(tableName = "notifications")
@TypeConverters(Converters.class)
public class Notification {

    @PrimaryKey()
    @NonNull
    private String notificationId;

    @Embedded
    private Action action;

    @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"triggeringUserId"})
    private String triggeringUserId;

    @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"effectedUserId"})
    private String effectedUserId;

    @ForeignKey(entity = Post.class, parentColumns = {"postId"}, childColumns = {"effectedPostId"})
    private String effectedPostId;

    private Date actionDateTime;

    private Boolean wasDeleted;

    @Ignore
    public Notification(@NonNull String notificationId, Action action, String triggeringUserId, String effectedUserId, String effectedPostId, Date actionDateTime, Boolean wasDeleted) {
        this.notificationId = notificationId;
        this.action = action;
        this.triggeringUserId = triggeringUserId;
        this.effectedUserId = effectedUserId;
        this.effectedPostId = effectedPostId;
        this.actionDateTime = actionDateTime;
        this.wasDeleted = wasDeleted;
    }

    public Notification() { }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(@NonNull String notificationId) {
        this.notificationId = notificationId;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getTriggeringUserId() {
        return triggeringUserId;
    }

    public void setTriggeringUserId(String triggeringUserId) {
        this.triggeringUserId = triggeringUserId;
    }

    public String getEffectedUserId() {
        return this.effectedUserId;
    }

    public void setEffectedUserId(String effectedUserId) {
        this.effectedUserId = effectedUserId;
    }

    public String getEffectedPostId() {
        return effectedPostId;
    }

    public void setEffectedPostId(String effectedPostId) {
        this.effectedPostId = effectedPostId;
    }

    public Date getActionDateTime() {
        return actionDateTime;
    }

    public void setActionDateTime(Date actionDateTime) {
        this.actionDateTime = actionDateTime;
    }

    public Boolean getWasDeleted() {
        return wasDeleted;
    }

    public void setWasDeleted(Boolean wasDeleted) {
        this.wasDeleted = wasDeleted;
    }
}

