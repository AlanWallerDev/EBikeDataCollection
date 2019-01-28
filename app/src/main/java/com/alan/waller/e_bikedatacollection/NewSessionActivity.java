package com.alan.waller.e_bikedatacollection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.*;

public class NewSessionActivity extends AppCompatActivity {
    final String TAG = "NEW SESSION ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_session_activity);
        Button startButton = (Button) findViewById(R.id.startButton);
        final EditText nameText = (EditText) findViewById(R.id.nameInputView);
        final EditText ageText = (EditText) findViewById(R.id.ageInputView);
        final EditText weightText = (EditText) findViewById(R.id.weightInput);
        EditText heightText = (EditText) findViewById(R.id.heightInputView);
        final Spinner sexSpinner = (Spinner) findViewById(R.id.sexSpinner);
        //populate the sex spinner with the sex_array string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSessionActivity.this, SessionActivity.class);
                intent.putExtra("SUBJECT_NAME", nameText.getText().toString());
                intent.putExtra("SUBJECT_AGE", ageText.getText().toString());
                intent.putExtra("SUBJECT_WEIGHT", weightText.getText().toString());
                intent.putExtra("SUBJECT_SEX", sexSpinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }
}
