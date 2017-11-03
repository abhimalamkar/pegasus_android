package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleDocument> itemsList;
    private Context mContext;
    OpenSingle openSingle;

    interface OpenSingle{
        void open(ArrayList<SingleDocument> document,int position);
    }

    public SectionListDataAdapter(Context context, ArrayList<SingleDocument> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        SingleDocument singleItem = itemsList.get(i);
        holder.tvTitle.setText(singleItem.getName());
        holder.itemImage.setImageBitmap(singleItem.getImage().currentImage);
//        Glide.with(mContext)
//                .load(feedItem.getImageURL())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .error(R.drawable.bg)
//                .into(feedListRowHolder.thumbView);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView tvTitle;
        protected TextView tvDetail;
        protected ImageView itemImage;
        public SingleItemRowHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.tvDetail = (TextView) view.findViewById(R.id.tv_details);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0;i<itemsList.size();i++) {
                        if(itemsList.get(i).getName().equals(tvTitle.getText().toString())) {
                            openSingle.open(itemsList,i);
                        }
                    }

                }
            });
        }
    }
}