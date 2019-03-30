package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Transaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ItemDetailActivity extends AppCompatActivity {
    private ItemViewModel mItemViewModel;
    private ItemTransactionViewModel mItemTransactionViewModel;
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
        TextView item_Id = (TextView) findViewById(R.id.itemDetailId);
        String itemId = item_Id.getText().toString();
        TextView itemDetailName = (TextView) findViewById(R.id.itemDetailName);
        String username = itemDetailName.getText().toString();
        String buyer_username = "gabriella";
        ItemTransaction transaction1 = new ItemTransaction(0,0,Integer.parseInt(itemId),username,1,buyer_username,0);
        mItemTransactionViewModel.insert(transaction1);
        int updated = mItemViewModel.updateAvailable(Integer.parseInt(itemId));
        Intent success = new Intent(getApplicationContext(),MyPurchases.class);
        Toast.makeText(getApplicationContext(),"Checkout is successful!",Toast.LENGTH_SHORT).show();
        startActivity(success);
    }
}
