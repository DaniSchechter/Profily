package com.example.profily.Model.Schema.Post;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;
import com.example.profily.Model.Schema.User.User;

import java.util.List;

@Entity(tableName = "posts")
@TypeConverters(Converters.class)
public class Post {

    @PrimaryKey()
    @NonNull
    private String postId;

    @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"userCreatorId"})
    private String userCreatorId;

    private String imageURL;
    private String caption;

    // FK References
    private List<String> likedUsersList;
    private List<String> commentsList;

    // Control Fields
    private Boolean wasDeleted;

    @Ignore
    public Post(String postId, String userCreatorId, String imageURL, String caption, List<String> likedUsersList, List<String> commentsList, Boolean wasDeleted) {
        this.postId = postId;
        this.userCreatorId = userCreatorId;
        this.imageURL = imageURL;
        this.caption = caption;
        this.likedUsersList = likedUsersList;
        this.commentsList = commentsList;
        this.wasDeleted = wasDeleted;
    }

    public Post(){}

    public void setPostId(@NonNull String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(String userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<String> getLikedUsersList() {
        return likedUsersList;
    }

    public void setLikedUsersList(List<String> likedUsersList) {
        this.likedUsersList = likedUsersList;
    }

    public List<String> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<String> commentsList) {
        this.commentsList = commentsList;
    }

    public Boolean getWasDeleted() {
        return wasDeleted;
    }

    public void setWasDeleted(Boolean wasDeleted) {
        this.wasDeleted = wasDeleted;
    }
}
