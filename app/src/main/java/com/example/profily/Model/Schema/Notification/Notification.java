package com.example.profily.Model.Schema.Notification;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;
import com.example.profily.Model.Schema.Action.Action;
import com.example.profily.Model.Schema.User.User;
import com.example.profily.Post.Post;

import java.util.Date;

@Entity(tableName = "notifications")
@TypeConverters(Converters.class)
public class Notification {

    @PrimaryKey()
    @NonNull
    private final String notificationId;

    @Embedded
    private Action action;

    @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"triggeringUserId"})
    private String triggeringUserId;

    @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"effectedUserId"})
    private String effectedUserId;

    @ForeignKey(entity = Post.class, parentColumns = {"postId"}, childColumns = {"effectedPostId"})
    private String effectedPostId;

    private Date actionDateTime;



    public Notification(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationId() {
        return notificationId;
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
}

