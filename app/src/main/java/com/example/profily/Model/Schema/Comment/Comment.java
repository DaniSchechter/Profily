package com.example.profily.Model.Schema.Comment;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;
import com.example.profily.Model.Schema.User.User;
import com.example.profily.Post.Post;

import java.util.Date;

@Entity(tableName = "comments")
@TypeConverters(Converters.class)
public class Comment {

    @PrimaryKey()
    @NonNull
    private final String commentId;

    private String content;

    private Date actionDateTime;

    // FK References
    @ForeignKey(entity = User.class, parentColumns = {"commentId"}, childColumns = {"userId"})
    private String userId;

    @ForeignKey(entity = Post.class, parentColumns = {"commentId"}, childColumns = {"postId"})
    private String postId;

    // Control Fields
    private Boolean wasDeleted;



    public Comment(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getActionDateTime() {
        return actionDateTime;
    }

    public void setActionDateTime(Date actionDateTime) {
        this.actionDateTime = actionDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Boolean getWasDeleted() {
        return wasDeleted;
    }

    public void setWasDeleted(Boolean wasDeleted) {
        this.wasDeleted = wasDeleted;
    }
}

