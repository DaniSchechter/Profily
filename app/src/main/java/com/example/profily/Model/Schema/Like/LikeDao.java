package com.example.profily.Model.Schema.Like;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LikeDao {

    // Get
    @Query("SELECT likeId FROM likes WHERE postId = :postId AND likingUserId = :likingUserId")
    String likeByUser(String postId, String likingUserId);

    @Query("SELECT COUNT(*) FROM likes WHERE postId = :postId")
    int getNumOfLikes(String postId);

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void like(Like like);

    @Query("DELETE FROM likes WHERE likeId = :likeId ")
    void unlike(String likeId);
}
