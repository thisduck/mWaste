package com.example;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Kallin
 * Date: 12/5/10
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MeterActivity extends Activity {

    private Handler mHandler = new Handler();

    MediaPlayer mediaPlayer;

    private int dollars = 0;
    private int employeeCount = 0;


    private long startTime;

    private float dollarsSpent = 0;
    private final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meter);

        startTime = SystemClock.uptimeMillis();

        String employeeCountParam = (String) getIntent().getExtras().get(MainActivity.EMPLOYEE_COUNT);
        employeeCount = Integer.valueOf(employeeCountParam);

        String dollarsParam = (String) getIntent().getExtras().get(MainActivity.DOLLARS);
        dollars = Integer.valueOf(dollarsParam);

        mediaPlayer = MediaPlayer.create(this, R.raw.danger);


        mHandler.post(mUpdateTimeTask);


//                mediaPlayer.start();
    }

    long now;
    long delta;
    final static int msPerHour = 1000 * 60 * 60;

    int prevWarnings = 0;

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            now = SystemClock.uptimeMillis();
            delta = now-startTime;

            dollarsSpent = (float)dollars * (float)delta / msPerHour * employeeCount;

            int warnings = (int) dollarsSpent / 10;
            if (warnings!=prevWarnings){
                prevWarnings=warnings;
                mediaPlayer.start();
            }

            TextView viewById = (TextView) findViewById(R.id.CounterText);
            viewById.setText(currencyInstance.format(dollarsSpent));


            //final long start = mStartTime;
            long now = SystemClock.uptimeMillis();
            mHandler.postAtTime(this, now + 20);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUpdateTimeTask != null) {
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.post(mUpdateTimeTask);
        }
    }
}
