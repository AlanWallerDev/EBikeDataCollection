package com.alan.waller.e_bikedatacollection;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

public class DataInsertion extends AppCompatActivity {



    public void insertGpsDataPoint(GPSData gpsData){

        GPSDataRepository gpsDataRepository = new GPSDataRepository(this.getApplication());

        gpsDataRepository.insertGpsData(gpsData);

    }

    public void insertHeartRateData(HeartRate heartRate){
        HeartRateRepository heartRateRepository = new HeartRateRepository(this.getApplication());

        heartRateRepository.insertHeartRate(heartRate);
    }


}
