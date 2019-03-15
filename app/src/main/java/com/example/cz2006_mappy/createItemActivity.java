package com.example.cz2006_mappy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class createItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        Button createItemActivityButton = (Button) findViewById(R.id.createItemButton);
//        createItemActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //EditText itemName = (EditText)  findViewById(R.id.create_item_name);
//                //....
//                //1.validate input
//                //if validated
//                //Intent in = new Intent(getApplicationContext(),MainActivity.class);
//                //startActivity(in);
//                //else balikin terus kasih error msg popup aja
//            }
//        });

        FloatingActionButton createItemBackButton = (FloatingActionButton) findViewById(R.id.createItemBackButton);
        createItemBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createItemBackIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(createItemBackIntent);
            }
        });
    }
}
