package com.example.cz2006_mappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager extends AppCompatActivity {
    private ItemTransactionViewModel mItemTransactionViewModel;
    private ItemViewModel mItemViewModel;
    public void insertToken(View view) {
        TextView id = (TextView) findViewById(R.id.grid_item_id_my_purchases);
        Intent token = new Intent(this, InsertToken.class);
        token.putExtra("item_id_my_purchases", Integer.parseInt(id.getText().toString()));

        startActivity(token);
    }

    public List<Item> getItems(List<Integer> listID){
        List<Item> items = new ArrayList<>();
        for(int i =0; i< listID.size(); i++){
            int item_id = listID.get(i);
            items.add(mItemViewModel.getItem(item_id));
        }
        return items;
    }

}
