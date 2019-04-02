package com.example.cz2006_mappy;


import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager extends AppCompatActivity {
    private AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
    private ItemDao itemDao = db.itemDao();

    public List<Item> getItems(List<Integer> listID){
        List<Item> items = new ArrayList<>();
        for(int i =0; i< listID.size(); i++){
            int item_id = listID.get(i);
            items.add(itemDao.getItem(item_id));
        }
        return items;
    }

}
