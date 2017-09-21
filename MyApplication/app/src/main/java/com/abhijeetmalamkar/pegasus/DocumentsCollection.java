package com.abhijeetmalamkar.pegasus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class DocumentsCollection implements Serializable {

    private String headerTitle;
    private ArrayList<SingleDocument> allItemsInSection;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DocumentsCollection() {

    }
    public DocumentsCollection(String headerTitle, ArrayList<SingleDocument> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleDocument> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleDocument> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
