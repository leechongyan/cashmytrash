package com.example.irecycle;

public class User {
    private String emailaddress;
    private String username;
    private String password;
    private int phone;

    public User(String emailaddress, String username, String password, int phone) {
        this.emailaddress = emailaddress;
        this.username = username;
        this.password = password;
        this.phone = phone;
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
}
