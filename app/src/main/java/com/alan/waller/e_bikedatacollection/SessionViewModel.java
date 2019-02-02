package com.alan.waller.e_bikedatacollection;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class SessionViewModel extends AndroidViewModel {

    private SessionRepository sessionRepository;

    private LiveData<List<Session>> allSessions;

    public SessionViewModel(Application app){
        super(app);
        sessionRepository = new SessionRepository(app);
        allSessions =sessionRepository.getmAllSessions();
    }

    public LiveData<List<Session>> getAllSessions() {
        return allSessions;
    }

    public void insert(Session session){
        sessionRepository.insertSession(session);
    }



}
