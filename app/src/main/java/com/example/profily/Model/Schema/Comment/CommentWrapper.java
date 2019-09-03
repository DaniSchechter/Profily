package com.example.profily.Model.Schema.Comment;

import com.example.profily.Model.Schema.User.User;

public class CommentWrapper {
    public Comment comment;
    private User user;

    public CommentWrapper(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){ return user; }
}