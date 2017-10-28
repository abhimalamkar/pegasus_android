package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class SingleDocumentFragment extends Fragment {

    Context context;
    SingleDocument document;
    int position;
    public static String Tag = "SingleDocumentFragment";

    interface Mail{
        void singleDocument(SingleDocument document,int position);
    }

    public SingleDocumentFragment() {
    }

    public static SingleDocumentFragment newInstance(SingleDocument document,int position) {
        SingleDocumentFragment fragment = new SingleDocumentFragment();
        fragment.document = document;
        fragment.position = position;
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((ImageView)view.findViewById(R.id.imageView)).setImageBitmap(document.getImage().currentImage);
        ((TextView)view.findViewById(R.id.textView)).setText(document.getName());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.btn_logout);
        item.setVisible(false);
        item = menu.findItem(R.id.share);
        if(item!=null) {
            item.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share) {
            if(context instanceof Mail) {
                ((Mail)context).singleDocument(document,position);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_document, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
