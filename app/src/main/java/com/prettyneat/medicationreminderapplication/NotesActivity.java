package com.prettyneat.medicationreminderapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;


public class NotesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);

        // Sets the toolbar inside the activity layout
        Toolbar patientToolbar = (Toolbar) findViewById(R.id.notesToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(patientToolbar);
        //Shows the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handles the back arrow click, sends user back to homepage using intentBack
        patientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(NotesActivity.this, MainActivity.class);
                startActivity(intentBack);

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
