package com.example.cz2006_mappy;


import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private ItemTransactionViewModel mItemTransactionViewModel;
    private ItemViewModel mItemViewModel;

    public List<Item> getItems(List<Integer> listID){
        mItemViewModel = ViewModelProviders.of(MyPurchases.this).get(ItemViewModel.class);
        List<Item> items = new ArrayList<>();
        for(int i =0; i< listID.size(); i++){
            int item_id = listID.get(i);
            items.add(mItemViewModel.getItem(item_id));
        }
        return items;
    }

}
