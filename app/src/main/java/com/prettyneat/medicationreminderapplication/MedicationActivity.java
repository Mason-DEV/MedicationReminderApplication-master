package com.prettyneat.medicationreminderapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MedicationActivity extends AppCompatActivity {

    SharedPreferences prefrences;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medications);

        // Sets the toolbar inside the activity layout
        Toolbar medicationToolbar = (Toolbar) findViewById(R.id.medicationToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(medicationToolbar);
        //Shows the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Handles the back arrow click, sends user back to homepage using intentBack
        medicationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(MedicationActivity.this, MainActivity.class);
                startActivity(intentBack);

            }
        });

        final Button editBTN = findViewById(R.id.editBTN);
        editBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes after user presses button
                Intent intentMedication = new Intent(MedicationActivity.this, EditMedicationActivity.class);
                startActivity(intentMedication);

            }
        });


        prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        int patientID = Integer.parseInt(prefrences.getString("User", null));
        String[] medNames;
        if (db.emptyCheck()) {
            medNames = db.getCombo(patientID);
            for(int i = 0; i < medNames.length; i++){
                System.out.println(medNames[i]);
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.array_adapter, medNames);

            ListView listView = (ListView) findViewById(R.id.medsListView);
            listView.setAdapter(adapter);
        }
    }



    // Loads created menu_back xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plus, menu);
        return true;
    }
    // Handles click events from menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_plus:
                // User choose the "Settings" item, show the app settings UI, using intentSettings
                Intent intentPlus = new Intent(MedicationActivity.this, AddMedicationActivity.class);
                startActivity(intentPlus);
                return true;



            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

