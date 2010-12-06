package com.randomaxis.mwaste;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.R;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        View goButtonView = findViewById(R.id.GoButton);
        goButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // obtain inputs
                TextView employeeCountView = (TextView) view.getRootView().findViewById(R.id.InputEmployees);
                CharSequence employeeCount = employeeCountView.getText();

                TextView dollarsView = (TextView) view.getRootView().findViewById(R.id.InputDollars);
                CharSequence dollars = dollarsView.getText();

                TextView alarmView = (TextView) view.getRootView().findViewById(R.id.InputAlarm);
                CharSequence alarm = alarmView.getText();

                // pass inputs to meteractivity
                Intent intent = new Intent(MainActivity.this, MeterActivity.class);
                intent.putExtra(Constants.EMPLOYEE_COUNT, employeeCount.toString());
                intent.putExtra(Constants.DOLLARS, dollars.toString());
                intent.putExtra(Constants.ALARM, alarm.toString());

                startActivity(intent);
            }
        });
    }
}
