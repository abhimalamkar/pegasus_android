package com.abhijeetmalamkar.pegasus;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class SingleDocument implements Serializable {

    private String name;
    private String description;
    private BitmapDataObject image;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SingleDocument() {
    }

    public SingleDocument(String name, BitmapDataObject image, Date date) {
        this.name = name;
        this.image = image;
        this.date = date;
    }

    public BitmapDataObject getImage() {
        return image;
    }

    public void setImage(BitmapDataObject image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class BitmapDataObject implements Serializable {

        public Bitmap currentImage;

        public BitmapDataObject(Bitmap bitmap)
        {
            currentImage = bitmap;
        }

        private void writeObject(java.io.ObjectOutputStream out) throws IOException {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            currentImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] byteArray = stream.toByteArray();

            out.writeInt(byteArray.length);
            out.write(byteArray);

            currentImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        }

        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {


            int bufferLength = in.readInt();

            byte[] byteArray = new byte[bufferLength];

            int pos = 0;
            do {
                int read = in.read(byteArray, pos, bufferLength - pos);

                if (read != -1) {
                    pos += read;
                } else {
                    break;
                }

            } while (pos < bufferLength);

            currentImage = BitmapFactory.decodeByteArray(byteArray, 0, bufferLength);

        }
    }
}
