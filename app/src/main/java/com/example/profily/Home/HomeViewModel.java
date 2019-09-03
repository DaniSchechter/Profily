package com.example.profily.Home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Action.Action;
import com.example.profily.Model.Schema.Action.LikeAction;
import com.example.profily.Model.Schema.Notification.Notification;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.Post.PostLikeWrapper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<PostLikeWrapper>> postsListLiveData;
    private MutableLiveData<Integer> numOfPostsLiveData;
    private List<PostLikeWrapper> postLikeWrappersList;

    public HomeViewModel() {
        postsListLiveData = new MutableLiveData<>();
        postLikeWrappersList = new LinkedList<>();
        numOfPostsLiveData = new MutableLiveData<>();

        // Get all posts async
        Model.instance.getAllPosts(postsList -> {
            Log.d("TAG", "------- Got posts, CLEARING POSTS LIST");
            postLikeWrappersList.clear();
            setNumOfPostsLiveData(postsList.size());

            for(Post post: postsList) {

                // Initialize the Post Wrapper with the post itself
                PostLikeWrapper postLikeWrapper = new PostLikeWrapper(post, null, null);
                postLikeWrappersList.add(postLikeWrapper);

                // Check in the DB if this post is liked
                Model.instance.findLike(post.getPostId(), Model.instance.getConnectedUserId(), likeId ->  {
                    postLikeWrapper.setLikeIdForCurrentUser(likeId);

                    // ---------------- DEBUGGING
                    Log.d("TAG", "Applying To VM's list: postID: " + post.getPostId() +" likeID: "+ likeId);
                    Log.d("TAG","******** Current VM's list ********");
                    for (PostLikeWrapper p : postLikeWrappersList) {
                        Log.d("TAG", p.post.getPostId() + " , " + p.likeIdForCurrentUser());
                    }

                    // Get the username of the post
                    Model.instance.getUserById(post.getUserCreatorId(), user ->  {
                        postLikeWrapper.setUser(user);

                        // Get the number of likes of the post
                        Model.instance.getNumberOfLikes(post.getPostId(), numOfLikes ->  {
                            postLikeWrapper.setNumOfLikes(numOfLikes);
                            this.postsListLiveData.setValue(postLikeWrappersList);
                        });
                    });

                });
            }
        });
    }

    public LiveData<List<PostLikeWrapper>> getPostsList() {
        return this.postsListLiveData;
    }

    private List<PostLikeWrapper> populatePostWrapperWithLike(List<Post> postsList) {
        postLikeWrappersList.clear();

        for(Post post: postsList) {
            // Initialize the Post Wrapper with the post itself
            PostLikeWrapper postLikeWrapper = new PostLikeWrapper(post, null, null);
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
            String userId = Model.instance.getConnectedUserId();
            Model.instance.like(postLikeWrapper.post.getPostId(), userId, likeId -> {
                postLikeWrapper.setLikeIdForCurrentUser(likeId);
            });
            if (!userId.equals(postLikeWrapper.post.getUserCreatorId())){
                Model.instance.addNotification(new Notification(
                        new LikeAction(),
                        userId,
                        postLikeWrapper.post.getUserCreatorId(),
                        postLikeWrapper.post.getPostId(),
                        new Date(),
                        false
                ));
            }
        } else {
            Model.instance.unlike(postLikeWrapper.likeIdForCurrentUser(), success ->{
                if(success) {
                    postLikeWrapper.setLikeIdForCurrentUser(null);
                }
            });
        }
    }

    public static boolean checkEdit(String userId){
        return userId.equals(Model.instance.getConnectedUserId());
    }

    public MutableLiveData<Integer> getNumOfPostsLiveData() {
        return numOfPostsLiveData;
    }

    public void setNumOfPostsLiveData(Integer numOfPostsLiveData) {
        this.numOfPostsLiveData.setValue(numOfPostsLiveData);
    }
}
