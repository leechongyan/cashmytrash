package com.example.cz2006_mappy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ItemTransaction",
        foreignKeys = @ForeignKey(entity=Item.class, parentColumns = "item_id", childColumns="item_id", onDelete=ForeignKey.CASCADE)
)
public class ItemTransaction {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "transaction_id")
    private int transaction_id;


    @NonNull
    @ColumnInfo(name = "seller_id")
    private int seller_id;

    @NonNull
    @ColumnInfo(name = "item_id")
    private int item_id;

    @NonNull
    @ColumnInfo(name = "seller_username")
    private String seller_username;

    @NonNull
    @ColumnInfo(name = "buyer_id")
    private int buyer_id;

    @NonNull
    @ColumnInfo(name = "buyer_username")
    private String buyer_username;

    @NonNull
    @ColumnInfo(name = "delivered")
    private int delivered;


    public ItemTransaction(@NonNull int transaction_id, @NonNull int seller_id,@NonNull int item_id, @NonNull String seller_username,@NonNull int buyer_id, @NonNull String buyer_username, @NonNull int delivered) {
        this.transaction_id = transaction_id; // put 0 while constructing
        this.seller_id = seller_id;
        this.item_id = item_id;
        this.seller_username = seller_username;
        this.buyer_id = buyer_id;
        this.buyer_username = buyer_username;
        this.delivered = delivered;
    }

    public int getTransaction_id() {
        return this.transaction_id;
    }

    public int getSeller_id() {
        return this.seller_id;
    }
    public int getItem_id(){return this.item_id;}
    public String getSeller_username() {return this.seller_username;}

    public int getBuyer_id(){
        return this.buyer_id;
    }
    public String getBuyer_username(){
        return this.seller_username;
    }
    public int getDelivered() {return this.delivered; }


}

