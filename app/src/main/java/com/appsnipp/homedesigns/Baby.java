package com.appsnipp.homedesigns;

import android.provider.ContactsContract;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.List;

public class Baby {
    private String name;
    private String gender;
    private int age_by_week;
    private int adj_age;
    private DatePicker birth_date;
    private ArrayList<Scale> scales;


    public Baby(){
    }


    public Baby(String name, String gender, int age_by_week, ArrayList<Scale> scales){
    this.name = name;
    this.gender = gender;
    this.age_by_week = age_by_week;
    this.adj_age = 40 - age_by_week;
    this.scales = scales;
    }

    // Getters and Setters
    public int getAdj_age() {
        return adj_age;
    }

    public int getAge_by_week() {
        return age_by_week;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public void setAdj_age(int adj_age) {
        this.adj_age = adj_age;
    }

    public void setAge_by_week(int age_by_week) {
        this.age_by_week = age_by_week;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Scale> getScales() {
        return scales;
    }

    public void AddScale(Scale scale){
        this.scales.add(scale);
    }
//    public void setScales(List<ContactsContract.CommonDataKinds.Relation> scales) {
//        this.scales = scales;
//    }
}

