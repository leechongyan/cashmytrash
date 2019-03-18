package com.example.cz2006_mappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SeeMoreCashIncentives extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_cash_incentives);
    }
    public void back(View view) {

        Intent back = new Intent(this, ConvertToCashNew.class);

        startActivity(back);
    }
}
