package com.example.cz2006_mappy;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ItemTransactionViewModel extends AndroidViewModel {

    private ItemTransactionRepository mRepository;

    private LiveData<List<ItemTransaction>> mAllTransactions;//add livedata member

    private Item mItemTransaction;

    public ItemTransactionViewModel (Application application) {
        super(application);
        mRepository = new ItemTransactionRepository(application);
        mAllTransactions = mRepository.getAllTransactions();
    }

    LiveData<List<ItemTransaction>> getAllItems() {return mAllTransactions;}

    List<Integer> getItemTransaction(String username){ return mRepository.getItemTransaction(username); }

    List<Integer> getItemsDelivered(String user_id){ return mRepository.getItemsDelivered(user_id); }

    Integer countDelivered(String user_id){ return mRepository.countDelivered(user_id); }

    List<Integer> getItemIdToDeliver(String seller_email){ return mRepository.getItemIdToDeliver(seller_email); }

    ItemTransaction getItemTransaction(int itemId){ return mRepository.getItemTransaction(itemId); }

    public void deleteToDeliverTransaction(int itemId, String seller_email){ mRepository.deleteToDeliverTransaction(itemId, seller_email); }

    public void insert(ItemTransaction transaction ) {mRepository.insert(transaction);}

    public void updateDelivered(int itemId){ mRepository.updateDelivered(itemId);}

    void deleteFromMyPurchases(int itemId){  mRepository.deleteFromMyPurchases(itemId);}

}
