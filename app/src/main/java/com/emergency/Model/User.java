package com.emergency.Model;

public class User {
    public String fullname;
    public String username;
    public String email;

    public String phone;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullname, String username, String email,  String phone) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;

        this.phone = phone;
    }
}
