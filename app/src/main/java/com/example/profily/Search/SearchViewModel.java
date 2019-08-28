package com.example.profily.Search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.User.User;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<User>> usersListLiveData;
//    private MutableLiveData<User> userData;

    public SearchViewModel() {

        usersListLiveData = new MutableLiveData<>();
//        userData = new MutableLiveData<>();
    }

    public void getUsersByName(String name){
        Model.instance.getAllUserByName(name, users -> {
           this.usersListLiveData.setValue(users);
        });
    }

    public LiveData<List<User>> getUsersList() {
        return this.usersListLiveData;
    }

//    public LiveData<User> getUser() {
//        return this.userData;
//    }

//    public void populateUserDetails(String userId){
//        Model.instance.getUserById(userId, user -> this.userData.setValue(user));
//    }
}
