package com.example.profily.Model.Schema.Comment;

public class CommentWrapper {
    public Comment comment;
    private String usernameForCurrentcomment;

    public CommentWrapper(Comment comment, String usernameForCurrentcomment) {
        this.comment = comment;
        this.usernameForCurrentcomment = usernameForCurrentcomment;
    }

    public void setUsernameForCurrentcomment(String usernameForCurrentcomment){
        this.usernameForCurrentcomment = usernameForCurrentcomment;
    }

    public String usernameForCurrentcomment(){ return usernameForCurrentcomment; }
}