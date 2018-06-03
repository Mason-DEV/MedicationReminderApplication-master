package com.prettyneat.medicationreminderapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;


public class AgendaActivity extends AppCompatActivity {

    CalendarView agendaCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda);

        // Sets the toolbar inside the activity layout
        Toolbar patientToolbar = (Toolbar) findViewById(R.id.agendaToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(patientToolbar);
        //Shows the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handles the back arrow click, sends user back to homepage using intentBack
        patientToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(AgendaActivity.this, MainActivity.class);
                startActivity(intentBack);

            }
        });

        // Calendar Code
        agendaCalendarView = (CalendarView) findViewById(R.id.agendaCalendar);
        agendaCalendarView.setShowWeekNumber(false);
        agendaCalendarView.setBackgroundColor(Color.TRANSPARENT);
        agendaCalendarView.setSelectedWeekBackgroundColor(Color.TRANSPARENT);
        agendaCalendarView.setWeekSeparatorLineColor(Color.GRAY);
        agendaCalendarView.setFocusedMonthDateColor(getResources().getColor(R.color.colorPolyPurple));
        agendaCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view,
                                            int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(),
                +month + "/"+ dayOfMonth +"/" +year,Toast.LENGTH_LONG).show();}});
    }

    // Loads created menu_back xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

}
