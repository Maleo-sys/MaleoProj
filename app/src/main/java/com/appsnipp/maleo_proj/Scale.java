package com.appsnipp.maleo_proj;


import java.util.Calendar;

public class Scale {
    private int age_by_week;
    private double weight;
    private double height;
    private double head;


    public Scale(){
    }

    public Scale(int age_by_week, double weight, double height, double head){
        this.age_by_week = age_by_week;
        this.weight = weight;
        this.height = height;
        this.head = head;
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

}

