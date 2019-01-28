package com.alan.waller.e_bikedatacollection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SessionActivity extends AppCompatActivity {

    final String TAG = "SESSION ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_activity);

        String subjectInfo = "";

        subjectInfo += "Subject Name: " + getIntent().getStringExtra("SUBJECT_NAME");

        Log.d(TAG, subjectInfo);

    }
}