package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailActivity extends AppCompatActivity {
    private ItemViewModel mItemViewModel;
    private ItemTransactionViewModel mItemTransactionViewModel;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mItemTransactionViewModel = ViewModelProviders.of(this).get(ItemTransactionViewModel.class);


        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.itemDetailBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), ListingActivity.class);
                startActivity(back);

            }
        });
        String id = getIntent().getExtras().getString("item_detail_id");
        Item item = mItemViewModel.getItem(Integer.parseInt(id));
        String item_name = item.getItem_name();
        double price = item.getPrice();
        String seller_username = item.getSeller_username();
        String description = item.getItem_description();
        byte[] image = item.getImage();
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        TextView itemDetailId = (TextView) findViewById(R.id.itemDetailId);
        itemDetailId.setText(id);


        TextView itemDetailName = (TextView) findViewById(R.id.itemDetailName);
        itemDetailName.setText(item_name);

        TextView itemDetailUsername = (TextView) findViewById(R.id.itemDetailUsername);
        itemDetailUsername.setText("Sold By: " + seller_username);

        TextView itemDetailPrice = (TextView) findViewById(R.id.itemDetailPrice);
        itemDetailPrice.setText(Double.toString(price));

        TextView itemDetailDescription = (TextView) findViewById(R.id.itemDetailDescription);
        itemDetailDescription.setText(description);

        ImageView imageView = (ImageView) findViewById(R.id.createItemImageButton);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap,150,150,false));
    }
    public void checkout(View view){
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        TextView item_Id = (TextView) findViewById(R.id.itemDetailId);
        String itemId = item_Id.getText().toString();
        Item item = mItemViewModel.getItem(Integer.parseInt(itemId));
        String seller_id = item.getSeller_id();
        String buyer_id = pref.getString("email", "Anon");
        if(seller_id.equals(buyer_id)){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You cannot buy your own item", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else{
            String username = item.getSeller_username();
            String buyer_username = pref.getString("username","Anon");
            ItemTransaction transaction1 = new ItemTransaction(0,seller_id,Integer.parseInt(itemId),username,buyer_id,buyer_username,0);
            mItemTransactionViewModel.insert(transaction1);
            mItemViewModel.updateAvailable(Integer.parseInt(itemId));
            Intent success = new Intent(getApplicationContext(),MyPurchases.class);
            Toast.makeText(getApplicationContext(),"Checkout is successful!",Toast.LENGTH_SHORT).show();
            startActivity(success);
        }
    }
}
