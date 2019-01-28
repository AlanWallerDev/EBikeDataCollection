package com.alan.waller.e_bikedatacollection;

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

        newSesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, new_session_activity.class);
                startActivity(intent);
            }
        });
    }
}
