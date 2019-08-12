package com.example.profily.Model.Schema.User;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.profily.Model.Schema.Post.Post;

import java.util.List;

@Dao
public interface UserDao {

    // Get
    @Query("SELECT * FROM posts WHERE userCreatorId = :userId AND wasDeleted = 0 ORDER BY createdDate Desc LIMIT :limit")
    List<Post> getAllUserPosts(String userId, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsers(List<User> usersList);
}
