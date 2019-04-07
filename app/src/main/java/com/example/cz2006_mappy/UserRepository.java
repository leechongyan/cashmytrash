package com.example.cz2006_mappy;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {

    private UserDAO mUserDAO;
    private LiveData<List<User>> mAllUsers;
    //list all member variable of the dao, check the return value of the method in Dao

    public UserRepository(Application application) {
        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(application);
        mUserDAO = db.userDao();
        mAllUsers = mUserDAO.getAllUsers();
    }

    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }
    public User getUser(String email){ return mUserDAO.getUser(email);}
    public int deleteUser(User user){return mUserDAO.delete(user);}
    public List<User> getSearchedUser(String s) { return mUserDAO.getSearchedUser(s);}

}
