package com.abhijeetmalamkar.pegasus;

/**
 * Created by AbhijeetMalamkar on 10/27/17.
 */

public class Service {
    String title;
    String descrption;
    String cost;

    public Service(String title, String descrption, String cost) {
        this.title = title;
        this.descrption = descrption;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
