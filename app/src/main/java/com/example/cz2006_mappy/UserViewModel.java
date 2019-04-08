package com.example.cz2006_mappy;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private LiveData<List<User>> mAllUsers;//add livedata member

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {return mAllUsers;}
    public User getUser(String email){ return mRepository.getUser(email); }
    public List<User> getSearchedUser(String s) {return mRepository.getSearchedUser(s);}
    public int deleteUser(User user){ return mRepository.deleteUser(user);}
}

