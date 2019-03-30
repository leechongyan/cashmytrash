package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertToken extends AppCompatActivity {

    private ItemViewModel mItemViewModel;
    private ItemTransactionViewModel mItemTransactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_token);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mItemTransactionViewModel = ViewModelProviders.of(this).get(ItemTransactionViewModel.class);

        FloatingActionButton tokenBackButton = (FloatingActionButton) findViewById(R.id.tokenBackButton);
        tokenBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), MyPurchases.class);
                startActivity(backIntent);
            }
        });
    }
    public void submitToken(View view){
        int id = getIntent().getExtras().getInt("item_id_my_purchases");
        EditText editTextToken = (EditText) findViewById(R.id.editTextToken);
        int token = Integer.parseInt(editTextToken.getText().toString());
        Item item = mItemViewModel.getItem(id);
        int item_token = item.getToken();
        if(token == item_token){
            int updated = mItemTransactionViewModel.updateDelivered(id);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Your item has been received. Thank you for shopping with us",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            onBackPressed();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Token entered is invalid. Please enter your token again.",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

}
