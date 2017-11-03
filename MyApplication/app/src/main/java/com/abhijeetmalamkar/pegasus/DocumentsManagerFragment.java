package com.abhijeetmalamkar.pegasus;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class DocumentsManagerFragment extends Fragment implements GetImage {

    public static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static String Tag = "DocumentsManagerFragment.Tag";
    Context mContext;
    ArrayList<DocumentsCollection> allSampleData;
    RecyclerView my_recycler_view;
    RecyclerViewDataAdapter adapter;

    AddDocument addDocument;
    private String m_Text = "No Name";
    private Boolean contains = false;

    @Override
    public void getImage(final Bitmap image) {
        final Calendar c = Calendar.getInstance();
        final int dayOfWeek = c.get(Calendar.MONTH);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(mContext);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!input.getText().toString().trim().isEmpty()) {
                    m_Text = input.getText().toString();
                }
                for (int i = 0; i < allSampleData.size(); i++) {
                    if (allSampleData.get(i).getHeaderTitle().equals(months[dayOfWeek])) {
                        if (allSampleData.get(i).getAllItemsInSection() != null) {
                            allSampleData.get(i).getAllItemsInSection().add(new SingleDocument(m_Text, new SingleDocument.BitmapDataObject(image), c.getTime()));
                        }
                        contains = true;
                        break;
                    }
                }

                if (!contains) {
                    DocumentsCollection dm = new DocumentsCollection();
                    dm.setHeaderTitle(months[dayOfWeek]);
                    dm.setDate(c.getTime());
                    ArrayList<SingleDocument> singleItem = new ArrayList<>();
                    singleItem.add(new SingleDocument(m_Text, new SingleDocument.BitmapDataObject(image), c.getTime()));
                    dm.setAllItemsInSection(singleItem);
                    allSampleData.add(dm);
                    contains = false;
                }

                for (int i = 0; i < allSampleData.size(); i++) {
                    Collections.sort(allSampleData.get(i).getAllItemsInSection(), new DateComparator());
                }
                Collections.sort(allSampleData, new DateComparatorDoc());

                DocumentsUtil.saveDocuments(addDocument.getUserDocuments().getEmail()+Tag,allSampleData,mContext);
                if(allSampleData.size() == 0) {
                    my_recycler_view.setVisibility(View.GONE);
                } else {
                    my_recycler_view.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



    public class DateComparator implements Comparator<SingleDocument> {
        public int compare(SingleDocument left, SingleDocument right) {
            return right.getDate().compareTo(left.getDate());
        }
    }

    public class DateComparatorDoc implements Comparator<DocumentsCollection> {
        public int compare(DocumentsCollection left, DocumentsCollection right) {
            return right.getDate().compareTo(left.getDate());
        }
    }


    public interface AddDocument {
        void showCamera();
        User getUserDocuments();
    }


    public DocumentsManagerFragment() {
        // Required empty public constructor
    }


    public static DocumentsManagerFragment newInstance() {
        DocumentsManagerFragment fragment = new DocumentsManagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) mContext).setTitle("Document Tracker");
        setupData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = (MenuItem) menu.findItem(R.id.btn_logout);
        item.setVisible(false);
        inflater.inflate(R.menu.add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_add) {
//            DocumentsCollection dm = new DocumentsCollection();
//            dm.setHeaderTitle("Section " + "asd");
//            allSampleData.add(dm);
//            adapter.notifyDataSetChanged();
            addDocument.showCamera();
        }
        return super.onOptionsItemSelected(item);
    }

    void setupData() {
        allSampleData = DocumentsUtil.loadDocuments(addDocument.getUserDocuments().getEmail()+Tag,mContext);
        if (allSampleData == null) {
            allSampleData = new ArrayList<>();
        }
        my_recycler_view = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        adapter = new RecyclerViewDataAdapter(mContext, allSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);

        if(allSampleData.size() == 0) {
            my_recycler_view.setVisibility(View.GONE);
        } else {
            my_recycler_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.documents, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof AddDocument) {
            addDocument = (AddDocument) context;
        }
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).getImage = this;
        }
    }
}
