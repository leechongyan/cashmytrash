package com.example.cz2006_mappy;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Item.class, Feedback.class, ItemTransaction.class}, version = 4, exportSchema = false)
public abstract class AndroidRoomDatabase extends RoomDatabase {
    //Later fill the entities {Item.class, UserAccount.class,... for example}

    public abstract ItemDao itemDao();
    //later fill

    public abstract FeedbackDao feedbackDao();
    // to fill

    public abstract ItemTransactionDao transactionDao();

    private static AndroidRoomDatabase INSTANCE;


    public static AndroidRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (AndroidRoomDatabase.class) {
                if(INSTANCE == null){
                    //Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AndroidRoomDatabase.class, "android_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //delete all content and repopulate the database whenever the app is started
//    private static RoomDatabase.Callback sRoomDatabaseCallback =
//            new RoomDatabase.Callback(){
//
//                @Override
//                public void onOpen(@NonNull SupportSQLiteDatabase db) {
//                    super.onOpen(db);
//                    new PopulateDbAsync(INSTANCE).execute();
//                }
//
//            };
//
//    /**
//     * Populate the database in the background.
//     */
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final ItemDao mItemDao;
//        //later add all dao that wants to be populated
//
//        String[] words = {"dolphin", "crocodile", "cobra"};
//
//        PopulateDbAsync(AndroidRoomDatabase db) {
//            mItemDao = db.itemDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate the database
//            // when it is first created
//
//            //NOT NEEDED
//            mItemDao.deleteAll();
//
////            for (int i = 0; i <= words.length - 1; i++) {
////                Item item = new Item(words[i]);
////                mItemDao.insert(item);
////            }
//            Item item = new Item(0, "some item name","some item description",50.0, false,false,false,"some seller username");
//            mItemDao.insert(item);
//            return null;
//        }
//    }


}
