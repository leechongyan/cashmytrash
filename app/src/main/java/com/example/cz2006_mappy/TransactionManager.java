package com.example.cz2006_mappy;


import android.support.v7.app.AppCompatActivity;

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

    public boolean checkout(int item_id, String buyer_id, String buyer_username){
        Item item = itemDao.getItem(item_id);
        String seller_id = item.getSeller_id();
        if(seller_id.equals(buyer_id)){
            return false;
        }
        else{
            String seller_username = item.getSeller_username();
            ItemTransaction transaction1 = new ItemTransaction(0,seller_id,item_id,seller_username,buyer_id,buyer_username,0);
            itemTransactionDao.insert(transaction1);
            itemDao.updateAvailable(item_id);
            return true;
        }
    }

}
