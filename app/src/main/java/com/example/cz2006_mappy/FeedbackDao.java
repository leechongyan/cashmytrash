package com.example.cz2006_mappy;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface FeedbackDao {

    @Insert
    void insert(Feedback feedback);

    @Query("DELETE FROM FEEDBACK")
    void deleteAll();

    @Delete
    int delete(Feedback feedback);

    @Query("SELECT * FROM FEEDBACK")
    LiveData<List<Feedback>> getAllFeedbacks();

    @Query("SELECT * FROM FEEDBACK WHERE feedback_id = :feedback_id")
    public Feedback getFeedback(int feedback_id);
}

