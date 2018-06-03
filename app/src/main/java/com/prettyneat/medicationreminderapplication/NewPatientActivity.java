package com.prettyneat.medicationreminderapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewPatientActivity extends AppCompatActivity {
    
    EditText nameText;
    DBHelper dbHelper;
    int patientID;
    SharedPreferences prefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpatient);
        nameText = (EditText) findViewById(R.id.nameText);


        dbHelper = new DBHelper(this);
        // Sets the toolbar inside the activity layout
        Toolbar patientToolbar = (Toolbar) findViewById(R.id.patientToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(patientToolbar);
        //Shows the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handles the back arrow click, sends user back to homepage using intentBack
        patientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(NewPatientActivity.this, PatientActivity.class);
                startActivity(intentBack);

            }
        });

        final Button done = findViewById(R.id.addPatientButton);

        //OnClicks for each buttons
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes after user presses button
                if (nameText.getText().toString().trim().length() > 0 ) {
                    Patient c1 = new Patient(nameText.getText().toString());
                    dbHelper.addPatient(c1);
                    patientID = dbHelper.getPatientID(c1.getpatientName());
                    c1.setPatientID(patientID);
                    //messageField.setText("Patient Added");
                    prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefrences.edit();
                    String temp = Integer.toString(c1.getPatientID());
                    System.out.println(temp);
                    editor.putString("User", Integer.toString(c1.getPatientID())); //Sets New patients ID as Active
                    editor.commit();
                    PatientNavMove();
                }
                Intent intentMedication = new Intent(NewPatientActivity.this, PatientActivity.class);
                startActivity(intentMedication);

            }
        });

    }


    public void addPatient(View view){
        if (nameText.getText().toString().trim().length() > 0 ) {
            Patient c1 = new Patient(nameText.getText().toString());
            dbHelper.addPatient(c1);
            patientID = dbHelper.getPatientID(c1.getpatientName());
            c1.setPatientID(patientID);
            //messageField.setText("Patient Added");
            prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefrences.edit();
            String temp = Integer.toString(c1.getPatientID());
            System.out.println(temp);
            editor.putString("User", Integer.toString(c1.getPatientID())); //Sets New patients ID as Active
            editor.commit();
            Intent intentAddNewPatient = new Intent(NewPatientActivity.this, MainActivity.class);
            startActivity(intentAddNewPatient);

        }

    }

    public void getPatient(View view){
        if (nameText.getText().toString().trim().length() > 0 ) {
            Patient patientRecord = dbHelper.getPatient(nameText.getText().toString());
            nameText.setText(patientRecord.getpatientName());
            //messageField.setText("Patient Retrieved");
        }
    }

    public Patient getRecordtoModify(){
        Patient patientRecord = dbHelper.getPatient(nameText.getText().toString());
        return patientRecord;
    }

    public void updatePatient(View view){
        if (nameText.getText().toString().trim().length() > 0 ) {
            Patient patientRecord = getRecordtoModify();

           // messageField.setText("Patient Updated");
        }
    }

    public void deletePatient(View view) {
        if (nameText.getText().toString().trim().length() > 0 ) {
            Patient patientRecord = getRecordtoModify();
            dbHelper.deletePatient(patientRecord);
           // messageField.setText("Patient Deleted");
        }
    }



    public void PatientNavMove(){
        // Moves navigation back to Patient Select
        Intent intentAddNewPatient = new Intent(NewPatientActivity.this, PatientActivity.class);
        startActivity(intentAddNewPatient);

    }


    // Loads created menu_back xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }


}
