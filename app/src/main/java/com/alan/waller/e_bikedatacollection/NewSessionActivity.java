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
    static RadioButton radio3;
    static RadioButton radio5;
    static RadioButton radio10;
    static RadioButton radio15;
    static RadioButton radio30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_session_activity);
        Button startButton = (Button) findViewById(R.id.startButton);
        final EditText nameText = (EditText) findViewById(R.id.nameInputView);
        final EditText ageText = (EditText) findViewById(R.id.ageInputView);
        final EditText weightText = (EditText) findViewById(R.id.weightInput);
        final EditText heightText = (EditText) findViewById(R.id.heightInputView);
        final Spinner sexSpinner = (Spinner) findViewById(R.id.sexSpinner);
        final RadioGroup refreshGroup = (RadioGroup) findViewById(R.id.refreshRateGroup);
        int checked = refreshGroup.getCheckedRadioButtonId();
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio5 = (RadioButton) findViewById(R.id.radio5);
        radio10 = (RadioButton) findViewById(R.id.radio10);
        radio15 = (RadioButton) findViewById(R.id.radio15);
        radio30 = (RadioButton) findViewById(R.id.radio30);
        final int selectedRate = selected(checked);

        //populate the sex spinner with the sex_array string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSessionActivity.this, SessionActivity.class);
                if(sexSpinner.getSelectedItem() != null && !(nameText.getText().toString().equals(""))&& !(ageText.getText().toString().equals(""))&& !(weightText.getText().toString().equals(""))&& !(heightText.getText().toString().equals(""))) {
                    intent.putExtra("SUBJECT_NAME", nameText.getText().toString());
                    intent.putExtra("SUBJECT_AGE", ageText.getText().toString());
                    intent.putExtra("SUBJECT_WEIGHT", weightText.getText().toString());
                    intent.putExtra("SUBJECT_HEIGHT", heightText.getText().toString());
                    intent.putExtra("SUBJECT_SEX", sexSpinner.getSelectedItem().toString());
                    intent.putExtra("REFRESH_RATE", selectedRate);
                    startActivity(intent);
                }else{
                    Toast.makeText(NewSessionActivity.this, "All Fields Must Be Filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int selected(int checked){

        if(checked == radio3.getId()){
            return 3;
        }else if(checked == radio5.getId()){
            return 5;
        }else if(checked == radio10.getId()){
            return 10;
        }else if(checked == radio15.getId()){
            return 15;
        }else if(checked == radio30.getId()){
            return 30;
        }else{
            return 30;
        }
    }
}
