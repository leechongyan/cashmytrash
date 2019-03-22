package com.example.cz2006_mappy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SeeMoreCashIncentives extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_cash_incentives);

        FloatingActionButton seeMoreBackButton = (FloatingActionButton) findViewById(R.id.seeMoreBackButton);
        seeMoreBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), ConvertToCashNew.class);
                startActivity(backIntent);
            }
        });
    }
}
