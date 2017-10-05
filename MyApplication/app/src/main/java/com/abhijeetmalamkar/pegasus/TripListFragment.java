package com.abhijeetmalamkar.pegasus;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TripListFragment extends ListFragment implements TripsAdapter.DeleteTrip,Update {

    public static String Tag = "TripListFragment.Tag";
    Context mContext;
    ArrayList<Trip> trips;
    public TripsAdapter adapter;

    GetUser getuser;

    @Override
    public void update_() {
        trips.removeAll(trips);
        trips.addAll(loadTrips(getuser.getUserDocuments().getEmail()+Tag));
        adapter.notifyDataSetChanged();
    }

    public interface GetUser {
        User getUserDocuments();
        void details(Trip trip, int position);
    }

    public TripListFragment() {
        // Required empty public constructor
    }

    public static TripListFragment newInstance() {
        TripListFragment fragment = new TripListFragment();
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
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = (MenuItem) menu.findItem(R.id.btn_logout);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add, menu);
    }

    Trip trip;
    private String m_Text = "No Name";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_add) {

//            GpsTracker gt = new GpsTracker(mContext);
//            Location l = gt.getLocation();
//            if( l == null){
//                Toast.makeText(mContext,"GPS unable to get Value",Toast.LENGTH_SHORT).show();
//            }else {
//                double lat = l.getLatitude();
//                double lon = l.getLongitude();
//                Toast.makeText(mContext,"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
//            }

            AlertDialog.Builder build = new AlertDialog.Builder(mContext);
            build.setTitle(trip == null ? "Start a trip!" : "End the trip!");
            build.setMessage(trip == null ? "Start a trip!" : "End the trip!");
            build.setPositiveButton(trip == null ? "Start" : "End", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(trip == null) {
//                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
//                        builder.setTitle("Title");
//                        // Set up the input
//                        final EditText input = new EditText(mContext);
//                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                        input.setInputType(InputType.TYPE_CLASS_TEXT);
//                        builder.setView(input);
//
//                        // Set up the buttons
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (!input.getText().toString().trim().isEmpty()) {
//                                    m_Text = input.getText().toString();
//                                }
//                                final Calendar c = Calendar.getInstance();
//                                trip = new Trip(m_Text, new Location[2],c.getTime());
//                                GpsTracker gt = new GpsTracker(mContext);
//                                Location l = gt.getLocation();
//                                trip.locations[0] = l;
//
//                            }
//                        });
//                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                        builder.show();
                        GpsTracker gt = new GpsTracker(mContext);
                        Location l = gt.getLocation();
                                final Calendar c = Calendar.getInstance();
                        Float[] points = {(float)l.getLatitude(),(float)l.getLongitude()};
                                trip = new Trip(m_Text,points,null,c.getTime());


                            } else {
                        GpsTracker gt = new GpsTracker(mContext);
                        Location l = gt.getLocation();
                        Float[] points = {(float)l.getLatitude(),(float)l.getLongitude()};
                        trip.setEnd(points);
                        trip.setLocations(new String[]{getAddress(trip.getStart())
                                ,getAddress(trip.getEnd())});
                        trips.add(trip);
                        adapter.notifyDataSetChanged();
                        saveTrips(getuser.getUserDocuments().getEmail()+Tag,trips);
                        trip = null;
                    }
                }
            });
            build.setNegativeButton("Cancel", null);
            build.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public String getAddress(Float[] location) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        String add = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(location[0], location[1], 1);
            if (addresses.size() > 0) {
                Address obj = addresses.get(0);
                add = obj.getAddressLine(0);
                add = add + "\n" + obj.getCountryName();
                add = add + "\n" + obj.getCountryCode();
                add = add + "\n" + obj.getAdminArea();
                add = add + "\n" + obj.getPostalCode();
                add = add + "\n" + obj.getSubAdminArea();
                add = add + "\n" + obj.getLocality();
                add = add + "\n" + obj.getSubThoroughfare();

                Log.v("IGA", "Address" + add);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return add;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        getuser.details(trips.get(position),position);
    }

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
        // Inflate the layout for this fragment
        trips = loadTrips(getuser.getUserDocuments().getEmail()+Tag);
        if(trips == null)
            trips = new ArrayList<>();
        adapter = new TripsAdapter(mContext, trips);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_trip_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if(context instanceof GetUser) {
            getuser = (GetUser) context;
        }
        if(context instanceof MainActivity) {
            ((MainActivity) context).update = TripListFragment.this;
        }
    }

    @Override
    public void deleteTrip(Date date) {
        Log.i("", "deleteTrip: " + date);
    }

    @Override
    public void getTrip(Trip trip) {
        trips.get(trip.position).setToll(trip.toll);
        saveTrips(getuser.getUserDocuments().getEmail()+Tag,trips);
    }
}
