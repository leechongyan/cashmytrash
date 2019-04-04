package com.example.cz2006_mappy;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertToken extends AppCompatActivity {

    private ItemViewModel mItemViewModel;
    private ItemTransactionViewModel mItemTransactionViewModel;
    private TransactionManager manager = new TransactionManager();

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
                onBackPressed();
            }
        });
    }
    public void submitToken(View view){
        int id = getIntent().getExtras().getInt("item_id_my_purchases");
        EditText editTextToken = (EditText) findViewById(R.id.editTextToken);
        String inserted_token = editTextToken.getText().toString();
        if(inserted_token == null | inserted_token.isEmpty() | inserted_token.length() == 0){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Token cannot be empty",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else{
            int token = Integer.parseInt(inserted_token);
            boolean result = manager.submitToken(token,id);
            if(result){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Your item has been received. Thank you for shopping with us",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent goPurchase = new Intent(getApplicationContext(), MyPurchases.class);
                startActivity(goPurchase);
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
}
