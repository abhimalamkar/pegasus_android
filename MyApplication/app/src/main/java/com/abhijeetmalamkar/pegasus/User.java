package com.abhijeetmalamkar.pegasus;


import java.io.Serializable;

public class User implements Serializable {

    Boolean isLoggedIn;
    String email;
    String password;
    String name;
    String address;

    public User(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String email, String password, String name, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;

    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
