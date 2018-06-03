package com.prettyneat.medicationreminderapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EditMedicationActivity extends AppCompatActivity {

    DBHelper db = new DBHelper(this);
    SharedPreferences prefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medication);

        // Sets the toolbar inside the activity layout
        Toolbar editToolbar = (Toolbar) findViewById(R.id.editToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(editToolbar);
        //Shows the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handles the back arrow click, sends user back to medication page using intentBack
        editToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(EditMedicationActivity.this, MedicationActivity.class);
                startActivity(intentBack);

            }
        });

    }

    // Loads created menu_back xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trash, menu);
        return true;
    }

    // Handles click events from menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_trash:
                // User choose the Trash Icon, Shows alert about deleting medication

                // Builds Alert
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditMedicationActivity.this);
                //set title
                alertDialogBuilder.setTitle("Delete Medication");
                //set dialog message
                alertDialogBuilder.setMessage("Are you sure you want to delete?");
                //set dialog button choices
                alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No button, does nothing


                    }
                });
                alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button

                        //code for deleting medicine goes here. Toast as place holder
                        Toast.makeText(EditMedicationActivity.this,
                                "Your Medicine was deleted", Toast.LENGTH_LONG).show();

                    }
                });

                //Create Alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                //show it
                alertDialog.show();

        default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void EditMedicationButton(View view){

        EditText medicationNameInput = (EditText) findViewById(R.id.editmedicationNameEditText);
        String medicationName = medicationNameInput.getText().toString();
        EditText medicationAmountInput = (EditText) findViewById(R.id.editcurrentEditText);
        int medicationAmount = Integer.parseInt(medicationAmountInput.getText().toString());
        EditText medicationReasonInput = (EditText) findViewById(R.id.editreasonNameEditText);
        String medicationReason = medicationReasonInput.getText().toString();
        EditText medicationDoctorInput = (EditText) findViewById(R.id.editdoctorNameEditText);
        String medicationDoctor = medicationDoctorInput.getText().toString();

        prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        int patientID = Integer.parseInt(prefrences.getString("User", null));
        int medicationPatient = patientID;

        Medication m1 = new Medication(medicationName, medicationAmount, medicationReason, medicationDoctor, medicationPatient);
        if(db.checkMedication(medicationName)) {
            db.updateMedication(m1, medicationName);
        }
        prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefrences.edit();
        editor.putInt("MedID", m1.getMedID());
        editor.commit();
        //int medIDTEst = prefrences.getInt("MedID", 0);
        //System.out.println("THIS HERE ->" + medIDTEst);
        Intent intentSchedule = new Intent(EditMedicationActivity.this, EditScheduleActivity.class);
        startActivity(intentSchedule);

    }

}