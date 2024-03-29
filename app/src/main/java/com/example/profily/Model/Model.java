package com.example.profily.Model;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.profily.Model.Schema.Action.Action;
import com.example.profily.Model.Schema.Action.CommentAction;
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

    public void getAllPosts(final GetAllPostsListener listener) {

        // Get already cached data
        PostAsyncDao.getAllPosts(cachedPosts -> {
            // Present it to the user
            Log.d("TAG", "------- STARTING DISPLAYING LOCAL POSTS ------");
            listener.onComplete(cachedPosts);
            // Get the newest data from the cloud
            modelFirebase.getAllPosts(cloudPosts -> {
                // Update local DB
                Log.d("TAG", "------- STARTING DISPLAYING REMOTE POSTS ------");
                PostAsyncDao.addPostsAndFetch(cloudPosts, posts -> listener.onComplete(posts));
            });
        });
        //PostAsyncDao.getAllPosts(listener);
    }

    public interface GetPostByIdListener {
        void onComplete(Post post);
    }

    public interface GetUserIdByPostListener {
        void onComplete(String userId);
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

    public void addPost(Post post, final AddPostListener listener){
        modelFirebase.addPost(post, new AddPostListener() {
            @Override
            public void onComplete(boolean success) {
                PostAsyncDao.addPost(post);
                listener.onComplete(success);
            }
        });
    }

    public void updatePost(Post post) {
        PostAsyncDao.updatePost(post);
        modelFirebase.updatePost(post);
    }
    
    /*
    =======================
    LIKES
    =======================
     */


    public interface FindLikeListener {
        /**
         * @return String - likeId if exists, null if does not exist yet
         */
        void onComplete(String likeId);
    }

    public interface GetNumberOfLikesListener {
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
            Log.d("TAG", "LIKE local : " + localLikeId);
            // Present it to the user
            listener.onComplete(localLikeId);
            // Get the newest data from the cloud
            modelFirebase.likeByUser(postId, userId, cloudLikeId -> {
                Log.d("TAG", "LIKE remote of user: " + userId + ", postID: " + postId + ": " + cloudLikeId);
                // Update local DB if needed

                if (localLikeId == null && cloudLikeId != null) {
                    Log.d("TAG", "Adding like to local DB");
                    LikeAsyncDao.like(cloudLikeId, postId, userId, i -> {
                    });
                } else if (localLikeId != null && cloudLikeId == null) {
                    Log.d("TAG", "Removing like from local DB");
                    LikeAsyncDao.unlike(localLikeId, i -> {
                    });
                } else if (localLikeId != null && !localLikeId.equals(cloudLikeId)) {
                    Log.d("TAG", "Updating like id in local DB");
                    LikeAsyncDao.unlike(localLikeId, i -> {
                    });
                    LikeAsyncDao.like(cloudLikeId, postId, userId, i -> {
                    });
                }
                listener.onComplete(cloudLikeId);
                Log.d("TAG", "---------------------------------\n\n");

            });
        });
    }

    public void getNumberOfLikes(String postId, GetNumberOfLikesListener listener) {
        modelFirebase.getNumOfLikes(postId, cloudNumOfLikes -> {
            listener.onComplete(cloudNumOfLikes);
        });
    }

    public void like(String postId, String userId, LikeOperationListener listener) {
        modelFirebase.like(postId, userId, likeId -> {
            if (likeId == null) {
                Log.e("TAG", "Could not preform remote like operation, postId " + postId
                        + ", userId " + userId);
                return;
            }
            LikeAsyncDao.like(likeId, postId, userId, i -> listener.onComplete(likeId));
        });
    }

    public void unlike(String likeId, UnlikeOperationListener listener) {
        modelFirebase.unlike(likeId, success -> {
            if (!success) {
                Log.e("TAG", "Could not preform remote unlike operation, likeId " + likeId);
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

    public interface GetAllUsersByNameListener {
        void onComplete(List<User> users);
    }

    public interface GetUserByIdListener {
        void onComplete(User user);
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

    public void getUserById(String userId, final GetUserByIdListener listener) {
        // Get already cached data
        UserAsyncDao.getUserById(userId, localUser -> {
            // Present it to the user
            if (localUser != null){
                listener.onComplete(localUser);
            }
            // Get the newest data from the cloud
            modelFirebase.getUserById(userId, cloudUser -> {
                UserAsyncDao.addUserAndFetch(cloudUser, user -> listener.onComplete(user));
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

    public void getAllComments(final String postId, final GetAllCommentsListener listener) {

        // Get already cached data
        CommentAsyncDao.getAllComments(postId, cachedComments -> {
            // Present it to the user
            listener.onComplete(cachedComments);
            // Get the newest data from the cloud
            modelFirebase.getAllComments(postId, cloudComments -> {
                // Update local DB
                CommentAsyncDao.addCommentsAndFetch(postId, cloudComments, comments -> listener.onComplete(comments));
            });
        });
    }

    public void addComment(Comment comment) {
        modelFirebase.addComment(comment, new AddCommentListener() {
            @Override
            public void onComplete(boolean success) {
                CommentAsyncDao.addComment(comment);
                PostAsyncDao.getUserIdByPost(comment.getPostId(), userId -> {
                    if (!userId.equals(comment.getUserCreatorId())) {
                        addNotification(new Notification(
                                new CommentAction(),
                                comment.getUserCreatorId(),
                                userId,
                                comment.getPostId(),
                                new Date(),
                                false
                        ));
                    }
                });
            }
        });
    }

    public void updateComment(Comment comment) {
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

    public void getAllNotifications(final String userId, final GetAllNotificationsListener listener) {

        // Get already cached data
        NotificationAsyncDao.getAllNotifications(userId, cachedNotifications -> {
            // Present it to the user
            listener.onComplete(cachedNotifications);
            // Get the newest data from the cloud
            modelFirebase.getAllNotifications(userId, cloudNotifications -> {
                // Update local DB
                NotificationAsyncDao.addNotificationsAndFetch(userId, cloudNotifications, notifications ->
                        listener.onComplete(notifications));
            });
        });
    }

    public void addNotification(Notification notification) {
        // No reason to add to local cache because the effected user is the one who gets the notification
        modelFirebase.addNotification(notification, result -> {
        });
    }


    /*
    =======================
    PROFILE
    =======================
     */

    public interface GetAllUserPostsListener {
        void onComplete(List<Post> posts);
    }

    public void getAllUserPosts(String userId, final GetAllUserPostsListener listener) {

        // Get already cached data
        UserAsyncDao.getAllUserPosts(userId, cachedPosts -> {
            // Present it to the user
            listener.onComplete(cachedPosts);
            // Get the newest data from the cloud
            modelFirebase.getAllUserPosts(userId, cloudPosts -> {
                // Update local DB
                UserAsyncDao.addUserPostsAndFetch(userId, cloudPosts, posts -> listener.onComplete(posts));
            });
        });
    }

    public interface AddUserListener {
        void onComplete(boolean success);
    }

    public void addUser(User user) {
        modelFirebase.addUser(user, new AddUserListener() {
            @Override
            public void onComplete(boolean success) {
                if (success) {
                    UserAsyncDao.addUser(user);
                }
            }
        });
    }

    public interface GetConnectedUserListener {
        void onComplete(User user);
    }

    public interface GetPostCountListener {
        void onComplete(Integer numOfPosts);
    }

    /*
    =======================
    FIREBASE STORAGE
    =======================
     */

    public interface SaveImageListener {
        void onFailure();

        void onSuccess(String URI);
    }

    private void uploadImage(Bitmap imageBmp, String collection_name, Model.SaveImageListener listener) {
        modelFirebase.uploadImage(imageBmp, collection_name, listener);
    }

    public void uploadPostImage(Post post, Bitmap imageBmp, Model.SaveImageListener listener) {
        this.uploadImage(imageBmp, "post_image/"+post.getPostId(), new SaveImageListener() {
            @Override
            public void onFailure() {
                Log.e("TAG", "Error uploading posts's image");
                listener.onFailure();
            }

            @Override
            public void onSuccess(String URI) {
                listener.onSuccess(URI);
            }
        });
    }

    public void uploadUserImage(User user, Bitmap imageBmp, Model.SaveImageListener listener) {
        this.uploadImage(imageBmp, "user_image/"+user.getUserId(), new SaveImageListener() {
            @Override
            public void onFailure() {
                Log.e("TAG", "Error uploading user's image");
                listener.onFailure();
            }

            @Override
            public void onSuccess(String URI) {
                listener.onSuccess(URI);
            }
        });
    }

}
