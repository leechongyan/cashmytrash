package com.example.cz2006_mappy;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ItemTransactionRepository {

    private ItemTransactionDao mItemTransactionDao;
    private LiveData<List<ItemTransaction>> mAllTransactions;
    //list all member variable of the dao, check the return value of the method in Dao

    public ItemTransactionRepository(Application application) {
        AndroidRoomDatabase db = AndroidRoomDatabase.getDatabase(application);
        mItemTransactionDao = db.transactionDao();
        mAllTransactions = mItemTransactionDao.getAllTransactions();
    }
    public void updateDelivered(int itemId){mItemTransactionDao.updateDelivered(itemId);}

    LiveData<List<ItemTransaction>> getAllTransactions() {
        return mAllTransactions;
    }

    List<Integer> getItemTransaction(String username){
        return mItemTransactionDao.getItemTransaction(username);
    }

    List<Integer> getItemIdToDeliver(String seller_email){
        return mItemTransactionDao.getItemIdToDeliver(seller_email);
    }

    ItemTransaction getItemTransaction(int itemId){
        return mItemTransactionDao.getItemTransaction(itemId);
    }

    public void deleteToDeliverTransaction(int itemId, String seller_email){ mItemTransactionDao.deleteToDeliverTransaction(itemId, seller_email); }

    void deleteFromMyPurchases(int itemId){
        mItemTransactionDao.deleteFromMyPurchases(itemId);
    }


    public void insert(ItemTransaction transaction) {
        new insertAsyncTask(mItemTransactionDao).execute(transaction);
    }

    private static class insertAsyncTask extends AsyncTask<ItemTransaction, Void, Void> {


        private ItemTransactionDao mAsyncItemTransactionDao;

        insertAsyncTask(ItemTransactionDao dao) {
            mAsyncItemTransactionDao = dao;
        }

        @Override
        protected Void doInBackground(final ItemTransaction... params) {
            mAsyncItemTransactionDao.insert(params[0]);// Argument from asynctask which is item
            return null;
        }

    }


}
