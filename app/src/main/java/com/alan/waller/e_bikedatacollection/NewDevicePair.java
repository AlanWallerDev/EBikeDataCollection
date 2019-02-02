package com.alan.waller.e_bikedatacollection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NewDevicePair extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pair_devices);

        Button pairWatch = (Button) findViewById(R.id.pairWatchButton);

        pairWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewDevicePair.this, PairWatch.class);
                startActivity(intent);
            }
        });

    }
}
