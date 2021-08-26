package com.appsnipp.maleo_proj;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String Uid;
    private ArrayList<Baby> children;

    public User(){
    }

    //send data at register
    public User(String name, String email, String Uid){
        this.name = name;
        this.email = email;
        this.Uid = Uid;
        this.children = new ArrayList<Baby>();
    }

    //retrieve info from database
    public User(String email, String name, String Uid, ArrayList<Baby> children){
        this.name = name;
        this.email = email;
        this.Uid = Uid;
        this.children = children;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Baby> getItems() {
        return children;
    }

    public void setItems(ArrayList<Baby> items) {
        this.children = items;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

}

