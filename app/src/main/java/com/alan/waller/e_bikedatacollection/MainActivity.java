package com.alan.waller.e_bikedatacollection;

import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newSesButton = (Button) findViewById(R.id.newSessionButton);
        Button oldSesButton = (Button) findViewById(R.id.oldSessionButton);
        Button signOutButton = (Button) findViewById(R.id.signOutButton);
        Button pairDevice = (Button) findViewById(R.id.pairButton);
        //test button
        Button testPage = (Button) findViewById(R.id.testPageButton);
        //mapTest Button
        Button mapTest = (Button) findViewById(R.id.mapTest);

        newSesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewSessionActivity.class);
                startActivity(intent);
            }
        });

        oldSesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OldSessionActivity.class);
                startActivity(intent);
            }
        });

        pairDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, NewDevicePair.class);
                startActivity(intent2);
            }
        });

        //button for test purposes only
        testPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeviceScanActivity.class);
                startActivity(intent);
            }
        });

        //button for test purposes only
        mapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GPSTest.class);
                startActivity(intent);
            }
        });
    }
}
