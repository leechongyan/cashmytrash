package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ItemDetailActivity extends AppCompatActivity {
    private ItemViewModel mItemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);


        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.itemDetailBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);

            }
        });
        TextView itemDetailId = (TextView) findViewById(R.id.itemDetailId);
        String id = getIntent().getExtras().getString("item_detail_id");
        itemDetailId.setText(id);


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
        String itemId = getIntent().getExtras().getString("item_detail_id");
        //change token and seller_id;
        mItemViewModel.update(itemId, "999","IM THE BUYER BITCH");

        Intent checkout = new Intent(this.getApplicationContext(), MyPurchases.class);
        startActivity(checkout);
    }
}
