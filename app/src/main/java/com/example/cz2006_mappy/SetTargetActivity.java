package com.example.cz2006_mappy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetTargetActivity extends AppCompatActivity {

    private Button setButton;
    private EditText setEditText;
    private Button cancelButton;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_target);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("com.example.cz2006.mappy.setTarget")){
            setButton = (Button) findViewById(R.id.setButton);
            setEditText = (EditText) findViewById(R.id.setEditText);
            cancelButton = (Button) findViewById(R.id.cancelButton);

            setButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == setButton.getId()){
                        text = setEditText.getText().toString();
                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putFloat("1", Float.parseFloat(text));
                        editor.apply();
                        Intent displayText = new Intent(getApplicationContext(), HomeActivity.class);
                        displayText.putExtra("com.example.cz2006.mappy.displayTarget", text);
                        startActivity(displayText);
                        Toast.makeText(getApplicationContext(),"Savings Target Set",Toast.LENGTH_LONG).show();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == cancelButton.getId()){
                        Intent goBack = new Intent(getApplicationContext(), HomeActivity.class);
                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        float target = sharedPref.getFloat("1", 0);
                        goBack.putExtra("com.example.cz2006.mappy.displayTarget", Float.toString(target));
                        goBack.putExtra("com.example.cz2006.mappy.goBack", "Go Back");
                        startActivity(goBack);
                        Toast.makeText(getApplicationContext(),"Setting Savings Target Cancelled",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

}
