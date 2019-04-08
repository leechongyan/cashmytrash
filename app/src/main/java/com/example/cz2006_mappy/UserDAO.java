package com.example.cz2006_mappy;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    public long insert(User user);

    @Update
    public void update(User user);

    @Delete
    public int delete(User user);

    @Query("SELECT * FROM user")
    public User[] loadAllUsers();

    @Query("SELECT * FROM user WHERE emailaddress = :email")
    public User getUser(String email);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE username LIKE '%'  ||:s || '%'")
    List<User> getSearchedUser(String s);

}
