package com.abhijeetmalamkar.pegasus;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class TripDetailsFragment extends Fragment {

    Trip trip;
    static int position;
    Context mContext;
    ArrayList<Trip> trips;
    int i = 0;
    public static String Tag = "TripDetailsFragment";

    public interface CloseFragment{
        void exit();
    }

    public TripDetailsFragment(){

    }

    public static TripDetailsFragment newInstance(int _position) {
        TripDetailsFragment fragment = new TripDetailsFragment();
        position = _position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.cancel_btn).setOnClickListener(cancelListener);


        view.findViewById(R.id.btn_trip).setOnClickListener(tripListner);
        view.findViewById(R.id.btn_notes).setOnClickListener(noteListner);
        view.findViewById(R.id.trip_delete).setOnClickListener(delete);
        ((TextView) view.findViewById(R.id.from_)).setText(trip.getLocations()[0]);
        ((TextView) view.findViewById(R.id.to_)).setText(trip.getLocations()[1]);
        trips = loadTrips(((TripListFragment.GetUser) mContext).getUserDocuments().getEmail() + TripListFragment.Tag);
        //trip = trips.get(position);
        Log.d("", "onViewCreated: " + trip.locations[0]);

        EditText toll = view.findViewById(R.id.num_toll);
        EditText parking = view.findViewById(R.id.num_parking);
        EditText note = view.findViewById(R.id.str_notes);

        toll.setText(trip.getToll() != null ? String.valueOf(trip.getToll()) : "");
        parking.setText(trip.getParking() != null ? String.valueOf(trip.getParking()) : "");
        note.setText(trip.getNote() != null ? trip.getNote() : "");

        toll.addTextChangedListener(tollWatcher);
        parking.addTextChangedListener(parkingWatcher);
        note.addTextChangedListener(noteWatcher);

    }

    private TextWatcher tollWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!String.valueOf(s).equals("")) {
                trip.setToll(Float.valueOf(String.valueOf(s)));
                saveTrip();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

    private void saveTrip(){
        trips = loadTrips(((TripListFragment.GetUser)mContext).getUserDocuments().getEmail()+TripListFragment.Tag);
        for (int i = 0; i < trips.size();i++) {

            if (Arrays.equals(trips.get(i).getLocations(),trip.getLocations())) {
                trips.get(i).setNote(trip.getNote());
                trips.get(i).setParking(trip.getParking());
                trips.get(i).setToll(trip.getToll());
                saveTrips(((TripListFragment.GetUser)mContext).getUserDocuments().getEmail()+TripListFragment.Tag,trips);
            }
        }
    }

    private TextWatcher parkingWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!String.valueOf(s).equals("")) {
                trip.setParking(Float.valueOf(String.valueOf(s)));
                saveTrip();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

    private TextWatcher noteWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!String.valueOf(s).equals("")) {
                trip.setNote(String.valueOf(s));
                saveTrip();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

    private View.OnClickListener tripListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().findViewById(R.id.map_views).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.toll_layout).setVisibility(View.GONE);
        }
    };

    private View.OnClickListener noteListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().findViewById(R.id.map_views).setVisibility(View.GONE);
            getActivity().findViewById(R.id.toll_layout).setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener delete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            trips.remove(position);
            saveTrips(((TripListFragment.GetUser)mContext).getUserDocuments().getEmail()+TripListFragment.Tag,trips);
            ((CloseFragment)mContext).exit();
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           if(mContext instanceof CloseFragment) {
               ((CloseFragment)mContext).exit();
           }
        }
    };

    private void saveTrips(String filename, ArrayList<Trip> trips){
        try {
            FileOutputStream fos = mContext.openFileOutput(filename + ".bin", mContext.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(trips);
            oos.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Trip> loadTrips(String filename){
        ArrayList<Trip> list = null;
        try {
            FileInputStream fis = mContext.openFileInput(filename + ".bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<Trip>) ois.readObject();
            ois.close();

        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip_details, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
