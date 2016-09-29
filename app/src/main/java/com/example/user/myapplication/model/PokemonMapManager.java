package com.example.user.myapplication.model;

import android.os.AsyncTask;
import android.util.Log;
import com.example.user.myapplication.utility.Utils;
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

        (new requestTask()).execute("http://140.112.30.42:5001/raw_data");

    }


    public static class requestTask extends AsyncTask<String,Void,String>{


        //connect to internet
        @Override
        protected String doInBackground(String... params) {
            byte[] bytes = Utils.urlToBytes(params[0]);
            if(bytes == null){
                return null;
            }
            return new String(bytes);
        }


        //back to Main Thread
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Pokemon Data:",s); //Stirng s 參數 就是 doInBackgroud做完的值
        }
    };







}
