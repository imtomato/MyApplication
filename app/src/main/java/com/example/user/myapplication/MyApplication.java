package com.example.user.myapplication;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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




    }





}
