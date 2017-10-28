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


public class DocumentCollectionFragment extends Fragment {

    Context context;
    DocumentsCollection document;
    int position;
    public static String Tag = "DocumentCollectionFragment";

    interface Mail{
        void singleDocument(DocumentsCollection document,int position);
    }

    public DocumentCollectionFragment() {
    }

    public static DocumentCollectionFragment newInstance(DocumentsCollection document) {
        DocumentCollectionFragment fragment = new DocumentCollectionFragment();
        fragment.document = document;
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
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(context instanceof RecyclerViewDataAdapter.Open) {
                ((RecyclerViewDataAdapter.Open)context).openSingle(document.getAllItemsInSection().get(i),i);
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
        return inflater.inflate(R.layout.fragment_document_collection_document, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
