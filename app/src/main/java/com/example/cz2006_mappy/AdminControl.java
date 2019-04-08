package com.example.cz2006_mappy;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class AdminControl extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ItemViewModel mItemViewModel;
    private ItemTransactionViewModel mItemTransactionViewModel;
    private FeedbackViewModel mFeedbackViewModel;
    private UserViewModel mUserViewModel;
    private static final int USER = 0;
    private static final int ITEM = 1;
    private static final int FEEDBACK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);
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

        mItemViewModel = ViewModelProviders.of(AdminControl.this).get(ItemViewModel.class);

        //Show item in gridview
        final GridView gridView = (GridView) findViewById(R.id.listing_list_view);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        // so that tabs are ready to have operations performed on it.
        tabs.post(new Runnable() {
            @Override
            public void run() {
                TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
                tabs.getTabAt(USER).select();
            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int  numTab =  tab.getPosition();
                switch(numTab){
                    case 0:
                        selectTab(USER);
                        break;
                    case 1:
                        selectTab(ITEM);
                        break;
                    case 2:
                        selectTab(FEEDBACK);
                        break;
                    default:
                        selectTab(0);
                }
            }

            public void selectTab(int tabNumber){
                if (tabNumber == USER){
                    //to implement User viewmodel and repo, link to dao
                    mUserViewModel = ViewModelProviders.of(AdminControl.this).get(UserViewModel.class);

                    final GridView gridView = (GridView) findViewById(R.id.listing_list_view);
                    mUserViewModel.getAllUsers().observe(AdminControl.this, new Observer<List<User>>() {
                        @Override
                        public void onChanged(@Nullable List<User> users) {
                            gridView.setAdapter(new UserAdaptor(AdminControl.this, users));
                        }

                    });
                    return;
                }
                if (tabNumber == ITEM){
                    mItemViewModel = ViewModelProviders.of(AdminControl.this).get(ItemViewModel.class);

                    final GridView gridView = (GridView) findViewById(R.id.listing_list_view);
                    mItemViewModel.getAllItems().observe(AdminControl.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(@Nullable List<Item> items) {
                            gridView.setAdapter(new ItemAllAdapter(AdminControl.this, items));
                        }

                    });
                    return;
                }
                else if (tabNumber == FEEDBACK){
                    mFeedbackViewModel = ViewModelProviders.of(AdminControl.this).get(FeedbackViewModel.class);
                    final GridView gridView = (GridView) findViewById(R.id.listing_list_view);
                    mFeedbackViewModel.getAllFeedbacks().observe(AdminControl.this, new Observer<List<Feedback>>() {
//                        @Override
                        public void onChanged(@Nullable List<Feedback> feedbacks) {
                            gridView.setAdapter(new FeedbackAdapter(AdminControl.this, feedbacks));
                        }
//                    !!!!Need to implement feedbackadaptor
                    });
                    return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
                if(tab.getPosition() == USER){
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
                if(tab.getPosition() == ITEM){
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
    public void deleteFeedback(View view) {
        mFeedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        UserDAO userDAO = db.userDao();
        FeedbackDao feedbackDAO = db.feedbackDao();
        SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
        String email = channel.getString("email","");
        User user = userDAO.getUser(email);

        RelativeLayout v = (RelativeLayout) view.getParent().getParent();
        Button grid_trash_all = (Button) v.findViewById(R.id.grid_item_trash_all);
        int id = Integer.parseInt(grid_trash_all.getText().toString());

        mFeedbackViewModel.deleteFeedback(feedbackDAO.getFeedback(id));
        Toast.makeText(getApplicationContext(),"Item Deleted", Toast.LENGTH_LONG).show();

        GridView gridView = (GridView) findViewById(R.id.listing_grid_view_my_listing);
        // changed from mylistingactivity
        // new FeedbackAdapter(AdminControl.this, feedbacks))
        gridView.setAdapter(new FeedbackAdapter(this, mFeedbackViewModel.getAllFeedbacks().getValue()));
    }
    public void deleteUser(View view){
        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        UserDAO userDAO = db.userDao();
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        RelativeLayout v = (RelativeLayout) view.getParent().getParent();
        TextView grid_item_email = (TextView) v.findViewById(R.id.grid_item_email);
        String email = grid_item_email.getText().toString();
        User user = userDAO.getUser(email);
        if (userDAO.delete(user)!=0){
            Toast.makeText(getApplicationContext(),"User Deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"User not deleted", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteItemAdmin(View view){
        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        ItemDao itemDAO = db.itemDao();
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        RelativeLayout v = (RelativeLayout) view.getParent().getParent();
        TextView grid_item_id_all = (TextView) v.findViewById(R.id.grid_item_id_all);
        String id = grid_item_id_all.getText().toString();
        int id2 = Integer.parseInt(id);
        if (itemDAO.delete(itemDAO.getItem(id2))!=0){
            Toast.makeText(getApplicationContext(),"Item Deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"Item not deleted", Toast.LENGTH_LONG).show();
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

