package com.example.profily.Model.Schema.Post;

import java.util.List;

public class Post {
    private String id;
    private String userCreatorId;
    private String imageURL;
    private String caption;

    // FK References
    private List<String> likedUsersList;
    private List<String> commentsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
