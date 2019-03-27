package com.example.cz2006_mappy;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

@Dao
public interface ItemTransactionDao {

    @Insert
    void insert(ItemTransaction transaction);

    @Query("DELETE FROM ITEMTRANSACTION")
    void deleteAll();


    @Query("SELECT * FROM ItemTransaction")
    LiveData<List<ItemTransaction>> getAllTransactions();

    @Query("SELECT item_id FROM ItemTransaction WHERE buyer_username == :username")
    List<Integer> getItemTransaction(String username);

}
