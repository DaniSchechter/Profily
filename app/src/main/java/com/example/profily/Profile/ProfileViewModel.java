package com.example.profily.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Post.Post;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<List<Post>> postsListLiveData;
    private static final int delta = 9;
    private int numOfPosts = 9;

    public ProfileViewModel() {
        postsListLiveData = new MutableLiveData<>();
    }

    public void getPosts(String userId){
        // Get all posts async
        Model.instance.getAllUserPosts( userId, numOfPosts, postsList -> this.postsListLiveData.setValue(postsList));
    }

    public LiveData<List<Post>> getPostsList() {
        return this.postsListLiveData;
    }

    public void loadMorePosts(String postId) {

        this.numOfPosts += delta;
        Model.instance.getAllUserPosts( postId, numOfPosts, postsList -> this.postsListLiveData.setValue(postsList));
    }
}
