package com.example.profily.Model.Schema.Comment;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;
import com.example.profily.Model.Schema.User.User;
import com.example.profily.Model.Schema.Post.Post;

import java.util.Date;

@Entity(tableName = "comments")
@TypeConverters(Converters.class)
public class Comment {

    @PrimaryKey()
    @NonNull
    private String commentId;

    private String content;

    // FK References
    @ForeignKey(entity = User.class, parentColumns = {"commentId"}, childColumns = {"userId"})
    private String userCreatorId;

    @ForeignKey(entity = Post.class, parentColumns = {"commentId"}, childColumns = {"postId"})
    private String postId;

    // Control Fields
    private Boolean wasDeleted;

    private Date createdDate;



    public Comment() {
    }

    @Ignore
    public Comment(@NonNull String commentId, String content,
                   String userCreatorId, String postId, Boolean wasDeleted, Date createdDate) {
        this.commentId = commentId;
        this.content = content;
        this.userCreatorId = userCreatorId;
        this.postId = postId;
        this.wasDeleted = wasDeleted;
        this.createdDate = createdDate;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(@NonNull String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userCreatorId;
    }

    public void setUserId(String userCreatorId) {
        this.userCreatorId = userCreatorId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(String userCreatorId) {
        this.userCreatorId = userCreatorId;
    }
}

