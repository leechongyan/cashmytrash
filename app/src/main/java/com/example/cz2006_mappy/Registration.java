package com.example.cz2006_mappy;

import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//https://www.youtube.com/watch?v=1WPAXHhG6u0
//C:\Users\leech\AppData\Local\Android\Sdk\extras\intel\Hardware_Accelerated_Execution_Manager
public class Registration extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;

    TextView t1, profilepic;
    EditText e1, e2, e3, e4, e5;
    Button b1;
    Intent i;

    ImageView imageUploaded;
    String profilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        final UserDAO userDAO = db.userDao();
        t1=(TextView)findViewById(R.id.existingacc);
        e1=(EditText)findViewById(R.id.username);
        e2=(EditText)findViewById(R.id.firstpassword);
        e3=(EditText)findViewById(R.id.secondpassword);
        e4=(EditText)findViewById(R.id.email);
        e5=(EditText)findViewById(R.id.phone);
        b1=(Button)findViewById(R.id.register);
        profilepic = (TextView)findViewById(R.id.pic_status);

        imageUploaded = (ImageView) findViewById(R.id.profilepic);

        imageUploaded.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });


        t1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                i = new Intent(Registration.this, Login.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                String s3=e3.getText().toString();
                String s4=e4.getText().toString();
                String s5=e5.getText().toString();
                if (s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")||s5==null){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }else{
                    if(s1.length()<4||s1.length()>19){
                        e1.setError("Username must be between 3 to 20 letters long");
                    }
                    if(s5.length()!=8){
                        e5.setError("Phone no is invalid");
                    }
                    if(s2.length()<8&&!isValidPassword(s2)){
                        e2.setError("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character");
                    }
                    if(!isEmailValid(s4)){
                        e4.setError("Email is invalid");
                    }
//                    if(profilepic.getText() != "Upload Success"){
//                        profilepic.setText("Upload a Image before registering");
//                    }

                    else {
                        int i5 = Integer.parseInt(s5);
                        if (s2.equals(s3)) {


                            User user = new User(s4, s1, s2, i5,0,0,profilePath);
                            if (userDAO.getUser(s4) == null) {
                                userDAO.insert(user);
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                i = new Intent(Registration.this, Login.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Email has already existed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            imageUploaded.setImageURI(selectedImage);
            profilepic.setText("Upload Success");
            String profilePath = selectedImage.toString();

        }
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}