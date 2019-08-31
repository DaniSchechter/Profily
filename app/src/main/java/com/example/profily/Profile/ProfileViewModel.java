package com.example.profily.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.User.User;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<List<Post>> postsListLiveData;
    private MutableLiveData<User> userData;

    public ProfileViewModel() {

        postsListLiveData = new MutableLiveData<>();
        userData = new MutableLiveData<>();
    }

    public void getPosts(String userId){
        // Get all posts async
        Model.instance.getAllUserPosts( userId, postsList -> this.postsListLiveData.setValue(postsList));
    }

    public LiveData<List<Post>> getPostsList() {
        return this.postsListLiveData;
    }

    public LiveData<User> getUser() {
        return this.userData;
    }

    public void populateUserDetails(String userId){
        Model.instance.getUserById(userId, user -> this.userData.setValue(user));
    }

    public void updateUser(User user){
        Model.instance.addUser(user);
    }
}
