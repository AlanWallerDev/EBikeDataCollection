package com.alan.waller.e_bikedatacollection;

import android.app.ActionBar;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SessionDataDisplayActivity extends AppCompatActivity {

    private final String TAG = "SessionDataDisplay";
    public final List<HeartRate> hrlist = new ArrayList<>();
    public final List<GPSData> gpsList = new ArrayList<>();

    public TextView gView;
    public TextView hView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_data);
        long startTime = getIntent().getLongExtra("START_TIME", -1);
        long endTime = getIntent().getLongExtra("END_TIME", -1);

        hView  = (TextView) findViewById(R.id.hView);
        gView  = (TextView) findViewById(R.id.gView);

        gView.setText(Long.toString(startTime));
        hView.setText(Long.toString(endTime));


        GPSDataRepository gpsDataRepository = new GPSDataRepository(this.getApplication());
        HeartRateRepository heartRateRepository = new HeartRateRepository(this.getApplication());

        LiveData<List<HeartRate>> hrLiveData = heartRateRepository.getAllHeartRates();
        LiveData<List<GPSData>> gpsLiveData = gpsDataRepository.getmAllGpsDataPoints();

        hrLiveData.observe(this, new Observer<List<HeartRate>>() {
            @Override
            public void onChanged(@Nullable List<HeartRate> heartRates) {
                for(int i = 0; i < heartRates.size(); i++){
                    hrlist.add(heartRates.get(i));
                    hrUpdate();
                }
            }
        });

        gpsLiveData.observe(this, new Observer<List<GPSData>>() {
            @Override
            public void onChanged(@Nullable List<GPSData> gpsData) {
                for(int i = 0; i < gpsData.size(); i++){
                    gpsList.add(gpsData.get(i));
                    gpsUpdate();
                }
            }
        });

    }

    public void hrUpdate(){
        String temp = "";
        for(int i = 0; i < hrlist.size(); i++){
            temp += hrlist.get(i).getTimeStamp() + ": " + hrlist.get(i).getHeartRate() + ", ";
        }
        hView.setText(temp);
    }

    public void gpsUpdate(){
        String temp = "";
        for(int i = 0; i < gpsList.size(); i++){
            temp += gpsList.get(i).getTimestamp() + ": " + gpsList.get(i).getData() + ", ";
        }
        gView.setText(temp);
    }

}
