package com.example.user.myapplication.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.user.myapplication.utility.Utils;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by user on 2016/9/29.
 */
public class PokemonMapManager implements RequestCallback {

    GoogleMap googleMap;
    HashMap <String,PokemonMarkerInfo> pokemonMarkerInfoHashMap = new
            HashMap<>();

    public PokemonMapManager(GoogleMap googleMap) {
        super();
        this.googleMap = googleMap;
    }


    public void requestPokemonServer() {

        (new requestTask(this)).execute("http://140.112.30.42:5001/raw_data");



    }

    @Override
    public void callback(JSONArray gyms, JSONArray pokemons, JSONArray pokeStops) {
            addMarkerInFromJsonArray(gyms, PokemonMarkerInfo.PokemonMarkerType.GYM);
            addMarkerInFromJsonArray(pokemons, PokemonMarkerInfo.PokemonMarkerType.POKEMON);
            addMarkerInFromJsonArray(pokeStops, PokemonMarkerInfo.PokemonMarkerType.STOP);
    }

    public void addMarkerInFromJsonArray(JSONArray jsonArray,PokemonMarkerInfo.PokemonMarkerType type){
        for(int i = 0 ; i < jsonArray.length() ; i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PokemonMarkerInfo pokemonMarkerInfo = PokemonMarkerInfo.newInstanceWithJSONObject(jsonObject, type);
                pokemonMarkerInfoHashMap.put(pokemonMarkerInfo.id,pokemonMarkerInfo);
                pokemonMarkerInfo.addMarkerToGoogleMap(googleMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }



    public static class requestTask extends AsyncTask<String, Void, String> {

        //避免memory leak,用指標暫存,減少占住記憶體
        WeakReference<RequestCallback> requestCallbackWeakReference;

        public requestTask(RequestCallback requestCallback) {
            requestCallbackWeakReference = new WeakReference<RequestCallback>(requestCallback);
        }


        //connect to internet
        @Override
        protected String doInBackground(String... params) {
            byte[] bytes = Utils.urlToBytes(params[0]);
            if (bytes == null) {
                return null;
            }
            return new String(bytes);
        }


        //back to Main Thread
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Pokemon Data:", s); //Stirng s 參數 就是 doInBackgroud做完的值
            RequestCallback requestCallback = requestCallbackWeakReference.get();
            if(requestCallback != null && s != null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray gymsArray = jsonObject.getJSONArray("gyms");
                    JSONArray pokemonsArray = jsonObject.getJSONArray("pokemons");
                    JSONArray pokeStopsArray = jsonObject.getJSONArray("pokestops");
                    requestCallback.callback(gymsArray,pokemonsArray,pokeStopsArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    }
}

interface RequestCallback {
    void callback(JSONArray gyms,JSONArray pokemons,JSONArray pokeStops);
}



