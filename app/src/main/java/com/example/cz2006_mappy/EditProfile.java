package com.example.cz2006_mappy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int RESULT_LOAD_IMAGE = 1;
    EditText curPass;
    EditText newPass;
    EditText newerPass;
    ImageView profPic;
    TextView textBox;
    String profilePath;
    Button conf;
    SharedPreferences pref;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // how does db layer work?
        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        final UserDAO userDAO = db.userDao();
        SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);
        String email = channel.getString("email","");
        final User user = userDAO.getUser(email);
        // 1 imageview; 1 textview; 3 editText
        curPass = findViewById(R.id.changepassword);
        newPass = findViewById(R.id.changepassword2);
        newerPass = findViewById(R.id.changepassword3);
        profPic = findViewById(R.id.editProfilePic);
        textBox = findViewById(R.id.uploadProfilePic);
        conf = findViewById(R.id.confirm);
        // pref causes this page to crash
        // taken from registration; intent: load gallery, select desired profile picture
        profPic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                // userdao requires email - how to get?
                user.setImagePath(profilePath);
            }
        });

        // side-drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // confirmation button
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curP = curPass.getText().toString();
                String newP = newPass.getText().toString();
                String newerP = newerPass.getText().toString();
                // logic for password here - refer to registration; userDAO update method lacking
                // user uploads profile pic and does nothing else
                if (curP.equals("") && newP.equals("") && newerP.equals("")) {
                    userDAO.update(user);
                    Toast.makeText(getApplicationContext(), "Profile Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfile.this, HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    if (curP.equals("") || newP.equals("") || newerP.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter all the fields to change your password!", Toast.LENGTH_SHORT).show();
                    }
                    // all fields are filled
                    if (!(curP.equals(user.getPassword()))) {
                        curPass.setError("Please make sure you have entered your current password correctly!");
                    }
                    if (newP.length() < 8 && !isValidPassword(newP)) {
                        newPass.setError("Password must contain minimum 8 characters, at least 1 Alphabet, 1 Number and 1 Special Character");
                    }
                    if (newP.length() < 8 && !isValidPassword(newP)) {
                        newerPass.setError("Password must contain minimum 8 characters, at least 1 Alphabet, 1 Number and 1 Special Character");
                    }
                    else if (!(newP.equals(newerP))) {
                        Toast.makeText(getApplicationContext(), "Please ensure that your new passwords match!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // TODO: persistence (user password not updating)
                        // userDAO.update(user);
                        user.setPassword(newP);
                        Toast.makeText(getApplicationContext(), "Profile Updated!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfile.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    // taken from registration
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            profPic.setImageURI(selectedImage);
            textBox.setText("Upload Success");
            String profilePath = selectedImage.toString();
        }
    }

    // taken from registration - regex matcher for valid password
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
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
