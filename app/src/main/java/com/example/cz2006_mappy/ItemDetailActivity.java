package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Transaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ItemDetailActivity extends AppCompatActivity {
    private ItemViewModel mItemViewModel;
    private ItemTransactionViewModel mItemTransactionViewModel;
    SharedPreferences pref;
    private TransactionManager manager = new TransactionManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mItemTransactionViewModel = ViewModelProviders.of(this).get(ItemTransactionViewModel.class);

        //back button
        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.itemDetailBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //get data to display in the ItemDetail page
        String id = getIntent().getExtras().getString("item_detail_id");
        Item item = mItemViewModel.getItem(Integer.parseInt(id));
        String item_name = item.getItem_name();
        double price = item.getPrice();
        String seller_username = item.getSeller_username();
        String description = item.getItem_description();
        byte[] image = item.getImage();
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        //get view
        TextView itemDetailId = (TextView) findViewById(R.id.itemDetailId);
        TextView itemDetailName = (TextView) findViewById(R.id.itemDetailName);
        TextView itemDetailUsername = (TextView) findViewById(R.id.itemDetailUsername);
        TextView itemDetailPrice = (TextView) findViewById(R.id.itemDetailPrice);
        TextView itemDetailDescription = (TextView) findViewById(R.id.itemDetailDescription);
        ImageView imageView = (ImageView) findViewById(R.id.createItemImageButton);

        //set view
        itemDetailId.setText(id);
        itemDetailName.setText(item_name);
        itemDetailUsername.setText("Sold By: " + seller_username);
        itemDetailPrice.setText(Double.toString(price));
        itemDetailDescription.setText(description);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap,150,150,false));
    }

    public void checkout(View view){
        //get all the data needed to perform checkout
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        TextView item_Id = (TextView) findViewById(R.id.itemDetailId);
        String itemId = item_Id.getText().toString();
        int item_id = Integer.parseInt(itemId);
        String buyer_id = pref.getString("email", "Anon");
        String buyer_username = pref.getString("username","Anon");

        //call transaction manager to handle checkout
        boolean success = manager.checkout(item_id, buyer_id, buyer_username);
        if(success){
            Intent succeed = new Intent(getApplicationContext(),MyPurchases.class);
            Toast.makeText(getApplicationContext(),"Checkout is successful!",Toast.LENGTH_SHORT).show();
            startActivity(succeed);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You cannot buy your own item", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
