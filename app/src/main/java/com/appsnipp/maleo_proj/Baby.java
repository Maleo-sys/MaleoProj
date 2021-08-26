package com.appsnipp.maleo_proj;

import android.provider.ContactsContract;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Baby {
    private String name;
    private String gender;
    private int age_by_week;
    private int week_number_of_birth;
    private int adj_age;
    private Calendar birth_date;
    private ArrayList<Scale> scales;


    public Baby(){
    }

    //send data at register
    public Baby(String name, String gender, int age_by_week, int week_number_of_birth, Scale first_scale){
        this.name = name;
        this.gender = gender;
        this.age_by_week = age_by_week;
        this.week_number_of_birth = week_number_of_birth;
        this.scales.add(first_scale);
    }

    //retrieve info from database
    public Baby(String name, String gender, ArrayList<Scale> scales){
    this.name = name;
    this.gender = gender;
    this.scales = scales;

    Calendar today = Calendar.getInstance();
    int age_by_days = ((today.get(Calendar.YEAR) - birth_date.get(Calendar.YEAR)) * 365) + (today.get(Calendar.DAY_OF_YEAR) - birth_date.get(Calendar.DAY_OF_YEAR));

    this.age_by_week = age_by_days / 7;
    this.adj_age = age_by_week - (40 - week_number_of_birth);
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

}

