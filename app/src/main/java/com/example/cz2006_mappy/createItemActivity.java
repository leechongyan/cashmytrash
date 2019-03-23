package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class createItemActivity extends AppCompatActivity {

    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);


        //TODO: Get User Object
        //onCreate
        Button createItemActivityButton = (Button) findViewById(R.id.createItemButton);
        createItemActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText itemName = (EditText) findViewById(R.id.createItemName);
                EditText itemPrice = (EditText) findViewById(R.id.createItemPrice);
                EditText itemDescription = (EditText) findViewById(R.id.createItemDescription);
                String name = itemName.getText().toString();
                String price = itemPrice.getText().toString();
                String description = itemDescription.getText().toString();

                String status = createInputValid(name,price,description);
                if(status.equals("success")){
                    //TODO: Get seller id and username
                    Item item = new Item(0,name,description,Double.parseDouble(price),Integer.parseInt(getRandomNumberString()),15, "elios");
                    mItemViewModel.insert(item);
                    Intent success = new Intent(getApplicationContext(),MainActivity.class);
                    Toast.makeText(getApplicationContext(),"Item has been created !",Toast.LENGTH_SHORT).show();
                    startActivity(success);
                } else {
                    Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Back
        FloatingActionButton createItemBackButton = (FloatingActionButton) findViewById(R.id.createItemBackButton);
        createItemBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String createInputValid(String name, String price, String description){
        System.out.println(name+price+description);
        if(name == null | name.isEmpty() | name.length() == 0){
            return "Item Name cannot be empty.";
        }
        if(price == null | price.isEmpty() | price.length() == 0){
            return "Item price cannot be empty";
        }
        if(description == null | description.isEmpty() | description.length() == 0){
            return "Item description cannot be empty";
        }
        if(isNumeric(name)){
            return "Item name cannot consists of only number";
        }
        if(Double.parseDouble(price) < 0){
            return "Item price cannot be negative";
        }
        if(description.length() <50){
            return "Item description must consists of minimum 50 characters";
        }
        return "success";


    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
