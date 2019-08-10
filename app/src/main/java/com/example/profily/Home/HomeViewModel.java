package com.example.profily.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Post.Post;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postsListLiveData; //todo yblalal
    private static final int delta = 10;
    private int numOfPosts = 10;

    public HomeViewModel() {
        postsListLiveData = new MutableLiveData<>();

        // Get all posts async
        Model.instance.getAllPosts( numOfPosts, postsList -> this.postsListLiveData.setValue(postsList));
    }

    public LiveData<List<Post>> getPostsList() {
        return this.postsListLiveData;
    }

    public void loadMorePosts() {

        this.numOfPosts += delta;
        Model.instance.getAllPosts( numOfPosts, postsList -> this.postsListLiveData.setValue(postsList));
    }

}
