package com.randomaxis.mwaste;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;
import com.example.R;

import java.text.NumberFormat;

public class MeterActivity extends Activity {

    // used for scheduling meter updates
    private Handler scheduler = new Handler();

    // plays alarm sound
    MediaPlayer mp3Player;

    // inputs from mainactivity
    private int dollars;
    private int employeeCount;
    private int alarm;

    // remember when activit launched
    private long startTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meter);

        startTime = SystemClock.uptimeMillis();
        mp3Player = MediaPlayer.create(this, R.raw.danger);

        processInputs();

        // launch the scheduler
        scheduler.post(meterUpdate);
    }

    private void processInputs() {
        String employeeCountParam = (String) getIntent().getExtras().get(Constants.EMPLOYEE_COUNT);
        employeeCount = Integer.valueOf(employeeCountParam);

        String dollarsParam = (String) getIntent().getExtras().get(Constants.DOLLARS);
        dollars = Integer.valueOf(dollarsParam);

        String alarmParam = (String) getIntent().getExtras().get(Constants.ALARM);
        alarm = Integer.valueOf(alarmParam);
    }

    // for calculating total dollars spent
    private long now;
    private long delta;
    private final static int msPerHour = 1000 * 60 * 60;
    private float dollarsSpent;

    // for tracking when to play a new alarm
    private int prevAlarms = 0;

    // refresh interval
    private final static int fps = 30;
    private final static int frameDelay = 1000 / fps;

    // formatting as a currency
    private final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();

    private Runnable meterUpdate = new Runnable() {
        public void run() {
            now = SystemClock.uptimeMillis();
            delta = now - startTime;
            dollarsSpent = (float) dollars * (float) delta / msPerHour * employeeCount;

            int warnings = (int) dollarsSpent / alarm;
            if (warnings != prevAlarms) {
                // it's time to trigger a new alarm
                prevAlarms = warnings;
                mp3Player.start();
            }

            // update counter display
            TextView viewById = (TextView) findViewById(R.id.CounterText);
            viewById.setText(currencyInstance.format(dollarsSpent));

            scheduler.postAtTime(this, SystemClock.uptimeMillis() + frameDelay);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mp3Player.stop();
        scheduler.removeCallbacks(meterUpdate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (meterUpdate != null) {
            scheduler.removeCallbacks(meterUpdate);
            scheduler.post(meterUpdate);
        }
    }
}
