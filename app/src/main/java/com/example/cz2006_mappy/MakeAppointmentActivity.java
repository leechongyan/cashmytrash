package com.example.cz2006_mappy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MakeAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.makeAppointmentBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MyListingActivity.class);
                startActivity(back);

            }
        });

        if (getIntent().hasExtra("com.example.cz2006.mappy.buyerUsername")){
            TextView buyerUsername = (TextView) findViewById(R.id.buyerUsername);
            String buyerUsernameDisplay = getIntent().getExtras().getString("com.example.cz2006.mappy.buyerUsername");
            buyerUsername.setText(buyerUsernameDisplay);

            TextView contactNumber = (TextView) findViewById(R.id.contactNumber);
            String contactNumberDisplay = getIntent().getExtras().getString("com.example.cz2006.mappy.contactNumber");
            contactNumber.setText(contactNumberDisplay);

            TextView emailAddress = (TextView) findViewById(R.id.email);
            String emailAddressDisplay = getIntent().getExtras().getString("com.example.cz2006.mappy.emailAddress");
            emailAddress.setText(emailAddressDisplay);
        }
    }
}
