package com.example.irecycle;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

}


