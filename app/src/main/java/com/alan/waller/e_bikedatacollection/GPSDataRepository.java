package com.alan.waller.e_bikedatacollection;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class GPSDataRepository {

    private GPSDataDao gpsDataDao;

    private LiveData<List<GPSData>> mAllGpsDataPoints;

    public GPSDataRepository(Application app){
        this.gpsDataDao = SessionRoomDatabase.getDatabase(app).gpsDataDao();
        mAllGpsDataPoints = gpsDataDao.getAll();
    }

    public void insertGpsData(GPSData gpsData){new InsertAsyncTask(gpsDataDao).execute(gpsData);}

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
