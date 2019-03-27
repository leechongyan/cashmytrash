package com.example.cz2006_mappy;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyListingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Navigation Panel
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mItemViewModel = ViewModelProviders.of(MyListingActivity.this).get(ItemViewModel.class);

        //Show item in gridview
        final GridView gridView = (GridView) findViewById(R.id.listing_grid_view_my_listing);

        mItemViewModel.getAllItems().observe(MyListingActivity.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                gridView.setAdapter(new ItemAllAdapter(MyListingActivity.this, items));
            }

        });

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        // so that tabs are ready to have operations performed on it.
        tabs.post(new Runnable() {
            @Override
            public void run() {
                TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
                tabs.getTabAt(0).select();
            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int  numTab =  tab.getPosition();
                switch(numTab){
                    case 0:
                        selectTab(0);
                        break;
                    case 1:
                        selectTab(1);
                        break;
                    default:
                        selectTab(0);
                }
            }

            public void selectTab(int tabNumber){
                if (tabNumber == 0){
                    mItemViewModel = ViewModelProviders.of(MyListingActivity.this).get(ItemViewModel.class);

                    //Show item in gridview
                    final GridView gridView = (GridView) findViewById(R.id.listing_grid_view_my_listing);

                    mItemViewModel.getSoldItems().observe(MyListingActivity.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(@Nullable List<Item> items) {
                            gridView.setAdapter(new ItemAllAdapter(MyListingActivity.this, items));
                        }

                    });
                }
                else if (tabNumber == 1){ // TODO: to deliver not implemented yet
                    mItemViewModel = ViewModelProviders.of(MyListingActivity.this).get(ItemViewModel.class);

                    //Show item in gridview
                    final GridView gridView = (GridView) findViewById(R.id.listing_grid_view_my_listing);

                    mItemViewModel.getSoldItems().observe(MyListingActivity.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(@Nullable List<Item> items) {
                            gridView.setAdapter(new ItemToDeliverAdapter(MyListingActivity.this, items));
                        }

                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
                if(tab.getPosition() == 0){
                    selectTab(tab.getPosition());
                    tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            selectTab(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });
                }
            }
        });
    }

    public void displayConfirmationBox(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MyListingActivity.this);
        builder.setTitle("Deleting Items Being Sold");
        builder.setMessage("Are you sure you want to remove the item?");
        builder.setCancelable(false);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView grid_item_all = (TextView) findViewById(R.id.grid_item_id_all);
                String id = grid_item_all.getText().toString();
                mItemViewModel.deleteSoldItem(Integer.parseInt(id));
                Toast.makeText(getApplicationContext(),"Item Deleted", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Cancelled", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void makeAppointment(View view){
        Intent buyerUsername = new Intent(getApplicationContext(), MakeAppointmentActivity.class);
        buyerUsername.putExtra("com.example.cz2006.mappy.buyerUsername", "Buyer Username");
        startActivity(buyerUsername);

        Intent contactNumber = new Intent(getApplicationContext(), MakeAppointmentActivity.class);
        contactNumber.putExtra("com.example.cz2006.mappy.contactNumber", "Contact Number");
        startActivity(contactNumber);

        Intent emailAddress = new Intent(getApplicationContext(), MakeAppointmentActivity.class);
        emailAddress.putExtra("com.example.cz2006.mappy.emailAddress", "Email Address");
        startActivity(emailAddress);
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
        getMenuInflater().inflate(R.menu.my_listing, menu);
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
            //THIS ONE
        } else if (id == R.id.nav_my_listing) {
            //TODO: MY LISTING ACTIVITY
            Intent myListing = new Intent(this, MyListingActivity.class);
            startActivity(myListing);
        } else if (id == R.id.nav_my_purchases) {
            //TODO: MY PURCHASES ACTIVITY
        } else if (id == R.id.nav_convert_to_cash) {
            //TODO: CONVERT TO CASH ACTIVITY
        } else if (id == R.id.nav_change_password) {
            //TODO: CHANGE PASSWORD ACTIVITY
        } else if (id == R.id.nav_save_the_environment) {
            //TODO: SAVE THE ENVIRONMENT ACTIVITY
        } else if (id == R.id.nav_give_us_feedback) {
            //TODO: GIVE US FEEDBACK ACTIVITY
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
