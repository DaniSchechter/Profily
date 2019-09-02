package com.example.profily.Model.Schema.Notification;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotificationDao {

    // Get
    @Query("SELECT notifications.* " +
            "FROM notifications JOIN posts ON notifications.effectedPostId = posts.postId " +
            "WHERE effectedUserId = :userId AND posts.wasDeleted = 0 " +
            "AND notifications.wasDeleted = 0 ORDER BY actionDateTime desc")
    List<Notification> getAllNotifications(String userId);

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotifications(List<Notification> notificationsList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotifications(Notification notification);

    // Update
    @Update
    void updateNotifications(List<Notification> notificationsList);

    @Query("UPDATE notifications SET wasDeleted = 1 WHERE notificationId = :notificationsId")
    void deleteById(String notificationsId);
}
