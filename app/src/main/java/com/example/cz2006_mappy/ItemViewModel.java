package com.example.cz2006_mappy;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;

    private LiveData<List<Item>> mAllItems;//add livedata member

    public ItemViewModel (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {return mAllItems;}
    List<Item> getSearchedItems(String s) {return mRepository.getSearchedItems(s);}



    public void update(String itemId, String buyerId, String buyerUsername ){ mRepository.update(itemId, buyerId,buyerUsername);}

    public void insert(Item item) {mRepository.insert(item);}

}
