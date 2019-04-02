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
    Item getItem(int itemId){ return mRepository.getItem(itemId); }
    List<Item> getSearchedItems(String s) {return mRepository.getSearchedItems(s);}
    LiveData<List<Item>> getSoldItems(int sellerId) {return mRepository.getSoldItems(sellerId);}
    public void deleteSoldItem(int i){mRepository.deleteSoldItem(i);}
    int updateAvailable(int itemId){return mRepository.updateAvailable(itemId);}

    public void insert(Item item) {mRepository.insert(item);}

}
