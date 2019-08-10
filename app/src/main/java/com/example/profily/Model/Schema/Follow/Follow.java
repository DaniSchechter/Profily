package com.example.profily.Model.Schema.Follow;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;
import com.example.profily.Model.Schema.User.User;

import java.util.List;

@Entity(tableName = "follow_relation")
@TypeConverters(Converters.class)
public class Follow {

    @PrimaryKey
    @NonNull
    @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"userId"})
    private final String userId;

    private List<String> following;
    private List<String> followers;



    public Follow(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
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
