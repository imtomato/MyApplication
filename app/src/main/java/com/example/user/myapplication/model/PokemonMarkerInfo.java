package com.example.user.myapplication.model;

import android.graphics.Bitmap;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/10/3.
 */
public class PokemonMarkerInfo implements ImageLoadingListener {


    public enum PokemonMarkerType{
        GYM,POKEMON,STOP
    }

    PokemonMarkerType type;
    LatLng location;
    String id;
    String imageURL;
    Marker marker;



    public static PokemonMarkerInfo newInstanceWithJSONObject(JSONObject jsonObject,PokemonMarkerType type) throws JSONException {

        PokemonMarkerInfo pokemonMarkerInfo = new PokemonMarkerInfo();
        pokemonMarkerInfo.type = type;
        pokemonMarkerInfo.location = new LatLng(jsonObject.getDouble("latitude"),jsonObject.getDouble("longitude"));
        if(type == PokemonMarkerType.GYM){
            pokemonMarkerInfo.id = jsonObject.getString("gym_id");
            pokemonMarkerInfo.imageURL = "http://www.csie.ntu.edu.tw/~r03944003/forts/Gym.png";
        }else if(type == PokemonMarkerType.POKEMON){
            pokemonMarkerInfo.id = jsonObject.getString("encounter_id");
            pokemonMarkerInfo.imageURL = "http://www.csie.ntu.edu.tw/~r03944003/listImg/"+jsonObject.getString("pokemon_id")+".png";
        }else if(type == PokemonMarkerType.STOP) {
            pokemonMarkerInfo.id = jsonObject.getString("pokestop_id");
            if (jsonObject.get("lure_expiration") == JSONObject.NULL) {
                pokemonMarkerInfo.imageURL = "http://www.csie.ntu.edu.tw/~r03944003/forts/Pstop.png";
            }else{
                pokemonMarkerInfo.imageURL = "http://www.csie.ntu.edu.tw/~r03944003/forts/PstopLured.png";
            }
        }
        return pokemonMarkerInfo;
    }


    public void addMarkerToGoogleMap(GoogleMap googleMap){

        MarkerOptions markerOptions = new MarkerOptions().position(location)
                .title(id);
        marker = googleMap.addMarker(markerOptions);
        ImageLoader.getInstance().loadImage(imageURL,this);



    }
    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(loadedImage);
        marker.setIcon(icon);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }



}
