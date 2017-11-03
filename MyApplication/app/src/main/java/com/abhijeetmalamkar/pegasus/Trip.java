package com.abhijeetmalamkar.pegasus;

import com.abhijeetmalamkar.pegasus.Calculator;

import java.io.Serializable;
import java.util.Date;


public class Trip implements Serializable {

    public String name;
    public Float[] start;
    public Float[] end;
    public Date date;
    public Float toll;
    public Float parking;
    public String[] locations;

    public String[] getLocations() {
        return locations;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    String note;
    Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Float getToll() {
        return toll;
    }

    public void setToll(Float toll) {
        this.toll = toll;
    }

    public Float getParking() {
        return parking;
    }

    public void setParking(Float parking) {
        this.parking = parking;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Trip(String name, Float[] start, Float[] end, Date date) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.date = date;
    }

    public Float[] getStart() {
        return start;
    }

    public void setStart(Float[] start) {
        this.start = start;
    }

    public Float[] getEnd() {
        return end;
    }

    public void setEnd(Float[] end) {
        this.end = end;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getDistance() {
        return start.length>1&&end.length>1 ? Calculator.calculateDistance(start[0],start[1],end[0],end[1]):0f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
