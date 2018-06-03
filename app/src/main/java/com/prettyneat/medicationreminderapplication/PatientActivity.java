package com.prettyneat.medicationreminderapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class PatientActivity extends AppCompatActivity {

    int patientID;
    SharedPreferences prefrences;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patients);

        // Sets the toolbar inside the activity layout
        Toolbar patientToolbar = (Toolbar) findViewById(R.id.patientToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(patientToolbar);
        //Shows the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        String[] patientNames;
        if (db.emptyCheck()) {
            patientNames = db.getPatientArray();
            for(int i = 0; i < patientNames.length; i++){
                System.out.println(patientNames[i]);
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.array_adapter, patientNames);

            ListView listView = (ListView) findViewById(R.id.patientsListView);
            listView.setAdapter(adapter);
        }


        // Handles the back arrow click, sends user back to homepage using intentBack
        patientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(PatientActivity.this, MainActivity.class);
                startActivity(intentBack);

            }
        });

    }
    public void addNewPatient(View view){
        // User choose the "Patient" item, show the app settings UI, using intentSettings
        Intent intentAddNewPatient = new Intent(PatientActivity.this, NewPatientActivity.class);
        startActivity(intentAddNewPatient);
    }


    public void changePatient(View view){
        EditText patientToChangeTo = (EditText) findViewById(R.id.editText);
        String patientName = patientToChangeTo.getText().toString();
        if (db.emptyCheck()) {
            int patientid = db.getPatientID(patientName);
            prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefrences.edit();
            editor.putString("User", Integer.toString(patientid));
            editor.commit();
            Intent intentAddNewPatient = new Intent(PatientActivity.this, MainActivity.class);
            startActivity(intentAddNewPatient);
        }
    }


    // Loads created menu_back xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }




}
