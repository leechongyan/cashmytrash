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

    @Query("SELECT item_id FROM ItemTransaction WHERE buyer_username = :username AND delivered = 0")
    List<Integer> getItemTransaction(String username);

    @Query("UPDATE ItemTransaction SET delivered = 1 WHERE item_id = :itemId")
    void updateDelivered(int itemId);

    @Query("DELETE FROM ITEMTRANSACTION WHERE item_id = :itemId")
    void deleteFromMyPurchases(int itemId);

    @Query("SELECT item_id FROM ItemTransaction WHERE seller_id = :seller_email AND delivered = 0")
    LiveData<List<ItemTransaction>> getItemIdToDeliver(String seller_email);

    @Query("DELETE FROM ItemTransaction WHERE item_id = :itemId AND seller_id = :seller_email")
    void deleteToDeliverTransaction(int itemId, String seller_email);

    @Query("SELECT * FROM ItemTransaction WHERE item_id = :itemId")
    ItemTransaction getItemTransaction(int itemId);

    @Query("SELECT item_id FROM ItemTransaction WHERE seller_id = :user_id AND delivered = 1")
    List<Integer> getItemsDelivered(String user_id);

    @Query("SELECT COUNT(item_id) FROM ItemTransaction WHERE seller_id = :user_id AND delivered = 1")
    Integer countDelivered(String user_id);
}
