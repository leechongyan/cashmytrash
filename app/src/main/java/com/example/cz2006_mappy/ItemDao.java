package com.example.cz2006_mappy;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Query("DELETE FROM ITEM")
    void deleteAll();


    @Query("SELECT * FROM ITEM")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM ITEM WHERE item_name LIKE '%'  ||:s || '%'")
    List<Item> getSearchedItems(String s);

}
