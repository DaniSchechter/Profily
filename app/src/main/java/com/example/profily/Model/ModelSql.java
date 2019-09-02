package com.example.profily.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.profily.MainActivity;
import com.example.profily.Model.Schema.Comment.Comment;
import com.example.profily.Model.Schema.Comment.CommentDao;
import com.example.profily.Model.Schema.Follow.Follow;
import com.example.profily.Model.Schema.Follow.FollowDao;
import com.example.profily.Model.Schema.Like.Like;
import com.example.profily.Model.Schema.Like.LikeDao;
import com.example.profily.Model.Schema.Notification.Notification;
import com.example.profily.Model.Schema.Notification.NotificationDao;
import com.example.profily.Model.Schema.Post.PostDao;
import com.example.profily.Model.Schema.User.User;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.User.UserDao;


@Database(entities = {User.class, Post.class, Comment.class, Like.class, Follow.class, Notification.class}, version = 58, exportSchema = false)

public abstract class ModelSql extends RoomDatabase {
    private static ModelSql instance;

    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract CommentDao commentDao();
    public abstract LikeDao likeDao();
    public abstract NotificationDao notificationDao();

    public static ModelSql getInstance()
    {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            MainActivity.context, ModelSql.class, "database.db"
                    ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}