package com.example.profily.Model.Schema.User;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey()
    @NonNull
    private String userId;

    private String profileImageURL;
    private String username;
    private String password;
    private String description;


    public User() { }

    @Ignore
    public User(@NonNull String userId, String profileImageURL, String username, String password, String description) {
        this.userId = userId;
        this.profileImageURL = profileImageURL;
        this.username = username;
        this.password = password;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { //TODO remove unnecessary getters and setters
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
