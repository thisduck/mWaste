package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.awt.*;

public class MainActivity extends Activity {
    public final static String EMPLOYEE_COUNT = "EMPLOYEE_COUNT";
    public final static String DOLLARS = "DOLLARS";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        View viewById = findViewById(R.id.GoButton);
        viewById.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                MediaPlayer mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.danger);
//                mediaPlayer.start();

//                new AlertDialog.Builder(view.getContext())
//                        .setMessage("Phyllis is calling")
//                        .show();
                TextView employeeCountView = (TextView) view.getRootView().findViewById(R.id.InputEmployees);
                CharSequence employeeCount = employeeCountView.getText();

                TextView dollarsView= (TextView) view.getRootView().findViewById(R.id.InputDollars);
                CharSequence dollars = dollarsView.getText();

                Intent intent = new Intent(MainActivity.this, MeterActivity.class);
                intent.putExtra(EMPLOYEE_COUNT,employeeCount.toString());
                intent.putExtra(DOLLARS,dollars.toString());
                startActivity(intent);
            }
        });
    }
}
