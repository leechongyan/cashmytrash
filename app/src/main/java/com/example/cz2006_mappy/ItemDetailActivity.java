package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailActivity extends AppCompatActivity {
    private ItemViewModel mItemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.itemDetailBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);

            }
        });

        TextView itemDetailName = (TextView) findViewById(R.id.itemDetailName);
        String name = getIntent().getExtras().getString("item_detail_name");
        itemDetailName.setText(name);

        TextView itemDetailUsername = (TextView) findViewById(R.id.itemDetailUsername);
        String username = getIntent().getExtras().getString("item_detail_username");
        itemDetailUsername.setText("Sold By: " + username);

        TextView itemDetailPrice = (TextView) findViewById(R.id.itemDetailPrice);
        String price = getIntent().getExtras().getString("item_detail_price");
        itemDetailPrice.setText(price);

        TextView itemDetailDescription = (TextView) findViewById(R.id.itemDetailDescription);
        String description = getIntent().getExtras().getString("item_detail_description");
        itemDetailDescription.setText(description);

    }
    public void checkout(View view){
        String name = getIntent().getExtras().getString("item_detail_name");
        String username = getIntent().getExtras().getString("item_detail_username");
        String price = getIntent().getExtras().getString("item_detail_price");
        String description = getIntent().getExtras().getString("item_detail_description");
        //change token and seller_id;
        Item item = new Item(0,name,description,Double.parseDouble(price),0,15, username);

        Intent checkout = new Intent(this.getApplicationContext(), MyPurchases.class);
        startActivity(checkout);
    }
}
