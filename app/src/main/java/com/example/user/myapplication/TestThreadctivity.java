package com.example.user.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.user.myapplication.utility.Utils;

import java.util.Calendar;
import java.util.TimerTask;

public class TestThreadctivity extends AppCompatActivity {


    Handler uiHandler;
    int updateTimePeriodSecs = 1;
    TextView timeInfoText;

    Handler bgThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_threadctivity);
        timeInfoText = (TextView) findViewById(R.id.timeInfoText);
        uiHandler = new Handler(getMainLooper());
        uiHandler.post(updateTimeInfoToUITask);


        HandlerThread bgThread = new HandlerThread("bgThread");
        bgThread.start();
        bgThreadHandler = new Handler(bgThread.getLooper());
        bgThreadHandler.post(checkThreadIdTask);
        Log.d("threadTest", "Main thread id:" + Thread.currentThread().getId() + "");


    }

    Runnable checkThreadIdTask = new Runnable() {
        @Override
        public void run() {
            Log.d("threadTest", "thread id:" + Thread.currentThread().getId() + "");
        }
    };



    Runnable updateTimeInfoToUITask = new Runnable() {
        @Override
        public void run() {
            String timeInfo = Calendar.getInstance().getTime().toString();
            timeInfoText.setText(timeInfo);

            uiHandler.postDelayed(this, updateTimePeriodSecs * 1000);


        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiEventReceiver,intentFilter);

    }


    BroadcastReceiver wifiEventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                String statusMsg = Utils.getConnectivityStatusString(context);
                Log.d("wifiEvent",statusMsg);

        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiEventReceiver);


    }
}
