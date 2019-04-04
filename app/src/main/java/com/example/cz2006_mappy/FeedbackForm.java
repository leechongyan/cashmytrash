package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackForm extends AppCompatActivity {


    private FeedbackViewModel mFeedbackViewModel;
    SharedPreferences pref;
    private FeedbackManager manager = new FeedbackManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        //back button
        FloatingActionButton feedbackBackButton = (FloatingActionButton) findViewById(R.id.feedbackBackButton);
        feedbackBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void insertFeedback(View view){
        //gets all data necessary to pass to manager's function
        mFeedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
        EditText content = findViewById(R.id.editTextFeedback);
        String feedback = content.getText().toString();
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        String username = pref.getString("username","Anon");
        String user_id = pref.getString("email","Anon");

        //calls manager class to handle insertion of feedback
        boolean success = manager.insertFeedback(feedback, user_id,username);
        if(success){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Your Feedback has been sent",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent goHome = new Intent(this, HomeActivity.class);
            startActivity(goHome);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You cannot send an empty feedback", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
}
