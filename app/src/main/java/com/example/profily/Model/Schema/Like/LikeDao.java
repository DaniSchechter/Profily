package com.example.profily.Model.Schema.Like;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface LikeDao {

    // Get
    @Query("SELECT likeId FROM likes WHERE postId = :postId AND likingUserId = :likingUserId")
    String likeByUser(String postId, String likingUserId);

    // Insert
    @Query("INSERT INTO likes (likeId, postId, likingUserId) VALUES (:likeId, :postId, :likingUserId)")
    void like(String likeId, String postId, String likingUserId);

    @Query("DELETE FROM likes WHERE likeId = :likeId ")
    void unlike(String likeId);
}
