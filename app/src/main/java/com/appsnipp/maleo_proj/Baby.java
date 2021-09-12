package com.appsnipp.maleo_proj;

import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class Baby {
    private String name;
    private String gender;
    private int age_by_week;
    private int week_number_of_birth;
    private int adj_age;
    private DatePicker birth_date;
    private int year_of_birth,month_of_birth,day_of_birth;
    private ArrayList<Scale> scales = new ArrayList<Scale>();


    public Baby(){
    }

    //send data at register
    public Baby(String name, String gender, int week_number_of_birth, DatePicker birth_date){
        this.name = name;
        this.gender = gender;
        this.week_number_of_birth = week_number_of_birth;
//        this.scales.add(first_scale);

        this.birth_date = birth_date;
        this.year_of_birth = birth_date.getYear();
        this.month_of_birth = birth_date.getMonth()+1;
        this.day_of_birth = birth_date.getDayOfMonth();

//        calc of adj age and age by week
        Calendar today = Calendar.getInstance();
        int age_by_days = ((today.get(Calendar.YEAR) - this.year_of_birth) * 365)
                + ((today.get(Calendar.MONTH)+1 - this.month_of_birth) * 30)
                + (today.get(Calendar.DAY_OF_MONTH)- this.day_of_birth);

        this.age_by_week = age_by_days / 7;
        this.adj_age = this.age_by_week - (40 - this.week_number_of_birth);
    }

    //retrieve info from database
    public Baby(String name, String gender, int week_number_of_birth, ArrayList<Scale> scales, int year_of_birth, int month_of_birth, int day_of_birth){
        this.name = name;
        this.gender = gender;
        this.week_number_of_birth = week_number_of_birth;
        this.scales = scales;
        this.year_of_birth = year_of_birth;
        this.month_of_birth = month_of_birth+1;
        this.day_of_birth = day_of_birth;


        /**
         * Calculation of adjusted age.
         * chronological age minus the number of weeks s\he was born early.
         */

        Calendar today = Calendar.getInstance();
        int age_by_days = ((today.get(Calendar.YEAR) - this.year_of_birth) * 365)
                + ((today.get(Calendar.MONTH)+1 - this.month_of_birth) * 30)
                + (today.get(Calendar.DAY_OF_MONTH) - this.day_of_birth);
        this.age_by_week = age_by_days / 7;
        this.adj_age = Math.max(0 , age_by_week - (40 - this.week_number_of_birth));
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

