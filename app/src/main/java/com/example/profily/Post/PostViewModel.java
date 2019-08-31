package com.example.profily.Post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Schema.Post.Post;


import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Post.PostLikeWrapper;

public class PostViewModel extends ViewModel {

    private MutableLiveData<PostLikeWrapper> postLiveData;

    public PostViewModel() {

        postLiveData = new MutableLiveData<>();
    }

    public void getPost(String postId){
        // Get all posts async
        Model.instance.getPostById( postId, post -> {

            PostLikeWrapper postLikeWrapper = new PostLikeWrapper(post, null, null);

            // Check in the DB if this post is liked
            Model.instance.findLike(post.getPostId(), Model.instance.getConnectedUserId(), likeId ->  {
                postLikeWrapper.setLikeIdForCurrentUser(likeId);

                // Check in the DB if this post is liked
                Model.instance.getUserNameById(post.getUserCreatorId(), userName ->  {
                    postLikeWrapper.setUsernameForCurrentUser(userName);

                    // Get the number of likes of the post
                    Model.instance.getNumberOfLikes(post.getPostId(), numOfLikes ->  {
                        postLikeWrapper.setNumOfLikes(numOfLikes);
                        this.postLiveData.setValue(postLikeWrapper);
                    });
                });
            });
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

    public void updatePost(Post post){
        Model.instance.addPost(post);
    }

}
