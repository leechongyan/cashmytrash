package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyListingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ItemViewModel mItemViewModel;
    private ItemTransactionViewModel mItemTransactionViewModel;

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

                    AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
                    UserDAO userDAO = db.userDao();
                    SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
                    String email = channel.getString("email","");
                    User user = userDAO.getUser(email);

                    gridView.setAdapter(new ItemAllAdapter(MyListingActivity.this, mItemViewModel.getSoldItems(user.getEmailaddress())));

//                    mItemViewModel.getSoldItems(user.getEmailaddress()).observe(MyListingActivity.this, new Observer<List<Item>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Item> items) {
//                            gridView.setAdapter(new ItemAllAdapter(MyListingActivity.this, items));
//                        }
//
//                    });
                }
                else if (tabNumber == 1){
                    mItemTransactionViewModel = ViewModelProviders.of(MyListingActivity.this).get(ItemTransactionViewModel.class);

                    //Show item in gridview
                    final GridView gridView = (GridView) findViewById(R.id.listing_grid_view_my_listing);

                    AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
                    UserDAO userDAO = db.userDao();
                    ItemDao itemDao = db.itemDao();
                    SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
                    String email = channel.getString("email","");
                    User user = userDAO.getUser(email);

                    // for loop
                    List<Integer> item_to_deliver_id = mItemTransactionViewModel.getItemIdToDeliver(user.getEmailaddress());
                    List<Item> items = new ArrayList<>();
                    for(int i =0; i< item_to_deliver_id.size(); i++){
                        int item_id = item_to_deliver_id.get(i);
                        items.add(itemDao.getItem(item_id));
                    }
                    gridView.setAdapter(new ItemToDeliverAdapter(MyListingActivity.this, items));

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
                if(tab.getPosition() == 1){
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

    public void deleteItemInAllTab(View view){
        mItemViewModel = ViewModelProviders.of(MyListingActivity.this).get(ItemViewModel.class);

        RelativeLayout v = (RelativeLayout) view.getParent().getParent();
        TextView grid_item_all = (TextView) v.findViewById(R.id.grid_item_id_all);
        String id = grid_item_all.getText().toString();

        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        UserDAO userDAO = db.userDao();
        SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
        String email = channel.getString("email","");
        User user = userDAO.getUser(email);

        mItemViewModel.deleteSoldItem(Integer.parseInt(id), user.getEmailaddress());
        Toast.makeText(getApplicationContext(),"Item Deleted", Toast.LENGTH_LONG).show();
    }

    public void deleteItemInToDeliverTab(View view){
        mItemViewModel = ViewModelProviders.of(MyListingActivity.this).get(ItemViewModel.class);
        mItemTransactionViewModel = ViewModelProviders.of(MyListingActivity.this).get(ItemTransactionViewModel.class);

        RelativeLayout v = (RelativeLayout) view.getParent().getParent();
        TextView grid_item_id_to_deliver = (TextView) v.findViewById(R.id.grid_item_id_to_deliver);
        String id = grid_item_id_to_deliver.getText().toString();

        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        UserDAO userDAO = db.userDao();
        SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
        String email = channel.getString("email","");
        User user = userDAO.getUser(email);

        // delete from ToDeliver: delete from Item and Transaction databases
        mItemViewModel.deleteToDeliverItem(Integer.parseInt(id), user.getEmailaddress());
        mItemTransactionViewModel.deleteToDeliverTransaction(Integer.parseInt(id), user.getEmailaddress());

        Toast.makeText(getApplicationContext(),"Item Deleted", Toast.LENGTH_LONG).show();

        GridView gridView = (GridView) findViewById(R.id.listing_grid_view_my_listing);
        ItemDao itemDao = db.itemDao();

        // for loop
        List<Integer> item_to_deliver_id = mItemTransactionViewModel.getItemIdToDeliver(user.getEmailaddress());
        List<Item> items = new ArrayList<>();
        for(int i =0; i< item_to_deliver_id.size(); i++){
            int item_id = item_to_deliver_id.get(i);
            items.add(itemDao.getItem(item_id));
        }
        gridView.setAdapter(new ItemToDeliverAdapter(MyListingActivity.this, items));
    }

    public void makeAppointment(View view){
        mItemTransactionViewModel = ViewModelProviders.of(MyListingActivity.this).get(ItemTransactionViewModel.class);

        RelativeLayout v = (RelativeLayout) view.getParent().getParent();
        TextView id = (TextView) v.findViewById(R.id.grid_item_id_to_deliver);

        ItemTransaction item = mItemTransactionViewModel.getItemTransaction(Integer.parseInt(id.getText().toString()));
        String buyer_id = item.getBuyer_id();
        Intent contactBuyer = new Intent(getApplicationContext(),MakeAppointmentActivity.class);
        contactBuyer.putExtra("buyer_id", buyer_id);
        startActivity(contactBuyer);
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
        } else if (id == R.id.nav_give_us_feedback) {
            //TODO: GIVE US FEEDBACK ACTIVITY
            Intent feedback = new Intent(this, FeedbackForm.class);
            startActivity(feedback);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
