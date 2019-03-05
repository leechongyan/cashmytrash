package com.example.irecycle;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
    DatabaseHelper db;
    SharedPreferences pref;
    Intent i1, i2, i3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
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
                /*i3 = new Intent(android.content.Intent.ACTION_SEND);
                i3.setData(Uri.parse("mailto:"));
                String recipient = e1.getText().toString();
                i3.putExtra(android.content.Intent.EXTRA_EMAIL, recipient);
                i3.putExtra(android.content.Intent.EXTRA_SUBJECT, "Reset Password");
                i3.putExtra(android.content.Intent.EXTRA_TEXT, "Your new password is 1234. Please reset upon logging in!");
                i3.setType("text/plain");*/
                User user = db.getUser(email);
                user.setPassword("1234");
                db.updateUser(user);
                new SendMail().execute("");
                /*if (i3.resolveActivity(getPackageManager()) != null) {
                    startActivity(i3);
                }*/
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = e1.getText().toString();
                password = e2.getText().toString();
                User user = db.getUser(email);
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(user.getPassword())) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", user.getUsername());
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putInt("phone", user.getPhone());
                        editor.commit();
                        i1 = new Intent(Login.this, HomePage.class);
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
            Mail m = new Mail("youremail@gmail", "password");

            String[] toArr = {};
            m.setTo(toArr);
            m.setFrom("youremail@gmail");
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