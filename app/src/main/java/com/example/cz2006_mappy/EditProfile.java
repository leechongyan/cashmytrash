package com.example.cz2006_mappy;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    EditText curPass;
    EditText newPass;
    EditText newerPass;
    ImageView profPic;
    TextView textBox;
    String profilePath;
    User user;
    Button conf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // how does db layer work?
        final UserDatabase db = UserDatabase.getAppDatabase(this);
        final UserDAO userDAO = db.userDao();
        // 1 imageview; 1 textview; 3 editText
        curPass = findViewById(R.id.changepassword);
        newPass = findViewById(R.id.changepassword2);
        newerPass = findViewById(R.id.changepassword3);
        profPic = findViewById(R.id.editProfilePic);
        textBox = findViewById(R.id.uploadProfilePic);
        conf = findViewById(R.id.confirm);
        user = userDAO.getUser("123");

        // taken from registration; intent: load gallery, select desired profile picture
        profPic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                // userdao requires email - how to get?
                user.setImagePath(profilePath);
            }
        });

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
                    Intent intent = new Intent(EditProfile.this, HomePage.class);
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
                        Intent intent = new Intent(EditProfile.this, HomePage.class);
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
}
