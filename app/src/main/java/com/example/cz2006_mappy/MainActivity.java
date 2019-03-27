package com.example.cz2006_mappy;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInMemoryRoomDatabases();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

//        Item test = new Item(0, "some item name", "some item description",
//                500.0, 12345,45,"elbert999");
//        mItemViewModel.insert(test);

        //Show item in gridview
        final GridView gridView = (GridView) findViewById(R.id.listing_grid_view);

        mItemViewModel.getAllItems().observe(MainActivity.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                gridView.setAdapter(new ItemAdapter(MainActivity.this, items));
            }

        });

        //onItemClick
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showItemDetailActivity = new Intent(getApplicationContext(), ItemDetailActivity.class);

                View v = gridView.getChildAt(position);
                TextView itemName = (TextView) v.findViewById(R.id.grid_item_name);
                TextView itemUsername = (TextView) v.findViewById(R.id.grid_item_username);
                TextView itemPrice = (TextView) v.findViewById(R.id.grid_item_price);
                TextView itemDescription = (TextView) v.findViewById(R.id.grid_item_description);


                showItemDetailActivity.putExtra("item_detail_username",itemUsername.getText());
                showItemDetailActivity.putExtra("item_detail_name",itemName.getText());
                showItemDetailActivity.putExtra("item_detail_price",itemPrice.getText() );
                showItemDetailActivity.putExtra("item_detail_description", itemDescription.getText());
                startActivity(showItemDetailActivity);
            }
        });




        //Search panel
        SearchView searchView = (SearchView) findViewById(R.id.listing_search_field);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {

                List<Item> items = mItemViewModel.getSearchedItems(query);
                if(items != null) {
                    gridView.setAdapter(new ItemAdapter(MainActivity.this, items));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")) {
                    mItemViewModel.getAllItems().observe(MainActivity.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(@Nullable List<Item> items) {
                            gridView.setAdapter(new ItemAdapter(MainActivity.this, items));
                        }

                    });
                }
                return false;
            }
        });

        //Create Item Activity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_item_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createItemActivity = new Intent(getApplicationContext(), createItemActivity.class);
                //TODO: Pass User Object
                startActivity(createItemActivity);
            }
        });



        //Navigation Panel
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    public static void setInMemoryRoomDatabases(SupportSQLiteDatabase... database) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Class[] argTypes = new Class[]{HashMap.class};
                HashMap<String, SupportSQLiteDatabase> inMemoryDatabases = new HashMap<>();
                // set your inMemory databases
                inMemoryDatabases.put("InMemoryOne.db", database[0]);
                Method setRoomInMemoryDatabase = debugDB.getMethod("setInMemoryRoomDatabases", argTypes);
                setRoomInMemoryDatabase.invoke(null, inMemoryDatabases);
            } catch (Exception ignore) {

            }
        }
    }
}
