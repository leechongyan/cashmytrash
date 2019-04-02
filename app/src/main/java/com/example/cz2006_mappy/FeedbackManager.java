package com.example.cz2006_mappy;

import android.support.v7.app.AppCompatActivity;

public class FeedbackManager extends AppCompatActivity {
    private AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
    private FeedbackDao feedbackDao = db.feedbackDao();
}
