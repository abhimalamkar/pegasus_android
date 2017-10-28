// Abhijeet Malamkar
// JAV1 - 1706
// CustomAdapter.java
package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class DocumentCollectionAdapter extends BaseAdapter {

    private static final int BASE_ID = 0x0A0B0CD;
    private final Context mContext;
    private final List<SingleDocument> mList;

    DocumentCollectionAdapter(Context mContext, List<SingleDocument> mList) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.custom_adapter_cell,parent,false);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        SingleDocument user = (SingleDocument)getItem(position);

        if (user != null) {
            vh.textView_name.setText(user.getName());
            vh.textView_birthdate.setText(user.getDescription());
            vh.imageView.setImageBitmap(user.getImage().currentImage);
        }

        return convertView;
    }

    private class ViewHolder {
        final TextView textView_name;
        final TextView textView_birthdate;
        final ImageView imageView;

        ViewHolder(View v) {
            this.textView_name = (TextView)v.findViewById(R.id.txt_name);
            this.textView_birthdate = (TextView) v.findViewById(R.id.txt_birthDate);
            this.imageView = (ImageView) v.findViewById(R.id.imageView);
        }
    }
}
