package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackForm extends AppCompatActivity {


    private FeedbackViewModel mFeedbackViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        FloatingActionButton feedbackBackButton = (FloatingActionButton) findViewById(R.id.feedbackBackButton);
        feedbackBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), ListingActivity.class);
                startActivity(backIntent);
            }
        });
    }

    public void displayToast(View view){
        EditText content = findViewById(R.id.editTextFeedback);
        String feedback = content.getText().toString();
        if(feedback.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You cannot send an empty feedback", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            mFeedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Your Feedback has been sent",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent goHome = new Intent(this, ListingActivity.class);
            Feedback fb = new Feedback(0,feedback,"some username"); //todo: username to be integrated
            mFeedbackViewModel.insert(fb);

            goHome.putExtra("feedbackContent", feedback); //todo: to be deleted
            startActivity(goHome);
        }

    }
}
