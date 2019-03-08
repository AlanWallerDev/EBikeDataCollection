package com.alan.waller.e_bikedatacollection;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HeartRateRepository {

    private HeartRateDao heartRateDao;
    private final String TAG = "HRRepo";

    private LiveData<List<HeartRate>> mAllHeartRates;

    private List<HeartRate> accessedData = new ArrayList<>();

    public HeartRateRepository(Application app) {
        this.heartRateDao = SessionRoomDatabase.getDatabase(app).heartRateDao();
        this.mAllHeartRates = heartRateDao.getAll();
    }

    public LiveData<List<HeartRate>> getmAllHeartRates(){ return mAllHeartRates;}

    public void insertHeartRate(HeartRate heartRate){new InsertAsyncTask(heartRateDao).execute(heartRate);}

    public LiveData<List<HeartRate>> getAllHeartRates(){ return mAllHeartRates;}

    public List<HeartRate> accessHeartRateData(final long startTime, final long endTime, LifecycleOwner owner){
        mAllHeartRates.observe(owner , new Observer<List<HeartRate>>() {
            @Override
            public void onChanged(@Nullable List<HeartRate> heartRates) {
                heartRateAccessor(heartRates, startTime, endTime);
            }
        });
        return accessedData;
    }

    public void heartRateAccessor(List<HeartRate> hrData, long startTime, long endTime){
        HeartRate temp;
        for (int i = 0; i < hrData.size(); i++) {
            temp = hrData.get(i);
            if (temp.getTimeStamp() >= startTime && temp.getTimeStamp() <= endTime) {
                accessedData.add(temp);
            }
        }
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
