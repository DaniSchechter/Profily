package com.example.profily.Home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.Post.PostLikeWrapper;

import java.util.LinkedList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<PostLikeWrapper>> postsListLiveData;
    private List<PostLikeWrapper> postLikeWrappersList;
    private static final int delta = 10;
    private int numOfPosts = 10;

    public HomeViewModel() {
        postsListLiveData = new MutableLiveData<>();
        postLikeWrappersList = new LinkedList<>();

        // Get all posts async
        Model.instance.getAllPosts( numOfPosts, postsList -> {
            Log.d("TAG", "------- Got posts, CLEARING POSTS LIST");
            postLikeWrappersList.clear();

            for(Post post: postsList) {

                // Initialize the Post Wrapper with the post itself
                PostLikeWrapper postLikeWrapper = new PostLikeWrapper(post, null);
                postLikeWrappersList.add(postLikeWrapper);

                // Check in the DB if this post is liked
                Model.instance.findLike(post.getPostId(), Model.instance.getConnectedUserId(), likeId ->  {
                        postLikeWrapper.setLikeIdForCurrentUser(likeId);
                        this.postsListLiveData.setValue(postLikeWrappersList);

                        // ---------------- DEBUGGING
                        Log.d("TAG", "Applying To VM's list: postID: " + post.getPostId() +" likeID: "+ likeId);
                        Log.d("TAG","******** Current VM's list ********");
                        for (PostLikeWrapper p : postLikeWrappersList) {
                            Log.d("TAG", p.post.getPostId() + " , " + p.likeIdForCurrentUser());
                        }
                });
            }
        });
    }

    public LiveData<List<PostLikeWrapper>> getPostsList() {
        return this.postsListLiveData;
    }

    public void loadMorePosts() {

        this.numOfPosts += delta;

        Model.instance.getAllPosts( numOfPosts, postsList -> {
            this.postsListLiveData.setValue(populatePostWrapperWithLike(postsList));
        });
    }

    private List<PostLikeWrapper> populatePostWrapperWithLike(List<Post> postsList) {
        postLikeWrappersList.clear();

        for(Post post: postsList) {
            // Initialize the Post Wrapper with the post itself
            PostLikeWrapper postLikeWrapper = new PostLikeWrapper(post, null);
            postLikeWrappersList.add(postLikeWrapper);

            // Check in the DB if this post is liked
            Model.instance.findLike(post.getPostId(), Model.instance.getConnectedUserId(), likeId -> {
                    postLikeWrapper.setLikeIdForCurrentUser(likeId);
                    Log.d("TAG", "Applying To VM's list: " + post.getPostId() +" "+ likeId);
            });
        }
        return postLikeWrappersList;
    }

    public static void likeToggle(PostLikeWrapper postLikeWrapper) {
        if(postLikeWrapper.likeIdForCurrentUser() == null ) {
            Model.instance.like(postLikeWrapper.post.getPostId(), Model.instance.getConnectedUserId(), likeId -> {
                postLikeWrapper.setLikeIdForCurrentUser(likeId);
            });
        } else {
            Model.instance.unlike(postLikeWrapper.likeIdForCurrentUser(), success ->{
                if(success) {
                    postLikeWrapper.setLikeIdForCurrentUser(null);
                }
            });
        }
    }

}
