package com.example.profily.Model.Schema.Post;

import com.example.profily.Model.Schema.User.User;

public class PostLikeWrapper {
    public Post post;
    private String likeIdForCurrentUser;
    private User user;
    private int numOfLikes = 0;

    public PostLikeWrapper(Post post, String likeIdForCurrentUser, User user) {
        this.post = post;
        this.likeIdForCurrentUser = likeIdForCurrentUser;
        this.user = user;
    }

    public void setLikeIdForCurrentUser(String likeIdForCurrentUser) {
        this.likeIdForCurrentUser = likeIdForCurrentUser;
    }
    public String likeIdForCurrentUser(){ return likeIdForCurrentUser; }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){ return user; }

    public void setNumOfLikes(int numOfLikes){
        this.numOfLikes = numOfLikes;
    }

    public int getNumOfLikes(){ return this.numOfLikes; }
}
