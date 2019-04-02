package com.example.cz2006_mappy;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Query("DELETE FROM ITEM")
    void deleteAll();

    @Query("SELECT * FROM ITEM WHERE available = 1")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM ITEM WHERE item_name LIKE '%'  ||:s || '%'")
    List<Item> getSearchedItems(String s);

    @Query("SELECT * FROM ITEM WHERE seller_id = :sellerId")
    LiveData<List<Item>> getSoldItems(int sellerId);

    @Query("DELETE FROM ITEM WHERE item_id = :i")
    void deleteSoldItem(int i);

    @Delete
    void delete(Item item);
//    @Query("SELECT * FROM ITEM WHERE ")
//    List<Item> getToDeliverItems();

    @Query("SELECT * FROM ITEM WHERE item_id = :itemId")
    Item getItem(int itemId);

    @Query("UPDATE ITEM SET available = 0 WHERE item_id = :itemId")
    int updateAvailable(int itemId);

}
