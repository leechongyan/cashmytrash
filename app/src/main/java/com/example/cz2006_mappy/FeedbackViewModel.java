package com.example.cz2006_mappy;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class FeedbackViewModel extends AndroidViewModel {
    private FeedbackRepository mRepository;

    private LiveData<List<Feedback>> mAllFeedbacks;//add livedata member

    public FeedbackViewModel (Application application) {
        super(application);
        mRepository = new FeedbackRepository(application);
        mAllFeedbacks = mRepository.getAllFeedbacks();
    }

    LiveData<List<Feedback>> getAllFeedbacks() {return mAllFeedbacks;}

    public void insert(Feedback feedback) {mRepository.insert(feedback);}
}
