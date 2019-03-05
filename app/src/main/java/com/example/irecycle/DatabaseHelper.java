package com.example.irecycle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//kang yu is here

public class DatabaseHelper extends SQLiteOpenHelper{
    public DatabaseHelper(Context context) {
        super(context,"Cash.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(emailaddress text primary key, username text, password text, phone integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }

    public Boolean insert(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("emailaddress",user.getEmailaddress());
        contentValues.put("username",user.getUsername());
        contentValues.put("password",user.getPassword());
        contentValues.put("phone",user.getPhone());
        long ins = db.insert("user",null,contentValues);
        if(ins==-1){
            return false;
        }else{
            return true;
        }
    }

    public User getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM user WHERE emailaddress = \"" + email + "\"";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.getCount()==0) return null;
        cursor.moveToFirst();
        User result = new User();
        result.setEmailaddress(email);
        result.setUsername(cursor.getString(1));
        result.setPassword(cursor.getString(2));
        result.setPhone(cursor.getInt(3));
        return result;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("emailaddress",user.getEmailaddress());
        contentValues.put("username",user.getUsername());
        contentValues.put("password",user.getPassword());
        contentValues.put("phone",user.getPhone());
        db.update("user", contentValues, "emailaddress=\""+user.getEmailaddress()+"\"", null);
    }

    //
}
