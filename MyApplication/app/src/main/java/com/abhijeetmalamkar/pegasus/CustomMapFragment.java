package com.abhijeetmalamkar.pegasus;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class CustomMapFragment extends MapFragment implements OnMapReadyCallback{

    public static final String Tag = "CustomMapFragment";
    private GoogleMap mMap;
    private Context _context;
    private Location current;

    private LocationManager mLocationManager;
    private boolean mRequestingUpdates = false;


    public static CustomMapFragment newInstance(Location current) {
        CustomMapFragment fragment = new CustomMapFragment();
        fragment.current = current;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        getMapAsync(this);
//        addMarker(current);
//        zoomInCamera(current);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _context = context;
    }

    private void zoomInCamera(Location location) {
        if (mMap == null) {
            return;
        }

        LatLng _location = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(_location, 16);
        mMap.animateCamera(cameraUpdate);
    }

    private void addMarker(Location location_) {
        if (mMap == null) {
            return;
        }

        MarkerOptions options = new MarkerOptions();
        LatLng location = new LatLng(location_.getLatitude(), location_.getLongitude());
        options.position(location);
        mMap.addMarker(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        addMarker(current);
        zoomInCamera(current);
    }
}
