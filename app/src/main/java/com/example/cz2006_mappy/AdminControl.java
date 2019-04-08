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
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminControl extends AppCompatActivity {

    private ItemViewModel mItemViewModel;
    private FeedbackViewModel mFeedbackViewModel;

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

        mItemViewModel = ViewModelProviders.of(AdminControl.this).get(ItemViewModel.class);

        //Show item in gridview
        final GridView gridView = (GridView) findViewById(R.id.listing_list_view);

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
                    case 2:
                        selectTab(2);
                        break;
                    default:
                        selectTab(0);
                }
            }

            public void selectTab(int tabNumber){
                if (tabNumber == 0){
                    //to implement User viewmodel and repo, link to dao
                    return;
                }
                if (tabNumber == 1){
                    Log.e("tab1 selected in admin", "--------------");

                    mItemViewModel = ViewModelProviders.of(AdminControl.this).get(ItemViewModel.class);

                    final GridView gridView = (GridView) findViewById(R.id.listing_list_view);
                    mItemViewModel.getAllItems().observe(AdminControl.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(@Nullable List<Item> items) {
                            Log.e("onchange is runned", "--------------");
                            gridView.setAdapter(new ItemAllAdapter(AdminControl.this, items));
                        }
                    });
                }
                else if (tabNumber == 2){
//                    mFeedbackViewModel = ViewModelProviders.of(AdminControl.this).get(FeedbackViewModel.class);
//
//                    final GridView gridView = (GridView) findViewById(R.id.listing_list_view);
//                    mFeedbackViewModel.getAllFeedbacks().observe(AdminControl.this, new Observer<List<Feedback>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Feedback> feedbacks) {
//                            gridView.setAdapter(new FeedbackAdaptor(AdminControl.this, feedbacks));
//                        }
//                    !!!!Need to implement feedbackadaptor
//                    });
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
}

