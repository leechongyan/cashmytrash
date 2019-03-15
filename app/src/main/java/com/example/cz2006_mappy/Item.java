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
    @ColumnInfo(name = "purchased")
    private boolean purchased =false;

    @NonNull
    @ColumnInfo(name = "received")
    private boolean received =false;

    @NonNull
    @ColumnInfo(name = "delivered")
    private boolean delivered =false;

    @NonNull
    @ColumnInfo(name = "seller_username")
    private String seller_username;


    public Item(@NonNull int item_id,@NonNull String item_name, @NonNull String item_description, @NonNull double price
    , boolean purchased, boolean received, boolean delivered, @NonNull String seller_username) {
        this.item_id = item_id; // put 0 while constructing
        this.item_name = item_name;
        this.item_description = item_description;
        this.price = price;
        this.purchased = purchased;
        this.received = received;
        this.delivered = delivered;
        this.seller_username = seller_username;
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
    public boolean getPurchased() {
        return purchased;
    }
    public boolean getDelivered() {
        return delivered;
    }
    public boolean getReceived() {
        return received;
    }
    public String getSeller_username() {
        return this.seller_username;
    }

}
