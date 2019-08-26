package com.example.profily.Model;

import android.util.Log;

import com.example.profily.Model.Schema.Action.Action;
import com.example.profily.Model.Schema.Action.CommentAction;
import com.example.profily.Model.Schema.Action.LikeAction;
import com.example.profily.Model.Schema.Action.SubscriptionAction;
import com.example.profily.Model.Schema.Comment.Comment;
import com.example.profily.Model.Schema.Comment.CommentAsyncDao;
import com.example.profily.Model.Schema.Notification.Notification;
import com.example.profily.Model.Schema.Notification.NotificationAsyncDao;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.Post.PostAsyncDao;
import com.example.profily.Model.Schema.User.User;
import com.example.profily.Model.Schema.User.UserAsyncDao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Model {

    final public static Model instance = new Model();
    private ModelSql modelSql;
    private ModelFireBase modelFirebase;

    private Model() {
        modelFirebase = new ModelFireBase();
    }

    /*
    -----------------------
    POSTS
    -----------------------
     */

    public interface GetAllPostsListener {
        void onComplete(List<Post> posts);
    }

    public interface AddPostListener {
        void onComplete(boolean success);
    }

    public interface GetUserNameByIdListener{
        void onComplete(String username);
    }

    public void getAllPosts(final int numOfPosts, final GetAllPostsListener listener) {

        // Get already cached data
        PostAsyncDao.getAllPosts(numOfPosts, cachedPosts -> {
            // Present it to the user
            listener.onComplete(cachedPosts);
            // Get the newest data from the cloud
            modelFirebase.getAllPosts(numOfPosts, cloudPosts -> {
                // Update local DB
                PostAsyncDao.addPostsAndFetch(numOfPosts, cloudPosts, posts -> listener.onComplete(posts));
            });
        });
        //PostAsyncDao.getAllPosts(listener);
    }

    public interface  GetPostByIdListener{
        void onComplete(Post post);
    }

    public void getPostById(String postId, final GetPostByIdListener listener) {
        // Get already cached data
        PostAsyncDao.getPostById(postId, cachedPost -> {
            // Present it to the user
            listener.onComplete(cachedPost);
            // Get the newest data from the cloud
            modelFirebase.getPostById(postId, cloudPost -> {
                // Update local DB
                PostAsyncDao.addPostAndFetch(cloudPost, post -> listener.onComplete(post));
            });
        });
    }

    public void addAllPosts(List<Post> postsList) {
        PostAsyncDao.addPosts(postsList);
//        modelFirebase.addPost(postsList, new AddPostListener() {
//            @Override
//            public void onComplete(boolean success) {
//
//            }
//        });
    }

    public void addPostById(Post post) {
//        PostAsyncDao.addPosts(postsList);
        //modelFirebase.addPost(post, listener);
    }


    /*
    -----------------------
    USERS
    -----------------------
     */

    public String getConnectedUserId() {
        return modelFirebase.getUserById();
    }

    public void logOut() {
        modelFirebase.logOut();
    }

//    public interface SaveImageListener{
//        void onComplete(String url);
//    }
//    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
//        modelFirebase.saveImage(imageBitmap, listener);
//    }

    /*
    -----------------------
    COMMENTS
    -----------------------
     */

    public interface GetAllCommentsListener {
        void onComplete(List<Comment> comments);
    }

    public interface AddCommentListener {
        void onComplete(boolean success);
    }

    public void getAllComments(final String postId, final int numOfComments, final GetAllCommentsListener listener) {

        // Get already cached data
        CommentAsyncDao.getAllComments(postId, numOfComments, cachedComments -> {
            // Present it to the user
            listener.onComplete(cachedComments);
            // Get the newest data from the cloud
            modelFirebase.getAllComments(postId, numOfComments, cloudComments -> {
                // Update local DB
                CommentAsyncDao.addCommentsAndFetch(postId, numOfComments, cloudComments, comments -> listener.onComplete(comments));
            });
        });
    }

    public void addComment(Comment comment) {
//        CommentAsyncDao.addComment(comment);
        modelFirebase.addComment(comment, new AddCommentListener() {
            @Override
            public void onComplete(boolean success) {

            }
        });
    }

    public void updateComment(Comment comment)
    {
        modelFirebase.updateComment(comment);
        CommentAsyncDao.updateComment(comment);
    }

    /*
    -----------------------
    NOTIFICATIONS
    -----------------------
     */

    public interface GetAllNotificationsListener {
        void onComplete(List<Notification> notifications);
    }

    public interface AddNotificationListener {
        void onComplete(boolean success);
    }

    public void getAllNotifications(final String userId, final int numOfNotifications, final GetAllNotificationsListener listener) {

        // Get already cached data
        NotificationAsyncDao.getAllNotifications(userId, numOfNotifications, cachedNotifications -> {
            // Present it to the user
            listener.onComplete(cachedNotifications);
            // Get the newest data from the cloud
            modelFirebase.getAllNotifications(userId, numOfNotifications, cloudNotifications -> {
                // Update local DB
                NotificationAsyncDao.addNotificationsAndFetch(userId, numOfNotifications, cloudNotifications, notifications ->
                        listener.onComplete(notifications));
            });
        });
    }

    public void addAllNotifications(List<Notification> notificationsList) {
//        NotificationAsyncDao.addNotifications(notificationsList);
        for(Notification n: notificationsList){
            modelFirebase.addNotification(n, result -> Log.d("TAG", String.valueOf(result)));
        }

    }

//    public interface SaveImageListener{
//        void onComplete(String url);
//    }
//    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
//        modelFirebase.saveImage(imageBitmap, listener);
//    }


    /*
    -----------------------
    PROFILE
    -----------------------
     */

    public interface GetAllUserPostsListener {
        void onComplete(List<Post> posts);
    }

    public void getAllUserPosts(String userId, final int numOfPosts, final GetAllUserPostsListener listener) {

        // Get already cached data
        UserAsyncDao.getAllUserPosts(userId, numOfPosts, cachedPosts -> {
            // Present it to the user
            listener.onComplete(cachedPosts);
            // Get the newest data from the cloud
            modelFirebase.getAllUserPosts(userId, numOfPosts, cloudPosts -> {
                // Update local DB
                UserAsyncDao.addUserPostsAndFetch(userId, numOfPosts, cloudPosts, posts -> listener.onComplete(posts));
            });
        });
        //PostAsyncDao.getAllPosts(listener);
    }

    public interface AddUserListener{
        void onComplete(boolean success);
    }

    public void addUser(User user){
        modelFirebase.addUser(user, new AddUserListener() {
            @Override
            public void onComplete(boolean success) {

            }
        });
        UserAsyncDao.addUser(user);
    }

    public interface GetConnectedUserListener{
        void onComplete(User user);
    }

    public void getUserById(String userId, final GetConnectedUserListener listener) {

        // Get already cached data
        UserAsyncDao.getUserById(userId, cachedUser -> {
            // Present it to the user
            if (cachedUser != null){
                listener.onComplete(cachedUser);
            }
            // Get the newest data from the cloud
            modelFirebase.getUserById(userId, cloudUser -> {
                // Update local DB
                UserAsyncDao.addUserDetailsAndFetch(userId, cloudUser, posts -> listener.onComplete(posts));
            });
        });
        //PostAsyncDao.getAllPosts(listener);
    }

    public interface GetPostCountListener{
        void onComplete(Integer numOfPosts);
    }
}
