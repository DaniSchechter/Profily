package com.example.profily.Model;

import com.example.profily.Model.Schema.Comment.Comment;
import com.example.profily.Model.Schema.Comment.CommentAsyncDao;
import com.example.profily.Model.Schema.Notification.Notification;
import com.example.profily.Model.Schema.Notification.NotificationAsyncDao;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.Post.PostAsyncDao;

import java.util.List;

public class Model {

    final public static Model instance = new Model();
    private ModelSql modelSql;
    private ModelFireBase modelFirebase;

    private Model() {
        modelFirebase = new ModelFireBase();

//        List<Post> posts = new LinkedList<Post>();
//        for (int i = 10; i < 22; i++) {
//            Comment p = new Comment(
//                    "comment" + i,
//                    "good post, post" + i,
//                    "userCreator"+i,
//                    "lite" + i,
//                    false,
//                    new Date()
//            );
//
//            addAllComments(p);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////                posts.add(p);
////            }
////            addAllPosts(posts);
//        }
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

    public void getPostById(String postId) {
//        PostAsyncDao.addPosts(postsList);
        //modelFirebase.addPost(post, listener);
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
        return modelFirebase.getConnectedUserId();
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


//    public interface SaveImageListener{
//        void onComplete(String url);
//    }
//    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
//        modelFirebase.saveImage(imageBitmap, listener);
//    }


}
