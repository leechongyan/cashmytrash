package com.example.cz2006_mappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
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
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Your Feedback has been sent",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent goHome = new Intent(this, MainActivity.class);
            goHome.putExtra("feedbackContent", feedback);
            startActivity(goHome);
        }
    }
}
