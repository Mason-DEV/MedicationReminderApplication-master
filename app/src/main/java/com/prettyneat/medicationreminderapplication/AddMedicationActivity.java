package com.prettyneat.medicationreminderapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddMedicationActivity extends AppCompatActivity {

    DBHelper db = new DBHelper(this);
    int patientID;
    SharedPreferences prefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);

        // Sets the toolbar inside the activity layout
        Toolbar patientToolbar = (Toolbar) findViewById(R.id.add_medicationToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(patientToolbar);
        //Shows the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handles the back arrow click, sends user back to homepage using intentBack
        patientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText medicationNameInput = (EditText) findViewById(R.id.medicationNameEditText);
                String medicationName = medicationNameInput.getText().toString();

                if (medicationName.matches("")){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddMedicationActivity.this);

                    //set title

                    alertDialogBuilder.setTitle("Cancel");

                    //set dialog message
                    alertDialogBuilder.setMessage("No Medication was entered. Are you sure you want to go back?");

                    //set dialog button choices

                    alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            // User clicked No button

                        }
                    });
                    alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Yes button

                            Intent intentBack = new Intent(AddMedicationActivity.this, MedicationActivity.class);
                            startActivity(intentBack);
                        }
                    });

                    //Create Alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    //show it
                    alertDialog.show();
                }


                else {
                    Intent intentBack = new Intent(AddMedicationActivity.this, MedicationActivity.class);
                    startActivity(intentBack);
                }
            }
        });


        //Set schedule button event, sends user to set schedule page

        Button schBTN = (Button) findViewById(R.id.scheduleBTN);

        schBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText medicationNameInput = (EditText) findViewById(R.id.medicationNameEditText);
                String medicationName = medicationNameInput.getText().toString();
                EditText medicationAmountInput = (EditText) findViewById(R.id.currentEditText);
                int medicationAmount = Integer.parseInt(medicationAmountInput.getText().toString());
                EditText medicationReasonInput = (EditText) findViewById(R.id.reasonNameEditText);
                String medicationReason = medicationReasonInput.getText().toString();
                EditText medicationDoctorInput = (EditText) findViewById(R.id.doctorNameEditText);
                String medicationDoctor = medicationDoctorInput.getText().toString();

                prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
                patientID = Integer.parseInt(prefrences.getString("User", null));
                int medicationPatient = patientID;

                Medication m1 = new Medication(medicationName, medicationAmount, medicationReason, medicationDoctor, medicationPatient);
                if(db.checkMedication(medicationName)) {
                    db.addMedication(m1);
                    int idHolder = db.getMedicationID(medicationName);
                    m1.setMedID(idHolder);
                    prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
                    patientID = Integer.parseInt(prefrences.getString("User", null));
                    SharedPreferences.Editor editor = prefrences.edit();
                    editor.putString("User", Integer.toString(patientID)); //Sets New patients ID as Active
                    editor.commit();
                    System.out.println(patientID);

                }else{
                    int idHolder = db.getMedicationID(medicationName);
                    m1.setMedID(idHolder);
                    prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
                    patientID = prefrences.getInt("User", 0);
                    //db.addCombo(patientID, idHolder);
                    SharedPreferences.Editor editor = prefrences.edit();
                    editor.putString("User", Integer.toString(patientID)); //Sets New patients ID as Active
                    editor.commit();
                }
                prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefrences.edit();
                editor.putInt("MedID", m1.getMedID());
                editor.commit();
                int medIDTEst = prefrences.getInt("MedID", 0);
                //System.out.println("THIS HERE ->" + medIDTEst);
                Intent intentSchedule = new Intent(AddMedicationActivity.this, ScheduleActivity.class);
                startActivity(intentSchedule);

            }
        });

    }

    // Loads created menu_back xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

}
