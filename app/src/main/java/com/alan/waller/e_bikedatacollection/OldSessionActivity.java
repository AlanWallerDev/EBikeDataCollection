package com.alan.waller.e_bikedatacollection;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OldSessionActivity extends AppCompatActivity {

    private SessionViewModel sessionViewModel;

    private SessionRecyclerViewAdapter sessionRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_sessions_activity);
        Log.d("OldSession", "created");

        RecyclerView rv =(RecyclerView) findViewById(R.id.recyclerview);

        sessionRecyclerViewAdapter = new SessionRecyclerViewAdapter(this);
        rv.setAdapter(sessionRecyclerViewAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        sessionViewModel = ViewModelProviders.of(this).get(SessionViewModel.class);

        sessionViewModel.getAllSessions().observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(@Nullable List<Session> sessions) {
                Log.d("OldSessions", "GetAllSessions Ran");
                sessionRecyclerViewAdapter.setSessions(sessions);
            }
        });


    }



}
