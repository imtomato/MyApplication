package com.example.user.myapplication;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
            Log.d("threadTest","thread id:"+Thread.currentThread().getId()+"");
        }
    };



    Runnable updateTimeInfoToUITask = new Runnable() {
        @Override
        public void run() {
            String timeInfo = Calendar.getInstance().getTime().toString();
            timeInfoText.setText(timeInfo);

            uiHandler.postDelayed(this,updateTimePeriodSecs*1000);


        }
    };


}
