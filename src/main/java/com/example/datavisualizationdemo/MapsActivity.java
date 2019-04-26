package com.example.datavisualizationdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int index;
    double lat;
    double lon;
    String str;
    Intent in;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        in = getIntent();
        str = in.getStringExtra("com.example.datavisualizationdemo.PIC_INDEX");
        System.out.println(str);
        String[] strArr = str.split(" ");
        System.out.println(strArr[1]);
        index = Integer.valueOf(strArr[0]);
        lon = Double.valueOf(strArr[1]);
        lat = Double.valueOf(strArr[2]);
        System.out.println(lat);
        System.out.println(lon);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        str = in.getStringExtra("com.example.datavisualizationdemo.PIC_INDEX");
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng usc = new LatLng(lon, lat);
        mMap.addMarker(new MarkerOptions().position(usc).title("Sensor"+Integer.toString(index)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(usc));
    }
}