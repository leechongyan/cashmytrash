package com.example.cz2006_mappy;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FeedbackRepository {
    private FeedbackDao mFeedbackDao;
    private LiveData<List<Feedback>> mAllFeedbacks;

    public FeedbackRepository(Application application){
        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(application);
        mFeedbackDao = db.feedbackDao();
        mAllFeedbacks = mFeedbackDao.getAllFeedbacks();
    }

    LiveData<List<Feedback>> getAllFeedbacks(){
        return mAllFeedbacks;
    }

    public void insert(Feedback feedback) {
        new FeedbackRepository.insertAsyncTask(mFeedbackDao).execute(feedback);
    }

    private static class insertAsyncTask extends AsyncTask<Feedback, Void, Void> {


        private FeedbackDao mAsyncFeedbackDao;

        insertAsyncTask(FeedbackDao dao) {
            mAsyncFeedbackDao = dao;
        }

        @Override
        protected Void doInBackground(final Feedback... params) {
            mAsyncFeedbackDao.insert(params[0]);// Argument from asynctask which is item
            return null;
        }

    }
}

