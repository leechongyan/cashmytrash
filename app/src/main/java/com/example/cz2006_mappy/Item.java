package com.example.cz2006_mappy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Item")
public class Item {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "item_id")
    private int item_id;

    @NonNull
    @ColumnInfo(name = "item_name")
    private String item_name;

    @NonNull
    @ColumnInfo(name = "item_description")
    private String item_description;

    @NonNull
    @ColumnInfo(name = "price")
    private double price;

    @NonNull
    @ColumnInfo(name = "token")
    private int token;

    @NonNull
    @ColumnInfo(name = "seller_id")
    private int seller_id;

    @NonNull
    @ColumnInfo(name = "seller_username")
    private String seller_username;

    @ColumnInfo(name = "buyer_username")
    private String buyer_username;

    @ColumnInfo(name = "buyer_id")
    private int buyer_id;


    public Item(@NonNull int item_id,@NonNull String item_name, @NonNull String item_description, @NonNull double price
    ,@NonNull int token, @NonNull int seller_id, @NonNull String seller_username,@NonNull int buyer_id,@NonNull String buyer_username) {
        this.item_id = item_id; // put 0 while constructing
        this.item_name = item_name;
        this.item_description = item_description;
        this.price = price;
        this.token = token;
        this.seller_id = seller_id;
        this.seller_username = seller_username;
        this.buyer_id = buyer_id;
        this.buyer_username =buyer_username;
    }

    public int getItem_id() {
        return this.item_id;
    }
    public String getItem_name(){
        return this.item_name;
    }
    public String getItem_description() {
        return this.item_description;
    }
    public double getPrice() {
        return price;
    }
    public int getToken() {return this.token;}
    public int getSeller_id() {
        return this.seller_id;
    }
    public String getSeller_username() {return this.seller_username;}
    public String getBuyer_username(){return this.buyer_username;}
    public int getBuyer_id(){return this.buyer_id;}

}
