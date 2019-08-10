package com.example.profily.Home;

import android.view.Display;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Post.Post;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postsListLiveData; //todo yblalal

    public HomeViewModel() {
        postsListLiveData = new MutableLiveData<>();
int i = 3;
        // Get all posts async
        Model.instance.getAllPosts( postsList -> this.postsListLiveData.setValue(postsList));
    }

    public LiveData<List<Post>> getPostsList() {
        return this.postsListLiveData;
    }


}
