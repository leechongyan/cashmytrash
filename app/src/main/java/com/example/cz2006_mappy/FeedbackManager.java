package com.example.cz2006_mappy;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class FeedbackManager extends AppCompatActivity {
    private AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
    private FeedbackDao feedbackDao = db.feedbackDao();


    public boolean insertFeedback(String feedback, String user_id, String username){
        if(feedback.isEmpty()){
            return false;
        }
        else {
            Feedback fb = new Feedback(0,feedback,username,user_id);
            feedbackDao.insert(fb);
            return true;
        }
    }
}
