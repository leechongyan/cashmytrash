package com.example.cz2006_mappy;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;
    //list all member variable of the dao, check the return value of the method in Dao

    public ItemRepository(Application application) {
        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }
    public Item getItem(int itemId){ return mItemDao.getItem(itemId);}
    public List<Item> getSearchedItems(String s) { return mItemDao.getSearchedItems(s);}
    public void updateAvailable(int itemId){ mItemDao.updateAvailable(itemId);}
    public void deleteFromMyPurchases(int itemId){mItemDao.deleteFromMyPurchases(itemId);}

    double getPriceItemsDelivered(int itemId){ return mItemDao.getPriceItemsDelivered(itemId); }

    List<Item> getSoldItems(String sellerId) {return mItemDao.getSoldItems(sellerId);}

    public void deleteSoldItem(int itemId, String seller_email) {mItemDao.deleteSoldItem(itemId, seller_email);}

    public void deleteToDeliverItem(int itemId, String seller_email){ mItemDao.deleteToDeliverItem(itemId, seller_email); }

    public void insert(Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {


        private ItemDao mAsyncItemDao;

        insertAsyncTask(ItemDao dao) {
            mAsyncItemDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncItemDao.insert(params[0]);// Argument from asynctask which is item
            return null;
        }

    }


}
