package com.example.cz2006_mappy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText e1, e2;
    TextView t1, t2;
    Button b1;
    String email, password;
    SharedPreferences pref;
    Intent i1, i2, i3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        final UserDAO userDAO = db.userDao();

        //for fake login
        User fake = new User("123", "123", "123", 123, 0, 0, "123");

        User vaild = userDAO.getUser(fake.getEmailaddress());
        if (vaild == null) {
            userDAO.insert(fake);
        }

        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        t1 = (TextView) findViewById(R.id.createaccount);
        t2 = (TextView) findViewById(R.id.passwordforget);
        e1 = (EditText) findViewById(R.id.email);
        e2 = (EditText) findViewById(R.id.password);
        b1 = (Button) findViewById(R.id.login);
        email = e1.getText().toString();
        password = e2.getText().toString();
        t1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i2 = new Intent(Login.this, Registration.class);
                startActivity(i2);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User user = userDAO.getUser(email);
                user.setPassword("1234");
                userDAO.update(user);
                new SendMail().execute("");
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = e1.getText().toString();
                password = e2.getText().toString();
                User user = userDAO.getUser(email);
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(user.getPassword())) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", user.getUsername());
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putInt("phone", user.getPhone());
                        editor.putString("target", Double.toString(user.getTarget()));
                        editor.putString("savings", Double.toString(user.getSavings()));
                        editor.commit();
                        Intent i1 = new Intent(Login.this, HomeActivity.class);
                        startActivity(i1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                        t2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private class SendMail extends AsyncTask<String, Integer, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Login.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        protected Void doInBackground(String... params) {
            Mail m = new Mail("CashMyTrash", "abc123%%%");

            String[] toArr = {email};
            m.setTo(toArr);
            m.setFrom("leechongyan@gmail.com");
            m.setSubject("Reset Password");
            m.setBody("Your new password is 1234. Please reset upon logging in!");
            try {
                if (m.send()) {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 1;
                    handler.sendMessage(msg);
                } else {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 2;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }
    }

    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1)
                Toast.makeText(getApplicationContext(), "Email was sent successfully.", Toast.LENGTH_LONG).show();
            else if (msg.arg1 == 2)
                Toast.makeText(getApplicationContext(), "Email was not sent.", Toast.LENGTH_LONG).show();
        }
    };
}