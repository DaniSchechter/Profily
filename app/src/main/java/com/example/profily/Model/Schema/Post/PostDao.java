package com.example.profily.Model.Schema.Post;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {

    // Get
    @Query("SELECT * FROM posts WHERE wasDeleted = 0 LIMIT :count")
    List<Post> getAllPosts(int count);

    @Query("SELECT * FROM posts WHERE postId = :postId AND wasDeleted = 0")
    Post getPostById(String postId);

    @Query("SELECT * FROM posts WHERE userCreatorId = :userId AND wasDeleted = 0 LIMIT :count")
    List<Post> getAllPostsByUserId(String userId, int count);

    @Query("SELECT * FROM posts WHERE wasDeleted = 0 AND userCreatorId IN " +
           "(SELECT following FROM follow_relation WHERE userId = :userId) LIMIT :count")
    List<Post> getAllFollowingPosts(String userId, int count);

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<Post> postsList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(Post post);

    // Update
    @Update
    void updatePosts(List<Post> postsList);

    @Query("UPDATE posts SET wasDeleted = 1 WHERE postId = :postId")
    void deleteById(String postId);
}