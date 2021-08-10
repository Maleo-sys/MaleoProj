package com.appsnipp.homedesigns;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String Uid;
    private List<ContactsContract.CommonDataKinds.Relation> children = new ArrayList<>();

    public User(){
    }

    //send data at register
    public User(String name, String email, String Uid){
        this.name = name;
        this.email = email;
        this.Uid = Uid;
    }

    //retrieve info from database
    public User(String email, String name, String Uid, List<ContactsContract.CommonDataKinds.Relation> children){
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

    public List<ContactsContract.CommonDataKinds.Relation> getItems() {
        return children;
    }

    public void setItems(List<ContactsContract.CommonDataKinds.Relation> items) {
        this.children = items;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

}

