package com.example.cz2006_mappy;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContactSeller extends AppCompatActivity {

    final AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
    final UserDAO mUserDao = db.userDao();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_seller);
        String user_id = getIntent().getExtras().getString("user_id");
        User seller = mUserDao.getUser(user_id);
        String seller_username = seller.getUsername();
        int seller_phone = seller.getPhone();

        TextView sellerUsername = (TextView) findViewById(R.id.sellerUsername);
        TextView sellerPhone = (TextView) findViewById(R.id.sellerPhone);
        TextView sellerEmail = (TextView) findViewById(R.id.sellerEmail);
        ImageView sellerPhoto = (ImageView) findViewById(R.id.sellerPhoto);

        sellerUsername.setText(seller_username);
        sellerEmail.setText(user_id);
        sellerPhone.setText(Integer.toString(seller_phone));
        sellerPhoto.setImageDrawable(getResources().getDrawable(R.drawable.profilepic));
        //TODO: set seller profile picture

        FloatingActionButton contactBackButton = (FloatingActionButton) findViewById(R.id.contactBackButton);
        contactBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}
