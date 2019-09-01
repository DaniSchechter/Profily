package com.example.profily.Profile;

import android.graphics.Bitmap;
import android.util.Log;

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

    private MutableLiveData<Bitmap> imageBitmapLiveData;
    private static boolean imageLoading = false;

    public ProfileViewModel() {
        postsListLiveData = new MutableLiveData<>();
        userData = new MutableLiveData<>();
        imageBitmapLiveData = new MutableLiveData<>();
    }

    public void getPosts(String userId){
        // Get all posts async
        Model.instance.getAllUserPosts( userId, postsList -> this.postsListLiveData.setValue(postsList));
    }

    public MutableLiveData<List<Post>> getPostsList() {
        return this.postsListLiveData;
    }

    public LiveData<User> getUser() {
        return this.userData;
    }

    public void populateUserDetails(String userId){
        Model.instance.getUserById(userId, user -> this.userData.setValue(user));
    }

    public void updateUser(User user){
        if (ProfileViewModel.imageLoading) {
            Model.instance.uploadUserImage(user, imageBitmapLiveData.getValue(), new Model.SaveImageListener() {
                @Override
                public void onFailure() {
                    Log.e("TAG", "Error uploading image");}

                @Override
                public void onSuccess(String URI) {
                    ProfileViewModel.imageLoading = false;
                    // Update URI reference
                    user.setProfileImageURL(URI);
                    Model.instance.addUser(user);
                }
            });
        } else {
            Model.instance.addUser(user);
        }

    }

    public MutableLiveData<Bitmap> getImageBitmap() {
        return imageBitmapLiveData;
    }

    public void setImageBitmapLiveData(Bitmap imageBitmapLiveData) {
        ProfileViewModel.imageLoading = true;
        this.imageBitmapLiveData.setValue(imageBitmapLiveData);
    }

    public boolean isImageLoading() {
        return ProfileViewModel.imageLoading;
    }
}
