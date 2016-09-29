package com.example.user.myapplication.model;

import android.os.AsyncTask;

import com.example.user.myapplication.PokemonMapFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by user on 2016/9/29.
 */
public class PokemonMapManager {

    GoogleMap googleMap;

    public PokemonMapManager(GoogleMap googleMap){
        super();
        this.googleMap=googleMap;
    }


    public void requestPokemonServer(){




    }


    public static class requestTask extends AsyncTask<String,Void,String>{


        //connect to internet
        @Override
        protected String doInBackground(String... params) {


            return null;
        }


        //back to Main Thread
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



        }
    };







}
