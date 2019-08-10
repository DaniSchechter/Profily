package com.example.profily.Model;

import androidx.lifecycle.LiveData;

import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.Post.PostAsyncDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Model {

    final public static Model instance = new Model();
    private ModelSql modelSql;
    private ModelFireBase modelFirebase;

    private Model() {
        modelFirebase = new ModelFireBase();

//        List<Post> posts = new LinkedList<Post>();
//            for (int i = 0; i < 10; i++) {
//                Post p = new Post(
//                        "lite" + i,
//                        "Creator lite" + i,
//                        "imageUrl",
//                        "caption " + i,
//                        Arrays.asList("1", "2", "3"),
//                        Arrays.asList("4", "5"),
//                        false,
//                        new Date()
//                );
//                addAllPosts(p);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                posts.add(p);
//            }
//            addAllPosts(posts);
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

    public void getAllPosts(final GetAllPostsListener listener) {

        // Get already cached data
        PostAsyncDao.getAllPosts(cachedPosts -> {
            // Present it to the user
            listener.onComplete(cachedPosts);
            // Get the newest data from the cloud
            modelFirebase.getAllPosts(cloudPosts -> {
                // Update local DB
                PostAsyncDao.addPostsAndFetch(cloudPosts, posts -> listener.onComplete(posts));
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
//        }
//        }
//        );

    }

    public void addPostById(Post post) {
//        PostAsyncDao.addPosts(postsList);
        //modelFirebase.addPost(post, listener);
    }



//    public interface SaveImageListener{
//        void onComplete(String url);
//    }
//    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
//        modelFirebase.saveImage(imageBitmap, listener);
//    }
}
