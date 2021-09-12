package com.appsnipp.maleo_proj;


public class Scale {
    private int adj_age;
    private double weight;
    private double height;
    private double head;


    public Scale(){
    }

    public Scale(int age_by_week, double weight, double height, double head){
        this.adj_age = age_by_week;
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

    public void setAdj_age(int age_by_week) {
        this.adj_age = age_by_week;
    }

    public int getAdj_age() {
        return adj_age;
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

