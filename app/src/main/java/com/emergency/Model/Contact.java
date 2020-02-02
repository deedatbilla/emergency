package com.emergency.Model;

public class Contact {

    public String email;

    public String phone;

    public Contact(String email, String phone) {

        this.email=email;
        this.phone=phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

}
