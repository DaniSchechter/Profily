package com.example.profily.Post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Schema.Post.Post;


import com.example.profily.Model.Model;

public class PostViewModel extends ViewModel {

    private MutableLiveData<Post> postLiveData;

    public PostViewModel() {

        postLiveData = new MutableLiveData<>();
    }

    public void getPost(String postId){
        // Get all posts async
        Model.instance.getPostById( postId, postData -> this.postLiveData.setValue(postData));
    }

    public LiveData<Post> getPost() {
        return this.postLiveData;
    }
}
