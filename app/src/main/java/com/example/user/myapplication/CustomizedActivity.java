package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CustomizedActivity extends AppCompatActivity {

    public String activityName ="";
    public final static String debug_tag = "testStage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(debug_tag,activityName+":onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(debug_tag,activityName+":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(debug_tag, activityName + ":onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(debug_tag, activityName + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(debug_tag, activityName + ":onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(debug_tag, activityName + ":onDestroy");
    }



}
