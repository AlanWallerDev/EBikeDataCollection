package com.alan.waller.e_bikedatacollection;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SessionDataDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_data);
        long startTime = getIntent().getLongExtra("START_TIME", -1);
        long endTime = getIntent().getLongExtra("END_TIME", -1);

        TextView gView = (TextView) findViewById(R.id.gView);
        TextView hView = (TextView) findViewById(R.id.hView);

        GPSDataRepository gpsDataRepository = new GPSDataRepository(this.getApplication());
        HeartRateRepository heartRateRepository = new HeartRateRepository(this.getApplication());

        List<GPSData> gpsData = gpsDataRepository.accessGpsData(startTime, endTime);
        List<HeartRate> heartRates = heartRateRepository.accessHeartRateData(startTime, endTime);

        ScrollView gpsView = findViewById(R.id.gpsDataView);
        ScrollView hrView = findViewById(R.id.heartRateDataView);

        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        String gpsString = "";
        for(int i = 0; i < gpsData.size(); i++){
            gpsString += gpsData.get(i).toString() + "|||";
        }

        gView.setText(gpsString);
        String hString = "";
        for(int i = 0; i < gpsData.size(); i++){
            TextView tv  = new TextView(getApplicationContext());
            tv.setLayoutParams(layoutParams);
            hString += gpsData.get(i).toString() + "|||";
        }

    }

}
