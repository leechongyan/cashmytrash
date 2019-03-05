package com.example.irecycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    SharedPreferences prf;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        TextView result = (TextView)findViewById(R.id.resultView);
        Button logout = (Button)findViewById(R.id.logout);
        i = new Intent(HomePage.this,Login.class);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        result.setText("Welcome, "+prf.getString("username",null));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                startActivity(i);
            }
        });
    }
}
