package com.abhijeetmalamkar.pegasus;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by amalamkar on 11/3/2017.
 */

public class DocumentsUtil {
    public static void saveDocuments(String filename, ArrayList<DocumentsCollection> document, Context mContext){
        try {
            FileOutputStream fos = mContext.openFileOutput(filename + ".bin", mContext.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(document);
            oos.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<DocumentsCollection> loadDocuments(String filename, Context mContext){
        ArrayList<DocumentsCollection> list = null;
        try {
            FileInputStream fis = mContext.openFileInput(filename + ".bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<DocumentsCollection>) ois.readObject();
            ois.close();

        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
