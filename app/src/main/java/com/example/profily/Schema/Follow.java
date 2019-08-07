package com.example.profily.Schema;

import java.util.List;

public class Follow {

    private String userId;
    private List<String> following;
    private List<String> followers;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String getUserId()
    {
        return this.userId;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }
}
