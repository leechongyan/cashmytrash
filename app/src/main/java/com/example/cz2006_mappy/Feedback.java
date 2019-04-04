package com.example.cz2006_mappy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Feedback")
public class Feedback {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "feedback_id")
    private int feedback_id;

    @NonNull
    @ColumnInfo(name = "feedback_content")
    private String feedback_content;

    @NonNull
    @ColumnInfo(name = "sender_username")
    private String sender_username;

    @NonNull
    @ColumnInfo(name= "sender_id")
    private String sender_id;


    public Feedback(@NonNull int feedback_id,@NonNull String feedback_content, @NonNull String sender_username, @NonNull String sender_id) {
        this.feedback_id = feedback_id; // put 0 while constructing
        this.feedback_content = feedback_content;
        this.sender_username = sender_username;
        this.sender_id = sender_id;
    }

    public int getFeedback_id() {
        return this.feedback_id;
    }
    public String getFeedback_content(){
        return this.feedback_content;
    }
    public String getSender_username() {
        return this.sender_username;
    }
    public String getSender_id(){return this.sender_id; }

}