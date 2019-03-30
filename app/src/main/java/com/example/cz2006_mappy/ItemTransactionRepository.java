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
    public int updateDelivered(int itemId){return mItemTransactionDao.updateDelivered(itemId);}

    LiveData<List<ItemTransaction>> getAllTransactions() {
        return mAllTransactions;
    }

    List<Integer> getItemTransaction(String username){
        return mItemTransactionDao.getItemTransaction(username);
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
