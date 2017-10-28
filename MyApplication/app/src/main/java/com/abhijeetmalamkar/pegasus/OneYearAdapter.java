package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AbhijeetMalamkar on 10/26/17.
 */

public class OneYearAdapter extends BaseAdapter {

    private static final int BASE_ID = 0x0A0B0CD;
    private final Context mContext;
    private final String[] mList;

    OneYearAdapter(Context mContext, String[] mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        if(mList!=null) {
            return mList.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if( mList!=null &&
                position < mList.length &&
                position > -1) {
            return mList[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.oneyearcell,parent,false);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        String year = (String)getItem(position);

        if (year != null) {
            vh.title.setText(year);
        }

        return convertView;
    }

    private class ViewHolder {
        final TextView title;

        ViewHolder(View v) {
            this.title = (TextView)v.findViewById(R.id.title_text);
        }
    }
}
