package com.example.profily.Model.Schema.Like;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.User.User;

@Entity(tableName = "likes")
@TypeConverters(Converters.class)
public class Like {

    @PrimaryKey()
    @NonNull
    private String likeId;

    @ForeignKey(entity = Post.class, parentColumns = {"postId"}, childColumns = {"postId"})
    private String postId;

    @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"likingUserId"})
    private String likingUserId;

    public Like(){}

    @Ignore
    public Like(String postId, String likingUserId) {
        this.postId = postId;
        this.likingUserId = likingUserId;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public String getLikingUserId() {
        return likingUserId;
    }

    public void setLikingUserId(String likingUserId) {
        this.likingUserId = likingUserId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
