package com.example.profily.Model;

import android.util.Log;

import com.example.profily.Model.Schema.Action.Action;
import com.example.profily.Model.Schema.Action.CommentAction;
import com.example.profily.Model.Schema.Action.LikeAction;
import com.example.profily.Model.Schema.Action.SubscriptionAction;
import com.example.profily.Model.Schema.Comment.Comment;
import com.example.profily.Model.Schema.Comment.CommentAsyncDao;
import com.example.profily.Model.Schema.Like.LikeAsyncDao;
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
    =======================
    POSTS
    =======================
     */

    public interface GetAllPostsListener {
        void onComplete(List<Post> posts);
    }

    public interface AddPostListener {
        void onComplete(boolean success);
    }

    public void getAllPosts(final int numOfPosts, final GetAllPostsListener listener) {

        // Get already cached data
        PostAsyncDao.getAllPosts(numOfPosts, cachedPosts -> {
            // Present it to the user
            Log.d("TAG", "------- STARTING DISPLAYING LOCAL POSTS ------");
            listener.onComplete(cachedPosts);
            // Get the newest data from the cloud
            modelFirebase.getAllPosts(numOfPosts, cloudPosts -> {
                // Update local DB
                Log.d("TAG", "------- STARTING DISPLAYING REMOTE POSTS ------");
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
    =======================
    LIKES
    =======================
     */


    public interface FindLikeListener {
        /**
         * @return String - likeId if exists, null if does not exist yet
         * */
        void onComplete(String likeId);
    }

    public interface GetNumberOfLikesListener{
        void onComplete(int numOfLikes);
    }

    public interface LikeOperationListener {
        void onComplete(String likeId);
    }

    public interface UnlikeOperationListener {
        void onComplete(boolean success);
    }

    public void findLike(String postId, String userId, FindLikeListener listener) {
        // Get already cached data
        LikeAsyncDao.likeByUser(postId, userId, localLikeId -> {
            Log.d("TAG", "============== FINISHED SEARCHING NEW LIKE ==============");
            Log.d("TAG", "post: " + postId + ", user: " + userId);
            Log.d("TAG", "LIKE local : " + localLikeId );
            // Present it to the user
            listener.onComplete(localLikeId);
            // Get the newest data from the cloud
            modelFirebase.likeByUser(postId, userId, cloudLikeId -> {
                Log.d("TAG", "LIKE remote of user: "+ userId + ", postID: " +postId + ": "+ cloudLikeId );
                // Update local DB
                if (
                        (localLikeId == null && cloudLikeId != null) ||
                        (localLikeId != null && cloudLikeId == null) ||
                        (localLikeId != null && ! localLikeId.equals(cloudLikeId))
                ) {
                    Log.w("TAG", "Like state is different in FB and SQLite");
                    if (localLikeId == null) {               // In local DB it is not liked, but should be
                        Log.d("TAG", "Adding like to local DB");
                        LikeAsyncDao.like(cloudLikeId, postId, userId, i -> {});
                    } else {
                        Log.d("TAG", "Removing like from local DB");
                        LikeAsyncDao.unlike(cloudLikeId, i-> {});
                    }
                }
                listener.onComplete(cloudLikeId);
                Log.d("TAG", "---------------------------------\n\n");

            });
        });
    }

    public void getNumberOfLikes(String postId, GetNumberOfLikesListener listener){
        LikeAsyncDao.getNumOfLikes(postId, localNumOfLikes-> {
           listener.onComplete(localNumOfLikes);

           modelFirebase.getNumOfLikes(postId, cloudNumOfLikes->{
              listener.onComplete(cloudNumOfLikes);
           });
        });
    }

    public void like(String postId, String userId, LikeOperationListener listener) {
        modelFirebase.like(postId, userId, likeId -> {
            if (likeId == null) {
                Log.e("TAG" ,"Could not preform remote like operation, postId " + postId
                        + ", userId " + userId);
                return;
            }
            LikeAsyncDao.like(likeId, postId, userId,  i -> listener.onComplete(likeId));
        });
    }

    public void unlike(String likeId, UnlikeOperationListener listener) {
        modelFirebase.unlike(likeId, success -> {
            if (!success) {
                Log.e("TAG" ,"Could not preform remote unlike operation, likeId " + likeId);
                return;
            }
            LikeAsyncDao.unlike(likeId, i -> listener.onComplete(true));
        });
    }

    /*
    =======================
    USERS
    =======================
     */

    public interface GetAllUsersByNameListener{
        void onComplete(List<User> users);
    }

    public interface GetUserNameByIdListener{
        void onComplete(String username);
    }

    public String getConnectedUserId() {
        return modelFirebase.getUserById();
    }

    public void logOut() {
        modelFirebase.logOut();
    }

    public void getAllUserByName(String username, final GetAllUsersByNameListener listener) {
        modelFirebase.getAllUserByName(username, cloudUsers -> listener.onComplete(cloudUsers));
    }
//    public interface SaveImageListener{
//        void onComplete(String url);
//    }
//    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
//        modelFirebase.saveImage(imageBitmap, listener);
//    }

    public void getUserNameById(String userId, final GetUserNameByIdListener listener){
        // Get already cached data
        PostAsyncDao.getUserNameById(userId, localUsername -> {
            // Present it to the user
            listener.onComplete(localUsername);
            // Get the newest data from the cloud
            modelFirebase.getUserNameById(userId, cloudUsername -> {
                listener.onComplete(cloudUsername);
            });
        });
    }

    /*
    =======================
    COMMENTS
    =======================
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
    =======================
    NOTIFICATIONS
    =======================
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
    =======================
    PROFILE
    =======================
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
