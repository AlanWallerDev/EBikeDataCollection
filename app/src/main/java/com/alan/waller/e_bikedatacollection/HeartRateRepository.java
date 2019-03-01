package com.alan.waller.e_bikedatacollection;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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
