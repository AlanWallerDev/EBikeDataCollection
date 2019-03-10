package com.alan.waller.e_bikedatacollection;




import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.SensorRequest;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.util.List;


public class Test_Page extends AppCompatActivity {

    TextView hR;
    public static final String TAG = "BasicSensorsApi";


    private OnDataPointListener mListener;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bleScanner;
    private BluetoothGatt bleGatt;
    private int REQUEST_ENABLE_BT = 20;
    //TODO:find our UUID
    private UUID heatRateUUID = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    private final int MY_PERMISSION_ACCESS_COURSE = 99;
    private final int MY_PERSMISSION_ACCESS_FINE = 98;
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            //TODO: change this name to the watch name
            if("Gopher".equals(result.getDevice().getName())) {
                Toast.makeText(Test_Page.this, "Gopher found",
                        Toast.LENGTH_SHORT).show();
                if(bleScanner != null) {
                    bleScanner.stopScan(scanCallback);
                }
                bleGatt =
                        result.getDevice().connectGatt(
                                getApplicationContext(), false, bleGattCallback);
            }super.onScanResult(callbackType, result);
        }
    };


    private BluetoothGattCallback bleGattCallback = new BluetoothGattCallback()
    {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int
                newState) {
            gatt.discoverServices();
            super.onConnectionStateChange(gatt, status, newState);
        }
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            BluetoothGattService service =
                    gatt.getService(heatRateUUID);
            BluetoothGattCharacteristic temperatureCharacteristic =
                    service.getCharacteristic(heatRateUUID);
            gatt.readCharacteristic(temperatureCharacteristic);
            super.onServicesDiscovered(gatt, status);
        }
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, final
        BluetoothGattCharacteristic characteristic, int status) {
            final String value = characteristic.getStringValue(0);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv;

                    tv = (TextView) Test_Page.this.findViewById(
                            R.id.heartRate);

                    tv.setText(value);
                }
            });
            BluetoothGattService service =
                    gatt.getService(heatRateUUID);
            //readNextCharacteristic(gatt, characteristic);
            super.onCharacteristicRead(gatt, characteristic, status);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COURSE);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERSMISSION_ACCESS_FINE);
        }


        setContentView(R.layout.test_page);

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        startScan();

    }

    private void startScan() {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled())
        {Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else {
            bleScanner = bluetoothAdapter.getBluetoothLeScanner();
            if (bleScanner != null) {
                final ScanFilter scanFilter =new ScanFilter.Builder().build();
                ScanSettings settings =new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
                bleScanner.startScan(Arrays.asList(scanFilter), settings, scanCallback);
            }
        }
    }



}
