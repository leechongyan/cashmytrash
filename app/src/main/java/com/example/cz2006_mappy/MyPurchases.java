package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyPurchases extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ItemViewModel mItemViewModel;
    private ItemTransactionViewModel mItemTransactionViewModel;
    private TransactionManager manager = new TransactionManager();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        String username = pref.getString("username","Anon");
        mItemViewModel = ViewModelProviders.of(MyPurchases.this).get(ItemViewModel.class);
        mItemTransactionViewModel = ViewModelProviders.of(this).get(ItemTransactionViewModel.class);
        final GridView gridView = (GridView) findViewById(R.id.grid_my_purchases_view);
        List<Integer> items_id= mItemTransactionViewModel.getItemTransaction(username);
        List<Item> items = manager.getItems(items_id);
        gridView.setAdapter(new ItemTransactionAdapter(MyPurchases.this, items));


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
        getMenuInflater().inflate(R.menu.main, menu);
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
        } else if (id == R.id.nav_listing) {
            //TODO: LISTING ACTIVITY
            //THIS ONE
            Intent listing = new Intent(this.getApplicationContext(), HomePage.class);
            startActivity(listing);
        } else if (id == R.id.nav_my_listing) {
            //TODO: MY LISTING ACTIVITY
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
        } else if (id == R.id.nav_give_us_feedback) {
            //TODO: GIVE US FEEDBACK ACTIVITY
            Intent feedback = new Intent(this, FeedbackForm.class);
            startActivity(feedback);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void insertToken(View view){
        ConstraintLayout v = (ConstraintLayout) view.getParent();
        TextView id = (TextView) v.findViewById(R.id.grid_item_id_my_purchases);

        Intent token = new Intent(this, InsertToken.class);
        token.putExtra("item_id_my_purchases", Integer.parseInt(id.getText().toString()));

        startActivity(token);
    }

    public void contactSeller(View view){
        ConstraintLayout v = (ConstraintLayout) view.getParent();
        TextView id = (TextView) v.findViewById(R.id.grid_item_id_my_purchases);

        Item item = mItemViewModel.getItem(Integer.parseInt(id.getText().toString()));
        String seller_id = item.getSeller_id();
        Intent contact = new Intent(getApplicationContext(),ContactSeller.class);
        contact.putExtra("user_id", seller_id);
        startActivity(contact);
    }

    public void delete(View view){
        ConstraintLayout v = (ConstraintLayout) view.getParent();
        TextView id = (TextView) v.findViewById(R.id.grid_item_id_my_purchases);
        int item_id = Integer.parseInt(id.getText().toString());
        manager.delete(item_id);
        Toast.makeText(getApplicationContext(),"Item has been deleted from your purchases!",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());

    }
}
