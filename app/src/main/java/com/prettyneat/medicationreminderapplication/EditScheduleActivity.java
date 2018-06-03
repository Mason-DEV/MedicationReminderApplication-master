package com.prettyneat.medicationreminderapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;


public class EditScheduleActivity extends AppCompatActivity {


    DBHelper db = new DBHelper(this);
    int medID;
    SharedPreferences prefrences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_schedule);

        // Sets the toolbar inside the activity layout
        Toolbar patientToolbar = (Toolbar) findViewById(R.id.scheduleToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(patientToolbar);
        //Shows the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handles the back arrow click, sends user back to homepage using intentBack
        patientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intentBack = new Intent(EditScheduleActivity.this, AddMedicationActivity.class);
                 startActivity(intentBack);

            }
        });


        //Code for Start Date - Date Picker

        EditText startET = (EditText) findViewById(R.id.startDate);
        startET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //code for datepicker goes here, toast as placeholder

            }

        });

    }

    public void editSchedule(View view) {
        int switchValue;
        EditText startDateEntry = (EditText) findViewById(R.id.startDate);
        String startDate = startDateEntry.getText().toString();
        Spinner intervalSpin = (Spinner) findViewById(R.id.intervalSpinner);
        String interval = intervalSpin.getSelectedItem().toString();
        Spinner durationSpin = (Spinner) findViewById(R.id.durationSpinner);
        String duration = durationSpin.getSelectedItem().toString();
        Spinner scheduleSpin = (Spinner) findViewById(R.id.scheduleSpinner);
        String schedule = scheduleSpin.getSelectedItem().toString();
        Switch switchEntry = (Switch) findViewById(R.id.alertSwitch);
        prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        medID = prefrences.getInt("MedID", 0);
        boolean switchCheck = switchEntry.isChecked();
        if (switchCheck) {
            switchValue = 1;
        } else {
            switchValue = 0;
        }

        Schedule sched = new Schedule(interval, duration, schedule, startDate, switchValue, medID);
        db.editSched(sched, medID);
        Intent intentSchedToMed = new Intent(EditScheduleActivity.this, MedicationActivity.class);
        startActivity(intentSchedToMed);
    }

        // Loads created menu_back xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

}
