package com.abhijeetmalamkar.pegasus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by amalamkar on 11/2/2017.
 */

public class Alert {

    public static void show(Context context, String title, String message, DialogInterface.OnClickListener okayListener,DialogInterface.OnClickListener cancelListener){
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        build.setTitle(title);
        build.setMessage(message);
        build.setPositiveButton("OKay",  okayListener);
        build.setNegativeButton("Cancel", cancelListener);
        build.show();
    }
}

