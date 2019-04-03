package com.example.cz2006_mappy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetTargetActivity extends AppCompatActivity {

    private Button setButton;
    private EditText setEditText;
    private Button cancelButton;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_target);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
        final UserDAO userDAO = db.userDao();

        final SharedPreferences channel = getSharedPreferences("user_details", MODE_PRIVATE);

        String email = channel.getString("email","");
        final User user = userDAO.getUser(email);

        if (getIntent().hasExtra("com.example.cz2006.mappy.setTarget")){
            setButton = (Button) findViewById(R.id.setButton);
            setEditText = (EditText) findViewById(R.id.setEditText);
            cancelButton = (Button) findViewById(R.id.cancelButton);


            setButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == setButton.getId()){
                        text = setEditText.getText().toString();

                        user.setTarget(Double.parseDouble(text));
                        userDAO.update(user);
                        SharedPreferences.Editor editor = channel.edit();
                        editor.putString("target", Double.toString(user.getTarget()));
                        editor.commit();

                        Intent gotoHome = new Intent(getApplicationContext(), HomeActivity.class);
                        gotoHome.putExtra("com.example.cz2006.mappy.displayTarget", user.getTarget());
                        startActivity(gotoHome);

                        Toast.makeText(getApplicationContext(),"Savings Target Set",Toast.LENGTH_LONG).show();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == cancelButton.getId()){
                        Toast.makeText(getApplicationContext(),"Setting Savings Target Cancelled",Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }
            });
        }

    }

}
