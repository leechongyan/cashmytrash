package com.example.cz2006_mappy;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        sellerUsername.setText("Seller Username: " + seller_username);
        sellerEmail.setText("Seller E-mail: " + user_id);
        sellerPhone.setText("Seller Phone: " + Integer.toString(seller_phone));
    }
    public void back(View v){
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
