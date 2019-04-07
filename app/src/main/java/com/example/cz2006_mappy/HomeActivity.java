package com.example.cz2006_mappy;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText addEditText;
    private Button addButton;
    String savings;
    Double total = 0.0;
    Double listingsSold = 0.0;
    private ItemTransactionViewModel mItemTransactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        UserDAO userDAO = db.userDao();
        SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
        String email = channel.getString("email","");
        User user = userDAO.getUser(email);

        TextView targetTextView = (TextView) findViewById(R.id.targetTextView);
        targetTextView.setText(Double.toString(user.getTarget()));

        TextView savingsTextView = (TextView) findViewById(R.id.savingsTextView);
        savingsTextView.setText(Double.toString(user.getSavings()));

        if(getIntent().hasExtra("com.example.cz2006.mappy.displayTarget")){
            Double toDisplay = getIntent().getExtras().getDouble("com.example.cz2006.mappy.displayTarget");
            targetTextView.setText(Double.toString(toDisplay));
        }
        if(getIntent().hasExtra("com.example.cz2006.mappy.displaySavings")){
            String toDisplay = getIntent().getExtras().getString("com.example.cz2006.mappy.displaySavings");
            savingsTextView.setText(toDisplay);
        }

        Button setTargetButton = (Button) findViewById(R.id.setTargetButton);

        setTargetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), SetTargetActivity.class);
                startIntent.putExtra("com.example.cz2006.mappy.setTarget", "test");
                startActivity(startIntent);
            }
        });

        addEditText = (EditText) findViewById(R.id.addEditText);
        addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == addButton.getId()){
                    TextView targetTextView = (TextView) findViewById(R.id.targetTextView);
                    String status = validSavings(addEditText.getText().toString());
                    if(status.equals("success")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setTitle("Adding to Savings");
                        builder.setMessage("Are you sure you want to add SGD " + Double.toString(Double.parseDouble(addEditText.getText().toString())) + " to your savings?");
                        builder.setCancelable(false);

                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                savings = addEditText.getText().toString();
                                total = total + Double.parseDouble(savings);

                                AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
                                UserDAO userDAO = db.userDao();
                                SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
                                String email = channel.getString("email","");
                                User user = userDAO.getUser(email);
                                user.setSavings(total);
                                userDAO.update(user);
                                SharedPreferences.Editor editor = channel.edit();
                                editor.putString("savings", Double.toString(user.getSavings()));
                                editor.commit();

                                TextView savingsTextView = (TextView) findViewById(R.id.savingsTextView);
                                savingsTextView.setText(Double.toString(user.getSavings()));
                                Toast.makeText(getApplicationContext(),"Added to savings", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // listings sold
        String user_id = user.getEmailaddress();
        ItemDao itemDao = db.itemDao();

        mItemTransactionViewModel = ViewModelProviders.of(HomeActivity.this).get(ItemTransactionViewModel.class);
        Integer numDelivered = mItemTransactionViewModel.countDelivered(user_id);

        if(numDelivered > 0){
            List<Integer> item_delivered_id = mItemTransactionViewModel.getItemsDelivered(user_id);

            for(int i =0; i< item_delivered_id.size(); i++){
                int item_id = item_delivered_id.get(i);
                listingsSold = listingsSold + itemDao.getPriceItemsDelivered(item_id);
            }

            TextView listingsSoldTextView = (TextView) findViewById(R.id.listingsSoldTextView);
            listingsSoldTextView.setText(Double.toString(listingsSold));
        }
        else{
            TextView listingsSoldTextView = (TextView) findViewById(R.id.listingsSoldTextView);
            listingsSoldTextView.setText("0.00");
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //TODO: HOME ACTIVITY
            Intent Home = new Intent(this, HomeActivity.class);
            startActivity(Home);
        } else if (id == R.id.nav_listing) {
            //TODO: LISTING ACTIVITY
            Intent Listing = new Intent(this, ListingActivity.class);
            startActivity(Listing);
        } else if (id == R.id.nav_my_listing) {
            //TODO: MY LISTING ACTIVITY
            Intent myListing = new Intent(this, MyListingActivity.class);
            startActivity(myListing);
        } else if (id == R.id.nav_my_purchases) {
            //TODO: MY PURCHASES ACTIVITY
            Intent purchases = new Intent(this.getApplicationContext(),MyPurchases.class);
            startActivity(purchases);
        } else if (id == R.id.nav_convert_to_cash) {
            //TODO: CONVERT TO CASH ACTIVITY
            Intent convert = new Intent(this, ConvertToCashNew.class);
            startActivity(convert);
        } else if (id == R.id.nav_change_password) {
            //TODO: CHANGE PASSWORD ACTIVITY
        } else if (id == R.id.nav_save_the_environment) {
            //TODO: SAVE THE ENVIRONMENT ACTIVITY
            Intent convert = new Intent(this, SavetheEnvironment.class);
            startActivity(convert);
        } else if (id == R.id.nav_give_us_feedback) {
            //TODO: GIVE US FEEDBACK ACTIVITY
            Intent feedback = new Intent(this, FeedbackForm.class);
            startActivity(feedback);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String validSavings(String savings){
        if(TextUtils.isEmpty(savings) | savings == null | savings.isEmpty() | savings.length() == 0){
            return "Savings cannot be empty";
        }
        if(savings.equals(".")){
            return "Savings invalid";
        }
        return "success";
    }
}
