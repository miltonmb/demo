package com.Demo.googlemapdemo;
import android.Manifest;

import android.app.ProgressDialog;

import android.content.pm.PackageManager;

import android.location.Geocoder;

import android.location.Location;

import android.location.LocationListener;

import android.location.LocationManager;

import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;



import com.google.android.gms.maps.CameraUpdate;

import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.maps.model.MarkerOptions;



import java.io.IOException;

import java.util.List;



import Modules.DirectionFinderListener;

import Modules.Route;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {
    private GoogleMap mMap;
    private Button btnMarcar;
    private Button btVerFeed;
    private ProgressDialog progressDialog;
    private LocationManager location;
    private Marker currLocationMarker;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        location = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (location.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    if (currLocationMarker != null) {
                        currLocationMarker.remove();
                    }
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    LatLng latLng = new LatLng(lat, lon);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<android.location.Address> addressList = geocoder.getFromLocation(lat, lon, 1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();
                        //currLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(latLng, 20);
                        mMap.moveCamera(cameraPosition);
                        mMap.animateCamera(cameraPosition);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }



                @Override
                public void onProviderDisabled(String provider) {

                }

            });

        } else if (location.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    if (currLocationMarker != null) {
                        currLocationMarker.remove();
                    }
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    LatLng latLng = new LatLng(lat, lon);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<android.location.Address> addressList = geocoder.getFromLocation(lat, lon, 1);
                        String str = addressList.get(0).getLocality() + ", ";
                        str += addressList.get(0).getCountryName();
                       // currLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(latLng, 20);
                        mMap.moveCamera(cameraPosition);
                        mMap.animateCamera(cameraPosition);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }

            });
        }
    }

    private void sendRequest() {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;

        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {

    }
}