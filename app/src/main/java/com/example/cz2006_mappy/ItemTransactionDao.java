package com.example.cz2006_mappy;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemTransactionDao {

    @Insert
    void insert(ItemTransaction transaction);

    @Query("DELETE FROM ITEMTRANSACTION")
    void deleteAll();

    @Query("SELECT * FROM ItemTransaction")
    LiveData<List<ItemTransaction>> getAllTransactions();

    @Query("SELECT item_id FROM ItemTransaction WHERE buyer_username == :username AND delivered == 0")
    List<Integer> getItemTransaction(String username);

    @Query("UPDATE ItemTransaction SET delivered = 1 WHERE item_id == :itemId")
    int updateDelivered(int itemId);

    @Query("SELECT item_id FROM ItemTransaction WHERE seller_id == :seller_email")
    List<Integer> getItemToDeliver(String seller_email);
    //get item ids trs pass to itemdao
}
