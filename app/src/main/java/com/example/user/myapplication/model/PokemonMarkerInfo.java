package com.example.user.myapplication.model;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/10/3.
 */
public class PokemonMarkerInfo {

    public enum PokemonMarkerType{
        GYM,POKEMON,STOP
    }

    PokemonMarkerType type;
    LatLng location;
    String id;
    String imageURL;




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



}
