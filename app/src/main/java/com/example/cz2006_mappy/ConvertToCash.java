package com.example.cz2006_mappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConvertToCash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_to_cash);
    }
    public void seeMore(View view) {

        Intent seeMore = new Intent(this, SeeMoreCashIncentives.class);

        startActivity(seeMore);
    }
}
