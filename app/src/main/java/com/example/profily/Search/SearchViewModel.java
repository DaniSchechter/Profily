package com.example.profily.Search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.User.User;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<User>> usersListLiveData;

    public SearchViewModel() {

        usersListLiveData = new MutableLiveData<>();
    }

    public void getUsersByName(String name){
        Model.instance.getAllUserByName(name, users -> {
           this.usersListLiveData.setValue(users);
        });
    }

    public LiveData<List<User>> getUsersList() {
        return this.usersListLiveData;
    }
}
