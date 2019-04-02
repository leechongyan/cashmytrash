package com.example.cz2006_mappy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MakeAppointmentActivity extends AppCompatActivity {

    final AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
    final UserDAO mUserDao = db.userDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        String buyer_id = getIntent().getExtras().getString("buyer_id");
        User buyer = mUserDao.getUser(buyer_id);
        String buyer_username = buyer.getUsername();
        int buyer_phone = buyer.getPhone();

        TextView buyerUsername = (TextView) findViewById(R.id.buyerUsername);
        TextView buyerPhone = (TextView) findViewById(R.id.contactNumber);
        TextView buyerEmail = (TextView) findViewById(R.id.email);

        buyerUsername.setText(buyer_username);
        buyerEmail.setText(buyer_id);
        buyerPhone.setText(Integer.toString(buyer_phone));

        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.makeAppointmentBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MyListingActivity.class);
                startActivity(back);

            }
        });

    }
}
