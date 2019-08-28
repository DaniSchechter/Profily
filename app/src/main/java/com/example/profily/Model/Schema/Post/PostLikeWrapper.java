package com.example.profily.Model.Schema.Post;

import com.example.profily.Model.Schema.Post.Post;

public class PostLikeWrapper {
    public Post post;
    private String likeIdForCurrentUser;

    public PostLikeWrapper(Post post, String likeIdForCurrentUser) {
        this.post = post;
        this.likeIdForCurrentUser = likeIdForCurrentUser;
    }

    public void setLikeIdForCurrentUser(String likeIdForCurrentUser) {
        this.likeIdForCurrentUser = likeIdForCurrentUser;
    }
    public String likeIdForCurrentUser(){ return likeIdForCurrentUser; };
}
