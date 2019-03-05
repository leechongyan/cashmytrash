package com.example.irecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

//https://www.youtube.com/watch?v=1WPAXHhG6u0
//C:\Users\leech\AppData\Local\Android\Sdk\extras\intel\Hardware_Accelerated_Execution_Manager
public class Registration extends AppCompatActivity {
    DatabaseHelper db;
    TextView t1;
    EditText e1, e2, e3, e4, e5;
    Button b1;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= new DatabaseHelper(this);
        t1=(TextView)findViewById(R.id.existingacc);
        e1=(EditText)findViewById(R.id.username);
        e2=(EditText)findViewById(R.id.firstpassword);
        e3=(EditText)findViewById(R.id.secondpassword);
        e4=(EditText)findViewById(R.id.email);
        e5=(EditText)findViewById(R.id.phone);
        b1=(Button)findViewById(R.id.register);
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
                   int i5=Integer.parseInt(s5);
                   if(s2.equals(s3)){
                        User user = new User(s4,s1,s2,i5);
                        if(db.getUser(s4)==null){
                            db.insert(user);
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            i = new Intent(Registration.this,Login.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(), "Email has already existed", Toast.LENGTH_SHORT).show();
                        }
                   }else{
                       Toast.makeText(getApplicationContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();
                   }
               }
           }
        });
    }
}
