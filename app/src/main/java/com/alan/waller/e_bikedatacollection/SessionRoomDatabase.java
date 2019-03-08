package com.alan.waller.e_bikedatacollection;

import android.arch.persistence.db.*;
import android.arch.persistence.room.*;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.*;
import android.util.Log;

@Database(entities = {Session.class, HeartRate.class, GPSData.class}, version = 2)
public abstract class SessionRoomDatabase extends RoomDatabase {

    public abstract SessionDao sessionDao();
    public abstract HeartRateDao heartRateDao();
    public abstract GPSDataDao gpsDataDao();
    private static SessionRoomDatabase INSTANCE;
    private final String TAG = "SessionRoomDatabase";

    public static SessionRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (SessionRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SessionRoomDatabase.class, "session-database")
                        .addCallback(sRoomDatabaseCallback)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }

        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SessionDao sessionDao;
        private final HeartRateDao heartDao;
        private final GPSDataDao gpsDao;
        private final String TAG = "PopulateDbAsync";
        PopulateDbAsync(SessionRoomDatabase db){
            sessionDao = db.sessionDao();
            heartDao = db.heartRateDao();
            gpsDao = db.gpsDataDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG,"database populated");
            sessionDao.insert(new Session("Test Subject",
                    21,
                    180.5,
                    75.0,
                    "Male",
                    Long.parseLong("100"),
                    Long.parseLong("10000")));
            heartDao.insert(new HeartRate(80, 101));
            heartDao.insert(new HeartRate(80, 102));
            heartDao.insert(new HeartRate(80, 103));
            heartDao.insert(new HeartRate(80, 999));
            gpsDao.insert(new GPSData(101, "Long: 1, Lat: 1, Elevation: 0"));
            gpsDao.insert(new GPSData(102, "Long: 2, Lat: 2, Elevation: 1"));
            gpsDao.insert(new GPSData(103, "Long: 3, Lat: 3, Elevation: 2"));
            gpsDao.insert(new GPSData(999, "Long: 999, Lat: 999, Elevation: 999"));

            return null;
        }
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };



}
