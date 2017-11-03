package com.abhijeetmalamkar.pegasus;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class DocumentCollectionFragment extends Fragment {

    Context context;
    DocumentsCollection document;
    User user;
    int position;
    public static String Tag = "DocumentCollectionFragment";

    interface MailCollection {
        void singleDocument(DocumentsCollection document,int position);
        void exitCollection();
    }

    public DocumentCollectionFragment() {
    }

    public static DocumentCollectionFragment newInstance(DocumentsCollection document,User user) {
        DocumentCollectionFragment fragment = new DocumentCollectionFragment();
        fragment.document = document;
        fragment.user = user;
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        GridView gridView = ((GridView)view.findViewById(R.id.gridView));
        gridView.setOnItemClickListener(listener);
        gridView.setAdapter(new DocumentCollectionAdapter(context,document.getAllItemsInSection()));
        ((TextView)view.findViewById(R.id.textView)).setText(document.getHeaderTitle());
        ((MainActivity) context).setTitle("Month Document Tracker");
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(context instanceof RecyclerViewDataAdapter.Open) {
                ((RecyclerViewDataAdapter.Open)context).openSingle(document.getAllItemsInSection(),i);
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.btn_logout);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share) {
            if(context instanceof MailCollection) {
                ((MailCollection)context).singleDocument(document,position);
            }
        }

        if(item.getItemId() == R.id.delete) {
            ArrayList<DocumentsCollection> list = DocumentsUtil.loadDocuments(user.getEmail()+DocumentsManagerFragment.Tag,context);
            list.remove(position);
            DocumentsUtil.saveDocuments(user.getEmail()+DocumentsManagerFragment.Tag,list,context);
            ((MailCollection)context).exitCollection();
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
        return inflater.inflate(R.layout.fragment_document_collection_document, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
