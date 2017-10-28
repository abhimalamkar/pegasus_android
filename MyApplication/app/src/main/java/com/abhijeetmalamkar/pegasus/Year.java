package com.abhijeetmalamkar.pegasus;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Year implements Serializable{
    Integer year;
    Map<String,String> data;

    public Year(Integer year) {
        this.year = year;
        data =  new HashMap<String,String>();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
