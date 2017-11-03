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

import java.util.ArrayList;


public class SingleDocumentFragment extends Fragment {

    Context context;
    ArrayList<SingleDocument> list;
    //SingleDocument document;
    int position;
    User user;
    public static String Tag = "SingleDocumentFragment";

    interface Mail{
        void singleDocument(ArrayList<SingleDocument> document,int position);
        void exitSingle();
    }

    public SingleDocumentFragment() {
    }

    public static SingleDocumentFragment newInstance( ArrayList<SingleDocument> document,int position,User user) {
        SingleDocumentFragment fragment = new SingleDocumentFragment();
        fragment.list = document;
        fragment.position = position;
        fragment.user = user;
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((ImageView)view.findViewById(R.id.imageView)).setImageBitmap(list.get(position).getImage().currentImage);
        ((TextView)view.findViewById(R.id.textView)).setText(list.get(position).getName());
        ((MainActivity) context).setTitle("Single Document Tracker");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.btn_logout);
        item.setVisible(false);
        item = menu.findItem(R.id.share);
        if(item!=null) {
            item.setVisible(false);
        }
        item = menu.findItem(R.id.delete);
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
                ((Mail)context).singleDocument(list,position);
            }
        }

        if(item.getItemId() == R.id.delete) {
            ArrayList<DocumentsCollection> list = DocumentsUtil.loadDocuments(user.getEmail()+DocumentsManagerFragment.Tag,context);
            list.remove(position);
            DocumentsUtil.saveDocuments(user.getEmail()+DocumentsManagerFragment.Tag,list,context);
            ((Mail)context).exitSingle();
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
