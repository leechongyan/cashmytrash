package com.example.cz2006_mappy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="emailaddress")
    private String emailaddress;

    @ColumnInfo(name="username")
    private String username;

    @ColumnInfo(name="password")
    private String password;

    @ColumnInfo(name="phone")
    private int phone;

    @ColumnInfo(name="target")
    private double target;

    @ColumnInfo(name="savings")
    private double savings;

    @Ignore
    @ColumnInfo(name="imagepath")
    private String imagePath;

    public User(String emailaddress, String username, String password, int phone, double target, double savings, String imagePath) {
        this.emailaddress = emailaddress;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.target = target;
        this.savings = savings;
        this.imagePath = imagePath;
    }

    public User() {
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPhone() {
        return phone;
    }

    public double getTarget() { return target; }

    public double getSavings() { return savings; }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setTarget(double target) { this.target = target; }

    public void setSavings(double savings) { this.savings = savings; }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}