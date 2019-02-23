package com.alan.waller.e_bikedatacollection;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.SensorRequest;

import java.util.concurrent.TimeUnit;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.util.List;

/**
 * This sample demonstrates how to use the Sensors API of the Google Fit platform to find available
 * data sources and to register/unregister listeners to those sources. It also demonstrates how to
 * authenticate a user with Google Play Services.
 */
public class Test_Page extends AppCompatActivity {

    TextView hR;
    public static final String TAG = "BasicSensorsApi";

    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    // [START mListener_variable_reference]
    // Need to hold a reference to this listener, as it's passed into the "unregister"
    // method in order to stop all sensors from sending data to this listener.
    private OnDataPointListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_page);

        hR = findViewById(R.id.heartRate);


        // When permissions are revoked the app is restarted so onCreate is sufficient to check for
        // permissions core to the Activity's functionality.
        if (hasRuntimePermissions()) {
            findFitnessDataSourcesWrapper();
        } else {
            requestRuntimePermissions();
        }
    }

    /**
     * A wrapper for {@link #findFitnessDataSources}. If the user account has OAuth permission,
     * continue to {@link #findFitnessDataSources}, else request OAuth permission for the account.
     */
    private void findFitnessDataSourcesWrapper() {
        Log.i(TAG, "findFitnessDataSourcesWrapper called");
        if (hasOAuthPermission()) {
            findFitnessDataSources();
        } else {
            requestOAuthPermission();
        }
    }

    /** Gets the {@link FitnessOptions} in order to check or request OAuth permission for the user. */
    private FitnessOptions getFitnessSignInOptions() {
        Log.i(TAG, "getFitnessSignInOptions calles");
        return FitnessOptions.builder().addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ).build();
    }

    /** Checks if user's account has OAuth permission to Fitness API. */
    private boolean hasOAuthPermission() {
        Log.i(TAG, "hasOAuthPermission called");
        FitnessOptions fitnessOptions = getFitnessSignInOptions();
        return GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions);
    }

    /** Launches the Google SignIn activity to request OAuth permission for the user. */
    private void requestOAuthPermission() {
        Log.i(TAG, "requestOAuthPermission called");
        FitnessOptions fitnessOptions = getFitnessSignInOptions();
        GoogleSignIn.requestPermissions(
                this,
                REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(this),
                fitnessOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        findFitnessDataSourcesWrapper();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult called");
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                findFitnessDataSources();
            }
        }
    }
    // [END auth_oncreate_setup]

    /** Finds available data sources and attempts to register on a specific {@link DataType}. */
    private void findFitnessDataSources() {
        Log.i(TAG, "findFitnessDataSources called");
        // [START find_data_sources]
        // Note: Fitness.SensorsApi.findDataSources() requires the ACCESS_FINE_LOCATION permission.
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
                                Log.i(TAG, "Listener Success");
                                Log.i(TAG, "DataSources: " + dataSources.size());
                                for (DataSource dataSource : dataSources) {
                                    Log.i(TAG, "Data source found: " + dataSource.toString());
                                    Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                                    // Let's register a listener to receive Activity data!
                                    if (dataSource.getDataType().equals(DataType.TYPE_HEART_RATE_BPM)
                                            && mListener == null) {
                                        Log.i(TAG, "Data source for HEART RATE found!  Registering.");
                                        registerFitnessDataListener(dataSource);
                                    }
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "failed", e);
                            }
                        });
        // [END find_data_sources]
    }

    /**
     * Registers a listener with the Sensors API for the provided {@link DataSource} and {@link
     * DataType} combo.
     */
    private void registerFitnessDataListener(DataSource dataSource) {
        Log.i(TAG, "registerFitnessDataListener called");
        // [START register_data_listener]
        mListener =
                new OnDataPointListener() {
                    @Override
                    public void onDataPoint(DataPoint dataPoint) {
                        for (final Field field : dataPoint.getDataType().getFields()) {
                            final Value val = dataPoint.getValue(field);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hR.setText(val.toString());
                                }
                            });
                        }
                    }
                };

        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .add(
                        new SensorRequest.Builder()
                                .setDataSource(dataSource) // Optional but recommended for custom data sets.
                                .setDataType(DataType.TYPE_HEART_RATE_BPM) // Can't be omitted.
                                .setSamplingRate(1, TimeUnit.SECONDS)
                                .build(),
                        mListener)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Listener registered!");
                                } else {
                                    Log.e(TAG, "Listener not registered.", task.getException());
                                }
                            }
                        });
        // [END register_data_listener]
    }

    /** Unregisters the listener with the Sensors API. */
    private void unregisterFitnessDataListener() {
        Log.i(TAG, "unRegisterFitnessDataListener called");
        if (mListener == null) {
            // This code only activates one listener at a time.  If there's no listener, there's
            // nothing to unregister.
            return;
        }

        // [START unregister_data_listener]
        // Waiting isn't actually necessary as the unregister call will complete regardless,
        // even if called from within onStop, but a callback can still be added in order to
        // inspect the results.
        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .remove(mListener)
                .addOnCompleteListener(
                        new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if (task.isSuccessful() && task.getResult()) {
                                    Log.i(TAG, "Listener was removed!");
                                } else {
                                    Log.i(TAG, "Listener was not removed.");
                                }
                            }
                        });
        // [END unregister_data_listener]
    }


    /** Initializes a custom log class that outputs both to in-app targets and logcat. */

    /** Returns the current state of the permissions needed. */
    private boolean hasRuntimePermissions() {
        Log.i(TAG, "hasRuntimePermissions called");
        int permissionState =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS);
        return permissionState == PackageManager.PERMISSION_GRANTED;
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
                    Test_Page.this,
                    new String[] {Manifest.permission.BODY_SENSORS},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /** Callback received when a permissions request has been completed. */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult called");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                findFitnessDataSourcesWrapper();
            } else {
                // Permission denied.
                Log.i(TAG, "Permission denied");

            }
        }
    }

}
