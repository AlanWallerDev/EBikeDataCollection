package com.alan.waller.e_bikedatacollection;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class SessionRepository {

    //session data access object
    private SessionDao sessionDao;

    //list of data coming from db
    private LiveData<List<Session>>mAllSessions;

    SessionRepository(Application app){
        sessionDao =SessionRoomDatabase.getDatabase(app).sessionDao();
        mAllSessions = sessionDao.getAll();
    }

    public LiveData<List<Session>> getmAllSessions(){
        return mAllSessions;
    }

    public void insertSession(Session session){
        new InsertAsyncTask(sessionDao).execute(session);
    }

    private static class InsertAsyncTask extends AsyncTask<Session, Void, Void>{
        private SessionDao sessionDao;
        InsertAsyncTask(SessionDao sessionDao){
            this.sessionDao = sessionDao;
        }

        @Override
        protected Void doInBackground(final Session... sessions) {
            sessionDao.insert(sessions[0]);
            return null;
        }
    }
}
