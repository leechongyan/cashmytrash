package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class createItemActivity extends AppCompatActivity implements View.OnClickListener {

    private ItemViewModel mItemViewModel;
    private static final int RESULT_LOAD_IMAGE=1;
    boolean changedimage;
    ImageView itemImage;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        changedimage = false;
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        final ImageView itemImage = (ImageView) findViewById(R.id.itemImage);
        itemImage.setOnClickListener(this);

        //onCreate
        Button createItemActivityButton = (Button) findViewById(R.id.createItemButton);
        createItemActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] byteImage;
                EditText itemName = (EditText) findViewById(R.id.createItemName);
                EditText itemPrice = (EditText) findViewById(R.id.createItemPrice);
                EditText itemDescription = (EditText) findViewById(R.id.createItemDescription);
                String name = itemName.getText().toString();
                String price = itemPrice.getText().toString();
                String description = itemDescription.getText().toString();
                if(itemImage.getDrawable() != null) {
                    Bitmap image = ((BitmapDrawable) itemImage.getDrawable()).getBitmap();
                    byteImage = uploadImage(image);
                }else{
                    byteImage =null;
                }

                String status = createInputValid(name,price,description, byteImage);
                if(status.equals("success")){
                    //TODO: Get seller id and username
                    pref = getSharedPreferences("user_details", MODE_PRIVATE);
                    String seller_username = pref.getString("username", "Anon");
                    String seller_id = pref.getString("email","Anon");
                    Item item = new Item(0,name,description,Double.parseDouble(price),Integer.parseInt(getRandomNumberString()),seller_id, seller_username,1, byteImage);
                    mItemViewModel.insert(item);
                    Intent success = new Intent(getApplicationContext(), ListingActivity.class);
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
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.itemImage:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        itemImage = (ImageView) findViewById(R.id.itemImage);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            itemImage.setImageURI(selectedImage);
            changedimage = true;
        }
    }

    private byte[] uploadImage(Bitmap image){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private String createInputValid(String name, String price, String description, byte[] byteImage){

        if(name == null | name.isEmpty() | name.length() == 0){
            return "Item Name cannot be empty";
        }
        if(price == null | price.isEmpty() | price.length() == 0){
            return "Item price cannot be empty";
        }
        if(byteImage == null){
            return "An item image must be uploaded";
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
            return "Item description must consist of minimum 50 characters";
        }
        if(changedimage == false){
            return "Please upload a image";
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
