package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AbhijeetMalamkar on 10/26/17.
 */

public class CustomAdapter extends BaseAdapter {

    private static final int BASE_ID = 0x0A0B0CD;
    private final Context mContext;
    private final ArrayList<Service> mList;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;


    CustomAdapter(Context mContext, ArrayList<Service> mList) {
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
    public int getViewTypeCount() {
        return 2;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.aboutmecell,parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Service year = (Service)getItem(position);

        if (year != null) {
            vh.title.setText(year.getTitle());
            vh.description.setText(year.getDescrption());
            vh.cost.setText(year.getCost());
        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView description;
        public TextView cost;

        public ViewHolder(View contentView) {
            title = contentView.findViewById(R.id.title);
            description = contentView.findViewById(R.id.description);
            cost = contentView.findViewById(R.id.cost);
        }
    }

}
