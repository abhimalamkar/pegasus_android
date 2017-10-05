package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorLong;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;


public class DatePickerFragment extends Fragment {

    public static String Tag = "DatePickerFragment";
    Close close;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public interface Close{
        void closeDatePicker();
    }

    public static DatePickerFragment newInstance() {
        DatePickerFragment fragment = new DatePickerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.book).setOnClickListener(bookListener);
        setHasOptionsMenu(true);
    }

    private View.OnClickListener bookListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             View v = getView();
            if (v != null) {
                DatePicker picker = v.findViewById(R.id.datepicker);
                int day = picker.getDayOfMonth();
                int month = picker.getMonth() + 1;
                int year = picker.getYear();
                sendSMS("3215016669", "Day: " + String.valueOf(day) + " Month: " + String.valueOf(month) + " Year: " + String.valueOf(year));
                close.closeDatePicker();
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = (MenuItem) menu.findItem(R.id.btn_logout);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Close) {
            close = (Close) context;
        }
    }
}
