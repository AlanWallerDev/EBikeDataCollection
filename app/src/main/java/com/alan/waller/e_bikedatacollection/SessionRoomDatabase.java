package com.alan.waller.e_bikedatacollection;

import android.arch.persistence.db.*;
import android.arch.persistence.room.*;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.*;

@Database(entities = {Session.class}, version = 1)
public abstract class SessionRoomDatabase extends RoomDatabase {

    public abstract SessionDao sessionDao();
    private static SessionRoomDatabase INSTANCE;

    public static SessionRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (SessionRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SessionRoomDatabase.class, "session-database")
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }

        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SessionDao mDao;
        PopulateDbAsync(SessionRoomDatabase db){
            mDao = db.sessionDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.insert(new Session("Test Subject",
                    21,
                    180.5,
                    75.0,
                    "Male",
                    Long.parseLong("123"),
                    "01-10-2010"));
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
