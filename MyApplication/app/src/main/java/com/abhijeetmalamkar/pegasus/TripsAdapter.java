package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TripsAdapter extends BaseAdapter {

    private static final int BASE_ID = 0x0A0B0CD;
    private final Context mContext;
    private final List<Trip> mList;


    // --Commented out by Inspection (6/9/17, 4:52 PM):private boolean isGrid = false;
    //Constructor
    public TripsAdapter(Context mContext, List<Trip> mList) {
        this.mContext = mContext;

        this.mList = mList;
    }

    @Override
    public int getCount() {
        if(mList!=null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if( mList!=null &&
                position < mList.size() &&
                position > -1) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return BASE_ID + position;
    }
    ViewHolder vh;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.trip_cell, parent, false);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Trip trip = (Trip) getItem(position);

        if (trip != null) {
            vh.num_miles.setText(String.valueOf(trip.getDistance()));
            //zoomInCamera(trip.start,map.get(0));
            Calendar c = Calendar.getInstance();
            c.setTime(trip.date);
            vh.num_day.setText(String.valueOf(c.get(Calendar.DATE)));
            vh.month.setText(DocumentsManagerFragment.months[c.get(Calendar.MONTH)].toUpperCase());
            if(trip.locations.length > 1) {
                vh.from.setText(trip.getLocations()[0]);
                vh.to.setText(trip.getLocations()[1]);
            }

//            vh.toll.setText(trip.getToll() != null ? String.valueOf(trip.getToll()) : "");
//
//            vh.toll.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if(!String.valueOf(s).equals("")) {
//                        trip.setToll(Float.valueOf(String.valueOf(s)));
//                        deleteTrip.getTrip(trip);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

        }

        return convertView;
    }



}

class ViewHolder {
    TextView num_miles;
    TextView num_day;
    TextView month;
    EditText from;
    EditText to;


    ViewHolder(View v) {
        this.num_miles = (TextView)v.findViewById(R.id.num_miles);
        this.num_day = (TextView)v.findViewById(R.id.num_day);
        this.month = (TextView)v.findViewById(R.id.str_day);
        this.from = (EditText)v.findViewById(R.id.from_);
        this.to = (EditText)v.findViewById(R.id.to_);
    }
}