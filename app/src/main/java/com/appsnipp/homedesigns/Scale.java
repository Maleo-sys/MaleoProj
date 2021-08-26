package com.appsnipp.homedesigns;


import java.util.Calendar;

public class Scale {
    private int age_by_week;
    private double weight;
    private double height;
    private double head;
    private Calendar date;


    public Scale(){
    }

    public Scale(int age_by_week, int weight, int height, int head, Calendar date){
        this.age_by_week = age_by_week;
        this.weight = weight;
        this.height = height;
        this.head = head;
        this.date = date;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setHead(double head) {
        this.head = head;
    }

    public void setAge_by_week(int age_by_week) {
        this.age_by_week = age_by_week;
    }

    public int getAge_by_week() {
        return age_by_week;
    }

    public double getHead() {
        return head;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}

