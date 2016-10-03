package com.example.user.myapplication;


import android.*;
import android.Manifest;

import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.widget.Toast;

import com.example.user.myapplication.model.PokemonMapManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks
        , OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener
        , LocationListener {


    GoogleMap googleMap;
    GoogleApiClient googleAPiClient;
    LocationRequest locationRequest;
    PokemonMapManager pokemonMapManager;

    boolean firstRequestLocation = true;


    public PokemonMapFragment() {
        // Required empty public constructor
    }


    public static PokemonMapFragment newInstance() {

        Bundle args = new Bundle();

        PokemonMapFragment fragment = new PokemonMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
    this.googleMap = googleMap;
        pokemonMapManager = new PokemonMapManager(googleMap);
        createGoogleApiClient();


    }

    public void createGoogleApiClient(){
        if(googleAPiClient == null){
            googleAPiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleAPiClient.connect();
            }
        }


    @Override
    public void onConnected(Bundle bundle) {
        // API23 before would ask users permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
            return;
        }

        pokemonMapManager.requestPokemonServer();


        if(locationRequest == null){
            locationRequest = new LocationRequest();
            locationRequest.setInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.FusedLocationApi.requestLocationUpdates(googleAPiClient,locationRequest,this);


        }







    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onConnected(null);
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(), "Google API Clinet Connection Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Google API Clinet Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(firstRequestLocation) {

            CameraUpdate cameraUpdate = CameraUpdateFactory
                    .newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 30);
            googleMap.moveCamera(cameraUpdate);
            firstRequestLocation = false;

        }
        pokemonMapManager.requestPokemonServer();

    }

}




