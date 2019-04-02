package com.example.cz2006_mappy;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager extends AppCompatActivity {
    private AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
    private ItemDao itemDao = db.itemDao();
    private ItemTransactionDao itemTransactionDao = db.transactionDao();

    public List<Item> getItems(List<Integer> listID){
        List<Item> items = new ArrayList<>();
        for(int i =0; i< listID.size(); i++){
            int item_id = listID.get(i);
            items.add(itemDao.getItem(item_id));
        }
        return items;
    }

    public void delete(int id){
        itemDao.deleteFromMyPurchases(id);
        itemTransactionDao.deleteFromMyPurchases(id);
    }
    public boolean submitToken(int token, int id){
        Item item = itemDao.getItem(id);
        int item_token = item.getToken();
        if(token==item_token){
            itemTransactionDao.updateDelivered(id);
            return true;
        }
        else{
            return false;
        }

    }

}
