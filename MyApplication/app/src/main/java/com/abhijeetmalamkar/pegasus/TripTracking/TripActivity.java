package com.abhijeetmalamkar.pegasus.TripTracking;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhijeetmalamkar.pegasus.Alert;
import com.abhijeetmalamkar.pegasus.GpsTracker;
import com.abhijeetmalamkar.pegasus.R;
import com.abhijeetmalamkar.pegasus.Trip;
import com.abhijeetmalamkar.pegasus.TripsAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TripActivity extends AppCompatActivity {

    String email;
    public static String Tag = "TripListFragment.Tag";
    ArrayList<Trip> trips;
    public TripsAdapter adapter;
    LinearLayout details;
    Trip trip;
    int position;
    private String m_Text = "No Name";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        details = (LinearLayout) findViewById(R.id.details);
        details.setVisibility(View.GONE);

        Intent intent = getIntent();

        if(intent!=null) {
        email = intent.getStringExtra("Email");
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        setTitle("Trip Tracker");

        ListView list = (ListView) findViewById(R.id.list_view);

        trips = loadTrips(email+Tag);

        if(trips == null)
            trips = new ArrayList<>();
        adapter = new TripsAdapter(this, trips);
        list.setAdapter(adapter);
        list.setOnItemClickListener(listener);
        }

        ImageView image = (ImageView) findViewById(R.id.cancel_btn);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details.setVisibility(View.GONE);
            }
        });
        button = (Button) findViewById(R.id.endButton);
        button.setOnClickListener(endListner);
        button.setVisibility(View.GONE);
    }

    View.OnClickListener endListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GpsTracker gt = new GpsTracker(TripActivity.this);
            Location l = gt.getLocation();
            Float[] points = {(float) l.getLatitude(), (float) l.getLongitude()};
            trip.setEnd(points);
            trip.setLocations(new String[]{getAddress(trip.getStart())
                    , getAddress(trip.getEnd())});
            trips.add(trip);
            adapter.notifyDataSetChanged();
            saveTrips(email + Tag, trips);
            trip = null;
            button.setVisibility(View.GONE);
        }
    };

    private TextWatcher tollWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!String.valueOf(s).equals("")) {
                trip.setToll(Float.valueOf(String.valueOf(s)));
                saveTrips(email+Tag,trips);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

    private TextWatcher parkingWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!String.valueOf(s).equals("")) {
                trip.setParking(Float.valueOf(String.valueOf(s)));
                saveTrips(email+Tag,trips);
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
                saveTrips(email+Tag,trips);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };



    private View.OnClickListener tripListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            findViewById(R.id.map_views).setVisibility(View.VISIBLE);
           findViewById(R.id.toll_layout).setVisibility(View.GONE);
        }
    };

    private View.OnClickListener noteListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          findViewById(R.id.map_views).setVisibility(View.GONE);
            findViewById(R.id.toll_layout).setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener delete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            trips.remove(position);
            saveTrips(email+Tag,trips);
            details.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            details.setVisibility(View.GONE);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add,menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuItem item = (MenuItem) menu.findItem(R.id.btn_logout);
//        item.setVisible(false);
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.add, menu);
//    }

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
            if(trip == null) {
            AlertDialog.Builder build = new AlertDialog.Builder(TripActivity.this);
            build.setTitle("Start a trip!" );
            build.setMessage( "Start a trip!");
            build.setPositiveButton( "Start" , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

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
                        GpsTracker gt = new GpsTracker(TripActivity.this);
                        Location l = gt.getLocation();
                        if(l!=null) {
                            final Calendar c = Calendar.getInstance();
                            Float[] points = {(float) l.getLatitude(), (float) l.getLongitude()};
                            trip = new Trip(m_Text, points, null, c.getTime());
                            button.setVisibility(View.VISIBLE);
                        }
                        else {
                            Toast.makeText(TripActivity.this,"No Location",Toast.LENGTH_SHORT).show();
                        }

                }

            });
                  build.setNegativeButton("Cancel", null);
            build.show();
            } else {
                Alert.show(TripActivity.this,"Trip!","Already Started a trip please cancel it first.",null,null);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            trip = trips.get(i);
            position = i;
            details.setVisibility(View.VISIBLE);

            findViewById(R.id.btn_trip).setOnClickListener(tripListner);
            findViewById(R.id.btn_notes).setOnClickListener(noteListner);
            findViewById(R.id.trip_delete).setOnClickListener(delete);
            ((TextView) findViewById(R.id.from_)).setText(trip.getLocations()[0]);
            ((TextView) findViewById(R.id.to_)).setText(trip.getLocations()[1]);
            //trip = trips.get(position);
            Log.d("", "onViewCreated: " + trip.locations[0]);

            EditText toll = (EditText) findViewById(R.id.num_toll);
            EditText parking = (EditText) findViewById(R.id.num_parking);
            EditText note = (EditText) findViewById(R.id.str_notes);

            toll.setText(trip.getToll() != null ? String.valueOf(trip.getToll()) : "");
            parking.setText(trip.getParking() != null ? String.valueOf(trip.getParking()) : "");
            note.setText(trip.getNote() != null ? trip.getNote() : "");

            toll.addTextChangedListener(tollWatcher);
            parking.addTextChangedListener(parkingWatcher);
            note.addTextChangedListener(noteWatcher);
            Location targetLocation = new Location("");//provider name is unnecessary
            targetLocation.setLatitude(trip.getStart()[0]);//your coords of course
            targetLocation.setLongitude(trip.getStart()[1]);
            getFragmentManager().beginTransaction().replace(R.id.map_1,CustomMapFragment.newInstance(targetLocation)).commit();

            Location targetLocation_1 = new Location("");//provider name is unnecessary
            targetLocation_1.setLatitude(trip.getEnd()[0]);//your coords of course
            targetLocation_1.setLongitude(trip.getEnd()[1]);
            getFragmentManager().beginTransaction().replace(R.id.map_2,CustomMapFragment.newInstance(targetLocation_1)).commit();
        }
    };



    public String getAddress(Float[] location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return add;
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        getuser.details(trips.get(position),position);
//    }

    private void saveTrips(String filename, ArrayList<Trip> trips){
        try {
            FileOutputStream fos = openFileOutput(filename + ".bin", MODE_PRIVATE);
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
            FileInputStream fis = openFileInput(filename + ".bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<Trip>) ois.readObject();
            ois.close();

        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
