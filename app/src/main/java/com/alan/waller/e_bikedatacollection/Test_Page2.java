package com.alan.waller.e_bikedatacollection;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test_Page2 extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    String TAG = "TestPage2";
    FitnessOptions fitnessOptions;
    OnDataPointListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_page);

         fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                .build();

        if(!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)){
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions
            );
        }else{
            accessGoogleFit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_PERMISSIONS_REQUEST_CODE){
                accessGoogleFit();
            }
        }
    }

    public void registerFitnessDataListener(DataSource dataSource, DataType dataType){
        mListener =
                new OnDataPointListener() {
                    @Override
                    public void onDataPoint(DataPoint dataPoint) {
                        for(Field field : dataPoint.getDataType().getFields()) {
                            Value val = dataPoint.getValue(field);
                            Log.i(TAG, "Detected DataPoint Field: " + field.getName());
                            Log.i(TAG, "Detected DataPoint value: " + val);
                        }
                    }
                };

        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .add(
                        new SensorRequest.Builder()
                            .setDataSource(dataSource)
                        .setDataType(dataType)
                        .setSamplingRate(3, TimeUnit.SECONDS)
                        .build(),
                        mListener
                ).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.i(TAG, "Listener Registered!");
                        }else{
                            Log.e(TAG, "Listener Not Registered", task.getException());
                        }
                    }
                }
        );
    }


    private void accessGoogleFit() {


        //the following lists all data sources

        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .findDataSources(
                        new DataSourcesRequest.Builder()
                            .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                        .setDataSourceTypes(DataSource.TYPE_RAW)
                        .build())
                .addOnSuccessListener(
                        new OnSuccessListener<List<DataSource>>() {
                            @Override
                            public void onSuccess(List<DataSource> dataSources) {
                                for(DataSource dataSource : dataSources){
                                    Log.i(TAG, "Data Source Found: " + dataSource.toString());
                                    Log.i(TAG, "Data Source Type: " + dataSource.getDataType().getName());

                                    //Regitster listener to receive data
                                    if(dataSource.getDataType().equals(DataType.TYPE_HEART_RATE_BPM) && mListener == null){
                                        Log.i(TAG, "Data source for LOCATION_SAMPLE found! Registering.");
                                        registerFitnessDataListener(dataSource, DataType.TYPE_HEART_RATE_BPM);

                                    }
                                }
                            }
                        }
                ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "failed", e);
                    }
                }
        );

    }




}
