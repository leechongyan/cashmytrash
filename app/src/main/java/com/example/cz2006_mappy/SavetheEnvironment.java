package com.example.cz2006_mappy;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SavetheEnvironment extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView overall25, north25, south25, east25, west25;
    TextView psinorth, psisouth, psieast, psiwest, psinational, psioverall;
    TextView uvstat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saveenv);
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
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.header_name);
        TextView navEmail = (TextView) headerView.findViewById(R.id.header_email);
        SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
        navUsername.setText(channel.getString("username", ""));
        navEmail.setText(channel.getString("email", ""));
        String url = "https://api.data.gov.sg/v1/environment/pm25";

        overall25 = (TextView) findViewById(R.id.overall25);
        north25 = (TextView) findViewById(R.id.north25);
        south25 = (TextView) findViewById(R.id.south25);
        east25 = (TextView) findViewById(R.id.east25);
        west25 = (TextView) findViewById(R.id.west25);

        psioverall = (TextView) findViewById(R.id.psioverall);
        psinational = (TextView) findViewById(R.id.psinational);
        psinorth = (TextView) findViewById(R.id.psinorth);
        psisouth = (TextView) findViewById(R.id.psisouth);
        psieast = (TextView) findViewById(R.id.psieast);
        psiwest = (TextView) findViewById(R.id.psiwest);

        uvstat = (TextView) findViewById(R.id.uvstat);

        fetchData fetch = new fetchData();
        fetch.execute();
    }

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
            Intent change_password = new Intent(this, EditProfile.class);
            startActivity(change_password);
        } else if (id == R.id.nav_save_the_environment) {
            //TODO: SAVE THE ENVIRONMENT ACTIVITY
            Intent convert = new Intent(this, SavetheEnvironment.class);
            startActivity(convert);
        } else if (id == R.id.nav_give_us_feedback) {
            //TODO: GIVE US FEEDBACK ACTIVITY
            Intent feedback = new Intent(this, FeedbackForm.class);
            startActivity(feedback);
        }else if (id == R.id.logout) {
            //TODO: GIVE US FEEDBACK ACTIVITY
            Intent logout = new Intent(this, Login.class);
            startActivity(logout);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class fetchData extends AsyncTask<Void, Void, Void>
    {
        String data;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.data.gov.sg/v1/environment/pm25");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line!= null)
                {
                    line = bufferedReader.readLine();
                    if (line!=null)
                    {
                        data = data+line;
                    }
                }
                JSONObject obj = new JSONObject(data.substring(4));
                JSONObject json = obj.getJSONArray("items").getJSONObject(0).getJSONObject("readings").getJSONObject("pm25_one_hourly");

                overall25.setText(obj.getJSONObject("api_info").getString("status"));
                north25.setText("North: "+json.getString("north"));
                south25.setText("South: "+json.getString("south"));
                east25.setText("East: "+json.getString("east"));
                west25.setText("West: "+json.getString("west"));


                url = new URL("https://api.data.gov.sg/v1/environment/psi");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                line = "";
                while(line!= null)
                {
                    line = bufferedReader.readLine();
                    if (line!=null)
                    {
                        data = data+line;
                    }
                }
                obj = new JSONObject(data.substring(4));
                json = obj.getJSONArray("items").getJSONObject(0).getJSONObject("readings").getJSONObject("pm25_one_hourly");

                psioverall.setText(obj.getJSONObject("api_info").getString("status"));
                psinational.setText("Central: "+json.getString("central"));
                psinorth.setText("North: "+json.getString("north"));
                psisouth.setText("South: "+json.getString("south"));
                psieast.setText("East: "+json.getString("east"));
                psiwest.setText("West: "+json.getString("west"));


                url = new URL("https://api.data.gov.sg/v1/environment/uv-index");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                line = "";
                while(line!= null)
                {
                    line = bufferedReader.readLine();
                    if (line!=null)
                    {
                        data = data+line;
                    }
                }
                obj = new JSONObject(data.substring(4));
                uvstat.setText(obj.getJSONObject("api_info").getString("status"));


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}