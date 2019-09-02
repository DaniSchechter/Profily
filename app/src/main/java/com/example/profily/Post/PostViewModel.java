package com.example.profily.Post;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Schema.Post.Post;


import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Post.PostLikeWrapper;

import java.util.Date;

public class PostViewModel extends ViewModel {

    private MutableLiveData<PostLikeWrapper> postLiveData;
    private MutableLiveData<Bitmap> bitmap;

    public PostViewModel() {
        postLiveData = new MutableLiveData<>();
        bitmap = new MutableLiveData<>();
    }

    public void getPost(String postId){
        // Get all posts async
        Model.instance.getPostById( postId, post -> {

            if (post!=null){
                PostLikeWrapper postLikeWrapper = new PostLikeWrapper(post, null, null);

                // Check in the DB if this post is liked
                Model.instance.findLike(post.getPostId(), Model.instance.getConnectedUserId(), likeId ->  {
                    postLikeWrapper.setLikeIdForCurrentUser(likeId);

                    // Check in the DB if this post is liked
                    Model.instance.getUserById(post.getUserCreatorId(), user ->  {
                        postLikeWrapper.setUser(user);

                        // Get the number of likes of the post
                        Model.instance.getNumberOfLikes(post.getPostId(), numOfLikes ->  {
                            postLikeWrapper.setNumOfLikes(numOfLikes);
                            this.postLiveData.setValue(postLikeWrapper);
                        });
                    });
                });
            }
        });
    }

    public void addPost(String description) {
        Post post = new Post(
                Model.instance.getConnectedUserId(),
                null,
                description,
                false,
                new Date()
        );
        Model.instance.addPost(post, success -> {
            if (!success){
                return;
            }
            Model.instance.uploadPostImage(post, bitmap.getValue(), new Model.SaveImageListener() {
                @Override
                public void onFailure() {
                    Log.e("TAG", "Error uploading image");}

                @Override
                public void onSuccess(String URI) {
                    // Update URI reference
                    post.setImageURL(URI);
                    Model.instance.updatePost(post);
                }
            });
        });
    }

    public void updatePost(Post post){
        if(bitmap.getValue() == null) {
            Model.instance.updatePost(post);
            return;
        }
        Model.instance.uploadPostImage(post, bitmap.getValue(), new Model.SaveImageListener() {
            @Override
            public void onFailure() {
                Log.e("TAG", "Error uploading image");}

            @Override
            public void onSuccess(String URI) {
                // Update URI reference
                post.setImageURL(URI);
                Model.instance.updatePost(post);
            }
        });
    }

    public static void updatePost(Post post, Bitmap bitmap){
        if (bitmap == null) {
            Model.instance.updatePost(post);
            return;
        }

        Model.instance.uploadPostImage(post, bitmap, new Model.SaveImageListener() {
            @Override
            public void onFailure() {
                Log.e("TAG", "Error uploading image");}

            @Override
            public void onSuccess(String URI) {
                // Update URI reference
                post.setImageURL(URI);
                Model.instance.updatePost(post);
            }
        });
    }


    public void populatePostDetails(String postId){
        Model.instance.getPostById(postId, post -> {
            PostLikeWrapper postLikeWrapper = new PostLikeWrapper(post, null, null);
            this.postLiveData.setValue(postLikeWrapper);
        });
    }

    public LiveData<PostLikeWrapper> getPost() {
        return this.postLiveData;
    }

    public MutableLiveData<Bitmap> getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap.setValue(bitmap);
    }
}
