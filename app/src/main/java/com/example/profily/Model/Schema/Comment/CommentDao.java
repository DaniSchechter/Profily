package com.example.profily.Model.Schema.Comment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CommentDao {
    
    // Get
    @Query("SELECT * FROM comments WHERE postId = :postId AND wasDeleted = 0 ORDER BY createdDate Desc")
    List<Comment> getAllComments(String postId);

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(List<Comment> commentsList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComment(Comment comment);

    // Update
    @Update
    void updateComments(List<Comment> commentsList);

    @Query("UPDATE comments SET wasDeleted = 1 WHERE commentId = :commentId")
    void deleteById(String commentId);
}
