package com.example.cz2006_mappy;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SellingManager extends AppCompatActivity {
    private AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(getApplication());
    private UserDAO userDAO = db.userDao();
    private ItemDao itemDao = db.itemDao();

    public User getUserListing(String email){
        return userDAO.getUser(email);
    }

    public List<Item> getItemsToDeliver(List<Integer> item_to_deliver_id){
        List<Item> items = new ArrayList<>();
        for(int i =0; i< item_to_deliver_id.size(); i++){
            int item_id = item_to_deliver_id.get(i);
            items.add(itemDao.getItem(item_id));
        }
        return items;
    }
}
