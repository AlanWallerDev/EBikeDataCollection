package com.alan.waller.e_bikedatacollection;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class GPSDataRepository {

    private GPSDataDao gpsDataDao;

    private LiveData<List<GPSData>> mAllGpsDataPoints;

    public GPSDataRepository(Application app){
        this.gpsDataDao = SessionRoomDatabase.getDatabase(app).gpsDataDao();
        mAllGpsDataPoints = gpsDataDao.getAll();
    }

    public void insertGpsData(GPSData gpsData){new InsertAsyncTask(gpsDataDao).execute(gpsData);}

    public List<GPSData> accessGpsData(long startTime, long endTime){
        List<GPSData> allData = mAllGpsDataPoints.getValue();
        List<GPSData> accessedData = new ArrayList<>();
        GPSData temp;
        for(int i = 0; i < allData.size(); i++){
            temp = allData.get(i);
            if(temp.getTimestamp() > startTime && temp.getTimestamp() < endTime){
                accessedData.add(temp);
            }
        }

        return accessedData;
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
