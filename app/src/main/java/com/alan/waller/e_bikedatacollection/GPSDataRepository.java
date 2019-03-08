package com.alan.waller.e_bikedatacollection;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GPSDataRepository {

    private GPSDataDao gpsDataDao;

    private LiveData<List<GPSData>> mAllGpsDataPoints;
    private final String TAG = "GPSRepo";
    private List<GPSData> accessedData = new ArrayList<>();

    public GPSDataRepository(Application app){
        this.gpsDataDao = SessionRoomDatabase.getDatabase(app).gpsDataDao();
        mAllGpsDataPoints = gpsDataDao.getAll();
    }

    public LiveData<List<GPSData>> getmAllGpsDataPoints(){return mAllGpsDataPoints;}

    public void insertGpsData(GPSData gpsData){new InsertAsyncTask(gpsDataDao).execute(gpsData);}

    public List<GPSData> accessGpsData(final long startTime, final long endTime, LifecycleOwner owner){
        mAllGpsDataPoints.observe(owner , new Observer<List<GPSData>>() {
            @Override
            public void onChanged(@Nullable List<GPSData> gpsData) {
                gpsDataAccessor(gpsData, startTime, endTime);
            }
        });

        return accessedData;
    }

    public void gpsDataAccessor(List<GPSData> gpsData, long startTime, long endTime){
        GPSData temp;
        Log.d(TAG, gpsData.size() + "");
        accessedData.clear();
        for (int i = 0; i < gpsData.size(); i++) {
            temp = gpsData.get(i);
            if (temp.getTimestamp() >= startTime && temp.getTimestamp() <= endTime) {
                Log.d(TAG, "yes");
                accessedData.add(temp);
                Log.d(TAG, accessedData.size() + "");
            }
        }
    }

    private static class InsertAsyncTask extends AsyncTask<GPSData, Void, Void> {
        private GPSDataDao gpsDataDao;
        InsertAsyncTask(GPSDataDao gpsDataDao){
            this.gpsDataDao = gpsDataDao;
        }

        @Override
        protected Void doInBackground(final GPSData...gpsDatas) {
            gpsDataDao.insert(gpsDatas[0]);
            return null;
        }
    }


}
