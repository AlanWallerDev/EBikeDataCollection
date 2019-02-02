package com.alan.waller.e_bikedatacollection;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
        rv.hasFixedSize();

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
