package com.alan.waller.e_bikedatacollection;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.HistoryClient;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Test_Page2 extends AppCompatActivity {

    static TextView heartView;

    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    static final String TAG = "TestPage2";
    FitnessOptions fitnessOptions;
    OnDataPointListener mListener;
    Button readButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_page);

        heartView = (TextView) findViewById(R.id.heartRate);
        readButton = (Button) findViewById(R.id.readButton);

        if (hasRuntimePermissions()) {
            findFitnessDataSourcesWrapper();
        } else {
            requestRuntimePermissions();
        }


        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    readData();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


    }
    private boolean hasRuntimePermissions() {
        Log.i(TAG, "hasRuntimePermissions called");
        int permissionState =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    private void findFitnessDataSourcesWrapper() {
        Log.i(TAG, "findFitnessDataSourcesWrapper called");
        if (hasOAuthPermission()) {
            accessGoogleFit();
        } else {
            requestOAuthPermission();
        }
    }
    private boolean hasOAuthPermission() {
        Log.i(TAG, "hasOAuthPermission called");
        FitnessOptions fitnessOptions = getFitnessSignInOptions();
        return GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions);
    }
    private FitnessOptions getFitnessSignInOptions() {
        Log.i(TAG, "getFitnessSignInOptions calles");
        return FitnessOptions.builder().addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ).build();
    }

    private void requestOAuthPermission() {
        Log.i(TAG, "requestOAuthPermission called");
        FitnessOptions fitnessOptions = getFitnessSignInOptions();
        GoogleSignIn.requestPermissions(
                this,
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(this),
                fitnessOptions);
    }

    private void requestRuntimePermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.BODY_SENSORS);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(
                    Test_Page2.this,
                    new String[] {Manifest.permission.BODY_SENSORS},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult called");
        Log.i(TAG, "Activity result = " + resultCode);
        Log.i(TAG, "Activity request = "  + requestCode);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {

                accessGoogleFit();
            }
        }
    }

    public void accessGoogleFit(){
        Log.i(TAG, "accessGoogleFitCalled");
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_HEART_RATE_BPM)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Successfully subscribed! BPM");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "There was a problem subscribing. BPM");
                        e.printStackTrace();
                    }
                });
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.AGGREGATE_HEART_RATE_SUMMARY)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Successfully subscribed! Summary");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "There was a problem subscribing. Summary");
                        e.printStackTrace();
                    }
                });

    }

    @Override
    protected void onStop(){
        super.onStop();
        try {
            Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .unsubscribe(DataType.TYPE_HEART_RATE_BPM)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "Successfully unsubscribed for data type: " + DataType.TYPE_HEART_RATE_BPM);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Subscription not removed
                            Log.i(TAG, "Failed to unsubscribe for data type: " + DataType.TYPE_HEART_RATE_BPM);
                        }
                    });
            Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .unsubscribe(DataType.AGGREGATE_HEART_RATE_SUMMARY)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "Successfully unsubscribed for data type: " + DataType.TYPE_HEART_RATE_BPM);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Subscription not removed
                            Log.i(TAG, "Failed to unsubscribe for data type: " + DataType.TYPE_HEART_RATE_BPM);
                        }
                    });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void readData(){
        try {
            HistoryClient mClient = Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this));
            new ReadTask().execute(mClient).get();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private class ReadTask extends AsyncTask<HistoryClient, Void, Task<DataReadResponse>>{

        @Override
        protected Task<DataReadResponse> doInBackground(final HistoryClient... params){
            try {
                Log.i(TAG, "readData called");
                // Setting a start and end date using a range of 1 week before this moment.
                Calendar cal = Calendar.getInstance();
                Date now = new Date();
                cal.setTime(now);
                long endTime = cal.getTimeInMillis();
                cal.add(Calendar.WEEK_OF_YEAR, -1);
                long startTime = cal.getTimeInMillis();

                java.text.DateFormat dateFormat = DateFormat.getDateInstance();
                Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
                Log.i(TAG, "Range End: " + dateFormat.format(endTime));

                final DataReadRequest readRequest =
                        new DataReadRequest.Builder()
                                .aggregate(DataType.TYPE_HEART_RATE_BPM, DataType.AGGREGATE_HEART_RATE_SUMMARY)
                                .bucketByTime(1, TimeUnit.DAYS)
                                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                                .build();
                Log.i(TAG, "readRequest made");
                final Task<DataReadResponse> response = params[0].readData(readRequest);
                response.addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        Log.i(TAG, "success");
                        List<DataSet> dataSets = response.getResult().getDataSets();
                        Log.i(TAG, "Datasets: " + dataSets.size());
                        for (DataSet dataSet : dataSets) {
                            dumpDataSet(dataSet);
                        }
                    }
                });
                response.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Failure");
                    }
                });

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Task<DataReadResponse> response){
            //Log.i(TAG, "onPostExecute: " + response.isSuccessful());
            /**try {
                List<DataSet> dataSets = response.getResult().getDataSets();
                for (DataSet dataSet : dataSets) {
                    dumpDataSet(dataSet);
                }
            }catch (Exception e){
                e.printStackTrace();
            }**/
        }
    }


    private static void dumpDataSet(DataSet dataSet) {
        Log.i(TAG, "dumpDataSet called");
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        DateFormat dateFormat = DateFormat.getTimeInstance();
        List<DataPoint> dataList = dataSet.getDataPoints();
        Log.i(TAG, "data points: " + dataList.size());

        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.i(TAG, "Data point:");
            Log.i(TAG, "\tType: " + dp.getDataType().getName());
            Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
            for (Field field : dp.getDataType().getFields()) {
                Log.i(TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
                heartView.setText(dp.getValue(field).toString());
            }
        }
    }


}
