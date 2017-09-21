package com.abhijeetmalamkar.pegasus;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.ArrayList;


public class TripDetailsFragment extends Fragment {

    Trip trip;
    static int position;
    Context mContext;
    ArrayList<Trip> trips;
    int i = 0;
    public static String Tag = "TripDetailsFragment";

   private OnMapReadyCallback mapReady = new OnMapReadyCallback() {
       @Override
       public void onMapReady(GoogleMap googleMap) {
           if(i == 0) {
               zoomInCamera(trip.start,googleMap);
               addMarker(trip.start,googleMap);
               i++;
           } else {
               zoomInCamera(trip.end,googleMap);
               addMarker(trip.end,googleMap);
           }
       }
   };

    void zoomInCamera(Float[] location, GoogleMap mMap){
        if(mMap == null) {
            return;
        }

        LatLng _location = new LatLng(location[0],location[1]);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(_location,16);
        mMap.animateCamera(cameraUpdate);
    }

    void addMarker(Float[] trip, GoogleMap mMap){
        if(mMap == null) {
            return;
        }

        MarkerOptions options = new MarkerOptions();
        options.title("");
        options.snippet("");
        //options.icon(BitmapDescriptorFactory.fromBitmap(image.getImage()));

        LatLng location = new LatLng(trip[0],trip[1]);
        options.position(location);
        mMap.addMarker(options);
    }

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
        ((MapView)view.findViewById(R.id.map_1)).getMapAsync(mapReady);
        ((MapView)view.findViewById(R.id.map_2)).getMapAsync(mapReady);
        view.findViewById(R.id.btn_trip).setOnClickListener(tripListner);
        view.findViewById(R.id.btn_notes).setOnClickListener(noteListner);
        view.findViewById(R.id.trip_delete).setOnClickListener(delete);
        trips = loadTrips(((TripListFragment.GetUser)mContext).getUserDocuments().getEmail()+TripListFragment.Tag);
        trip = trips.get(position);
    }

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
