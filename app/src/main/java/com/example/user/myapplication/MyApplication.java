package com.example.user.myapplication;

import android.app.Application;

import com.example.user.myapplication.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.Parse.Configuration;
import com.parse.ParseObject;


/**
 * Created by user on 2016/9/5.
 */

//App層級最高的,代表整個App
public class MyApplication extends Application {






    @Override
    public void onCreate(){
        super.onCreate();

        //初始ImageLoader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheSize(50 * 1024 * 1024) //50MB
                .diskCacheFileCount(100)
                .build();

        ImageLoader.getInstance().init(config);


        //register Class
        ParseObject.registerSubclass(OwnedPokemonInfo.class);

        //initialize Parse
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .enableLocalDataStore()
                .applicationId("ra4EARRjY0Ac9hJ3xfrg4XeFKsLzwSQ7NJOLZWLr")
                .server("https://parseapi.back4app.com/")
                .clientKey("npW4YLkrOJwQoJPbpkBHKD3uw9C8gZkDd26pbPC1")
                .build());


    }





}
