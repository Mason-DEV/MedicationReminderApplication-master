package com.prettyneat.medicationreminderapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.widget.Toast;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;

    //Declare TextViews for Month/Date/Day
    private TextView monthTextView;
    private TextView dateTextView;
    private TextView dayTextView;
    DBHelper db = new DBHelper(this);
    int patientID;
    int[] medsids;
    SharedPreferences prefrences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        // Sets the toolbar inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);


        // Declares buttons
        final ImageButton medication = findViewById(R.id.medicationButton);
        //final Button due = findViewById(R.id.dueButton);
        final ImageButton log = findViewById(R.id.logButton);
        //final Button notes = findViewById(R.id.notesButton);

        //TextView connections for layout for Month/Day/Date
        monthTextView = (TextView) findViewById(R.id.monthTextView);
        dayTextView = (TextView) findViewById(R.id.dayTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);

        //Calendar instance to get current time/date, using SimpleDateFormat
        Calendar calendar = Calendar.getInstance();
        monthTextView.setText(new SimpleDateFormat("MMM").format(calendar.getTime()));
        dayTextView.setText(new SimpleDateFormat("EEE").format(calendar.getTime()));
        dateTextView.setText(new SimpleDateFormat("d").format(calendar.getTime()));

        //List View Setup
        /* NEED Working Database and Patient Field First*/
        prefrences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        if (db.emptyCheck()) {
            patientID = Integer.parseInt(prefrences.getString("User", null));
            String medNames[] = db.getCombo(patientID);
            medsids = db.getMedicationArray(patientID);
            //String[] medSchedule = );
            System.out.println(medNames.length + " " + medsids.length);
            String[] comboSched = new String[medNames.length];
            String comboString;
            System.out.println("THIS HERE ->" + medNames.length + " " + medsids.length);
            for (int x = 0; x < medNames.length; x++){
                comboString = medNames[x] + " " + db.getSchedArray(medsids[x]);
                comboSched[x] = comboString;
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.array_adapter, comboSched);

            ListView listView = (ListView) findViewById(R.id.medsListView);
            listView.setAdapter(adapter);
        }
            //System.out.println("HELLO THIS THINGS");
        if (db.emptyCheck()) {
            patientID = Integer.parseInt(prefrences.getString("User", null));
            String medNames[] = db.getCombo(patientID);
            medsids = db.getMedicationArray(patientID);
            //String[] medSchedule = );

            for (int x = 0; x < medNames.length; x++){
                String Sched = db.getSchedArray(medsids[x]);
                if (Sched == "Morning"){
                    //PUT ALERTS HERE
                    startAt8AM();
                    medRunningLow(medsids[x]);

                }else if(Sched == "Noon"){
                    //PUT ALERTS HERE
                    startAt12();
                    medRunningLow(medsids[x]);
                }else if(Sched == "Evening"){
                    //PUT ALERTS HERE
                    startAt8PM();
                    medRunningLow(medsids[x]);
                }

        }}

        //OnClicks for each buttons
        medication.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes after user presses button
                Intent intentMedication = new Intent(MainActivity.this, MedicationActivity.class);
                startActivity(intentMedication);

            }
        });


        /*due.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes after user presses button
                Intent intentDue = new Intent(MainActivity.this, AgendaActivity.class);
                startActivity(intentDue);

            }
        });
*/
        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes after user presses button
                Intent intentLog = new Intent(MainActivity.this, LogActivity.class);
                startActivity(intentLog);

            }
        });

        /*
        notes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes after user presses button
                Intent intentNotes = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intentNotes);

            }
        });

*/

    }
    public void medRunningLow( int MedID){
        int medChecker = db.medTimer(MedID);
        if (medChecker < 7){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            //set title

            alertDialogBuilder.setTitle("Medication running out!");

            //set dialog message
            alertDialogBuilder.setMessage("Your medication is getting low, get your script refilled");

            //set dialog button choices

            alertDialogBuilder.setPositiveButton("Snooze", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    // User clicked No button


                }
            });
            alertDialogBuilder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked Yes button

                    Intent intentBack = new Intent(MainActivity.this, MedicationActivity.class);
                    startActivity(intentBack);
                }
            });

            //Create Alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            //show it
            alertDialog.show();
        }
    }


    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    // Code for 8AM Code

    public void startAt8AM() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 8:00 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }

    // Code for Noon Code

    public void startAt12() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 12:00  PM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }
    // Code for 8PM Code
    public void startAt8PM() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 12:00  PM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 30);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }

    // Loads created menu xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);//Menu Resource, Menu
        return true;
    }



    // Handles click events from menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User choose the "Settings" item, show the app settings UI, using intentSettings
                Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;

            case R.id.action_patientselect:
                // User chose the "PatientSelect", shows the PatientSelect UI, using intentPatient
                Intent intentPatient = new Intent(MainActivity.this, PatientActivity.class);
                startActivity(intentPatient);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
