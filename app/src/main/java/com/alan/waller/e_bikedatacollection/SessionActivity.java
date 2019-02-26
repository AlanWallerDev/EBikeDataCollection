package com.alan.waller.e_bikedatacollection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SessionActivity extends AppCompatActivity {

    final String TAG = "SESSION ACTIVITY";

    static String subjectName;
    static int subjectAge;
    static double subjectWeight;
    static double subjectHeight;
    static String subjectSex;
    static int refreshRate;
    static Date startDate;
    static String startDateString;
    static Long startTime;
    static Long endTime;
    static Long sessionLength;
    static Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_activity);
        subjectName = getIntent().getStringExtra("SUBJECT_NAME");
        subjectAge = Integer.parseInt(getIntent().getStringExtra("SUBJECT_AGE"));
        subjectWeight = Integer.parseInt(getIntent().getStringExtra("SUBJECT_WEIGHT"));
        subjectHeight = Integer.parseInt(getIntent().getStringExtra("SUBJECT_HEIGHT"));
        subjectSex = getIntent().getStringExtra("SUBJECT_SEX");
        refreshRate = getIntent().getIntExtra("REFRESH_RATE", -1);

        Button startButton = (Button) findViewById(R.id.startButton);
        Button endButton = (Button) findViewById(R.id.stopButton);

        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDate = Calendar.getInstance().getTime();
                startDateString = df.format(startDate);
                startTime = System.currentTimeMillis();
                Toast.makeText(SessionActivity.this, "Session Started", Toast.LENGTH_SHORT).show();
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTime = System.currentTimeMillis();
                sessionLength = endTime - startTime;
                session = new Session(subjectName, subjectAge, subjectHeight, subjectWeight, subjectSex, sessionLength, startDateString);
                Toast.makeText(SessionActivity.this, session.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        String subjectInfo = "";

        subjectInfo += "Subject Name: " + getIntent().getStringExtra("SUBJECT_NAME");

        Log.d(TAG, subjectInfo);

    }
}