package com.example.profily.Model.Schema.Post;

public class PostLikeWrapper {
    public Post post;
    private String likeIdForCurrentUser;
    private String usernameForCurrentUser;
    private int numOfLikes = 0;

    public PostLikeWrapper(Post post, String likeIdForCurrentUser, String usernameForCurrentUser) {
        this.post = post;
        this.likeIdForCurrentUser = likeIdForCurrentUser;
        this.usernameForCurrentUser = usernameForCurrentUser;
    }

    public void setLikeIdForCurrentUser(String likeIdForCurrentUser) {
        this.likeIdForCurrentUser = likeIdForCurrentUser;
    }
    public String likeIdForCurrentUser(){ return likeIdForCurrentUser; }

    public void setUsernameForCurrentUser(String usernameForCurrentUser){
        this.usernameForCurrentUser = usernameForCurrentUser;
    }

    public String usernameForCurrentUser(){ return usernameForCurrentUser; }

    public void setNumOfLikes(int numOfLikes){
        this.numOfLikes = numOfLikes;
    }

    public int getNumOfLikes(){ return this.numOfLikes; }
}
