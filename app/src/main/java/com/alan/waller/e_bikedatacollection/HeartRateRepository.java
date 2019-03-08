package com.alan.waller.e_bikedatacollection;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class HeartRateRepository {

    private HeartRateDao heartRateDao;

    private LiveData<List<HeartRate>> mAllHeartRates;

    public HeartRateRepository(Application app) {
        this.heartRateDao = SessionRoomDatabase.getDatabase(app).heartRateDao();
        this.mAllHeartRates = heartRateDao.getAll();
    }

    public LiveData<List<HeartRate>> getmAllHeartRates(){ return mAllHeartRates;}

    public void insertHeartRate(HeartRate heartRate){new InsertAsyncTask(heartRateDao).execute(heartRate);}

    public List<HeartRate> accessHeartRateData(long startTime, long endTime){
        List<HeartRate> allData = mAllHeartRates.getValue();
        List<HeartRate> accessedData = new ArrayList<>();
        HeartRate temp;
        for(int i = 0; i < allData.size(); i++){
            temp = allData.get(i);
            if(temp.getTimeStamp() > startTime && temp.getTimeStamp() < endTime){
                accessedData.add(temp);
            }
        }

        return accessedData;
    }

    private static class InsertAsyncTask extends AsyncTask<HeartRate, Void, Void> {
        private HeartRateDao heartRateDao;
        InsertAsyncTask(HeartRateDao heartRateDao){
            this.heartRateDao = heartRateDao;
        }

        @Override
        protected Void doInBackground(final HeartRate... heartRates) {
            heartRateDao.insert(heartRates[0]);
            return null;
        }
    }

}
