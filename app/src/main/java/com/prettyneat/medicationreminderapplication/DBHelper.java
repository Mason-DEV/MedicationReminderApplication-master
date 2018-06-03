package com.prettyneat.medicationreminderapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "patientManager";
    private static final String TABLE_NAME = "patientTable";
    private static final String MED_TABLE_NAME = "medicationTable";

    private static final String MED_ID = "_MedID";
    private static final String MED_NAME = "MedName";
    private static final String MED_QUANTITY = "MedQuant";
    private static final String MED_TAKE = "MedTake";
    private static final String MED_DOC = "MedDoc";
    private static final String MED_PID = "PatientID";

    private static final String SCHED_TABLE = "scheduleTable";
    private static final String SCHED_ID = "scheduleID";
    private static final String SCHED_INTERVAL = "scheduleInterval";
    private static final String SCHED_DURATION = "scheduleDuration";
    private static final String SCHED_SCHEDULE = "scheduleSchedule";
    private static final String SCHED_START = "scheduleStartDay";
    private static final String SCHED_ALERT = "scheduleAlert";
    private static final String SCHED_MED_ID = "scheduleMedicationID";

    private static final String COMBO_TABLE_NAME = "comboTable";

    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";

    private int autoIncrement = 0;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("  //added if not Exists to hopefully stop crash on start
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT);";

        String CREATE_MEDICATION_TABLE = "CREATE TABLE IF NOT EXISTS " + MED_TABLE_NAME + " ("  //added if not Exists to hopefully stop crash on start
                + MED_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY, "
                + MED_NAME + " TEXT, "
                + MED_QUANTITY + " INTEGER, "
                + MED_TAKE + " TEXT, "
                + MED_DOC + " TEXT,"
                + MED_PID + " INTEGER"
                + ");";

        String CREATE_CONNECTING_TABLE = "CREATE TABLE IF NOT EXISTS " + COMBO_TABLE_NAME + " ("  //added if not Exists to hopefully stop crash on start
                + MED_ID + " INTEGER, "
                + KEY_ID + " INTEGER"
                + ");";

        String CREATE_SCHEDULE_TABLE = "CREATE TABLE IF NOT EXISTS " + SCHED_TABLE + " ("  //added if not Exists to hopefully stop crash on start
                + SCHED_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY, "
                + SCHED_INTERVAL + " TEXT, "
                + SCHED_DURATION + " TEXT, "
                + SCHED_SCHEDULE + " TEXT, "
                + SCHED_START + " TEXT, "
                + SCHED_ALERT + " TEXT, "
                + SCHED_MED_ID + " INTEGER"
                + ");";

        System.out.println(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_SCHEDULE_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_MEDICATION_TABLE);
        db.execSQL(CREATE_CONNECTING_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*PATIENT DATABASE ACTIONS*/

    public void addPatient(Patient patient){

        SQLiteDatabase db = this.getWritableDatabase();
        String queryStm = "SELECT COUNT(" + KEY_ID + ") FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(queryStm, null);
        if (c != null) {
            c.moveToFirst();
        }
        if ( c.getInt(0) == 0){
            autoIncrement = 0;
        }else {
            c.moveToLast();
            autoIncrement = c.getInt(0);
            autoIncrement++;
        }
        String insertStm = "INSERT INTO " + TABLE_NAME + " (" + KEY_NAME + ", " + KEY_ID + ") VALUES('" +
                patient.getpatientName() + "', '" + autoIncrement + "');";
        System.out.println(insertStm);
        db.execSQL(insertStm);
        db.close();
    }

    public Patient getPatient(String name){

        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_NAME + " = " + name;
        Cursor c = db.rawQuery(queryStm, null);

        if (c != null) {
            c.moveToFirst();
        }

        //Patient patient = new Patient(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2));
        db.close();
        return  null;
    }

    public int getPatientID(String name){

        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_NAME + " = '" + name + "';";
        Cursor c = db.rawQuery(queryStm, null);
        System.out.println(queryStm);
        if (c != null) {
            c.moveToFirst();
        }
        int patientId = c.getInt(c.getColumnIndex(KEY_ID));

        //Patient patient = new Patient(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2));
        db.close();
        return  patientId;
    }

    public void updatePatientName(Patient patient, String newName){

        /*SQLiteDatabase db = this.getWritableDatabase();
        String updateSTM = "UPDATE " + TABLE_NAME + " SET " + KEY_NAME + " = '" + newName
                + "' WHERE " + KEY_ID + " = " + patient.get_id();

        db.execSQL(updateSTM);
        db.close();*/
    }


    public void deletePatient(Patient patient){

        SQLiteDatabase db = this.getWritableDatabase();
        // String deleteSTM = "DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + patient.get_id();
        // db.execSQL(deleteSTM);
        db.close();
    }

    public String[] getPatientArray(){

        SQLiteDatabase db = this.getReadableDatabase();

        int counter = 0;
        int total;

        String queryStm1 = "SELECT COUNT(" + KEY_NAME + ") FROM " + TABLE_NAME + ";";
        Cursor c1 = db.rawQuery(queryStm1, null);
        c1.moveToFirst();
        total = c1.getInt(0);

        String queryStm = "SELECT " + KEY_NAME + " FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(queryStm, null);
        String patientList[] = new String[total];
        if (c != null) {
            c.moveToFirst();
            System.out.println(total);
            while (total > counter) {
                patientList[counter] = c.getString(0);
                c.moveToNext();
                counter++;
            }
        }
        db.close();
        return patientList;
    }

    /*MEDICATION DATABASE ACTIONS*/

    public void addMedication(Medication medication){

        SQLiteDatabase db = this.getWritableDatabase();

        String queryStm = "SELECT COUNT(" + MED_ID + ") FROM " + MED_TABLE_NAME + ";";
        Cursor c = db.rawQuery(queryStm, null);
        if (c != null) {
            c.moveToFirst();
        }
        if ( c.getInt(0) == 0){
            autoIncrement = 0;
        }else {
            c.moveToLast();
            autoIncrement = c.getInt(0);
            autoIncrement++;
        }

        String insertStm = "INSERT INTO " + MED_TABLE_NAME + "(" + MED_ID + ", "+ MED_NAME + ", " + MED_QUANTITY + ", " + MED_TAKE + ", " + MED_DOC + ", " + MED_PID + ") VALUES ('" +
                autoIncrement + "', '" +
                medication.getMedicationName() + "', '" +
                medication.getMedQuantity() + "', '" +
                medication.gettakeType() + "', '" +
                medication.getMedDoctor() + "', '" +
                medication.getPatientID() + "')";

        db.execSQL(insertStm);
        db.close();
    }

    public Medication getMedication(String name){

        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT * FROM " + MED_TABLE_NAME + " WHERE " + KEY_ID + " = '" + name + "';";
        Cursor c = db.rawQuery(queryStm, null);

        if (c != null) {
            c.moveToFirst();
        }

        Medication medication = new Medication(c.getString(0), Integer.parseInt(c.getString(1)), c.getString(2), c.getString(3), Integer.parseInt(c.getString(4)));
        db.close();
        return  medication;
    }

    public Boolean checkMedication(String name){

        SQLiteDatabase db = this.getReadableDatabase();
        Boolean checker = false;
        String queryStm = "SELECT COUNT(*) FROM " + MED_TABLE_NAME + " WHERE " + MED_NAME + " = '" + name + "';";
        Cursor c = db.rawQuery(queryStm, null);
        if (c != null) {
            c.moveToFirst();
        }
        if ( c.getInt(0) == 0){
            checker = true;
        }

        db.close();
        return  checker;
    }

    public int getMedicationID(String name){

        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT " + MED_ID + " FROM " + MED_TABLE_NAME + " WHERE " + MED_NAME + " = '" + name + "';";
        Cursor c = db.rawQuery(queryStm, null);

        if (c != null) {
            c.moveToFirst();
        }

        int medID = c.getInt(0);
        System.out.println(medID);
        db.close();
        return  medID;
    }

    public void updateMedication(Medication medication, String newName){

        SQLiteDatabase db = this.getWritableDatabase();
        String updateSTM = "UPDATE " + MED_TABLE_NAME + " SET " + MED_NAME + " = '" + newName
                + "' WHERE " + MED_ID + " = '" + medication.getMedID() + "';";

        db.execSQL(updateSTM);
        db.close();
    }


    public void deleteMedication(Medication medication){

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSTM = "DELETE FROM " + MED_TABLE_NAME + " WHERE " + MED_ID + " = '" + medication.getMedID() + "';";
        db.execSQL(deleteSTM);
        db.close();
    }


    public int[] getMedicationArray(int patientID){

        SQLiteDatabase db = this.getReadableDatabase();

        int counter = 0;
        int total;
        int[] medList = new int[10];

            String queryStm1 = "SELECT COUNT(" + MED_ID + ") FROM " + MED_TABLE_NAME + " WHERE " + MED_PID + " = '" + patientID + "';";
            Cursor c1 = db.rawQuery(queryStm1, null);
            c1.moveToFirst();
            total = c1.getInt(0);

            String queryStm = "SELECT " + MED_ID + " FROM " + MED_TABLE_NAME + " WHERE " + MED_PID + " = '" + patientID + "';";
            Cursor c = db.rawQuery(queryStm, null);
            medList = new int[total];
            if (c != null) {
                c.moveToFirst();
                while (total > counter) {
                    medList[counter] = Integer.parseInt(c.getString(0));
                    c.moveToNext();
                    counter++;
                }
            }

        db.close();
        return medList;
    }

    public String[] getCombo(int patientid){

        SQLiteDatabase db = this.getReadableDatabase();

        int counter = 0;
        int total;

        String queryStm1 = "SELECT COUNT(" + MED_ID + ") FROM " + MED_TABLE_NAME + " WHERE " + MED_PID + " = '" + patientid + "';";
        Cursor c1 = db.rawQuery(queryStm1, null);
        c1.moveToFirst();
        total = c1.getInt(0);

        String queryStm = "SELECT " + MED_NAME + " FROM " + MED_TABLE_NAME + " WHERE " + MED_PID + " = '" + patientid + "';";
        Cursor c = db.rawQuery(queryStm, null);
        String medList[] = new String[total];
        if (c != null) {
            c.moveToFirst();
            System.out.println(total);
            while (total > counter) {
                medList[counter] = c.getString(0);
                c.moveToNext();
                counter++;
            }
        }
        db.close();
        return medList;
    }

    public String[] getComboIDS(int patientid){

        SQLiteDatabase db = this.getReadableDatabase();

        int counter = 0;
        int total;

        String queryStm1 = "SELECT COUNT(" + MED_ID + ") FROM " + MED_TABLE_NAME + " WHERE " + MED_PID + " = '" + patientid + "';";
        Cursor c1 = db.rawQuery(queryStm1, null);
        c1.moveToFirst();
        total = c1.getInt(0);

        String queryStm = "SELECT " + MED_ID + " FROM " + MED_TABLE_NAME + " WHERE " + MED_PID + " = '" + patientid + "';";
        Cursor c = db.rawQuery(queryStm, null);
        String medList[] = new String[total];
        if (c != null) {
            c.moveToFirst();
            System.out.println(total);
            while (total > counter) {
                medList[counter] = c.getString(0);
                c.moveToNext();
                counter++;
            }
        }
        db.close();
        return medList;
    }


    public boolean emptyCheck(){ //Used to determine if its a first time user
        boolean check = true;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT Count(*) FROM " + MED_TABLE_NAME + " WHERE " + MED_ID + " = " + MED_ID;
        Cursor c = db.rawQuery(queryStm, null);
        if (c != null) {
            c.moveToFirst();
        }
        if ( c.getInt(0) == 0){
            check = false;
        }

        //Patient patient = new Patient(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2));
        db.close();
        return check;
    }

    /*SCHEDULE DATABASE*/

    public void addSched(Schedule sched){

        SQLiteDatabase db = this.getWritableDatabase();
        String insertStm = "INSERT INTO " + SCHED_TABLE + " (" + SCHED_INTERVAL + ", " + SCHED_DURATION + ", " + SCHED_SCHEDULE + ", " + SCHED_START + ", " + SCHED_ALERT +  ", " + SCHED_MED_ID + ") VALUES ('" +
                sched.getInterval() + "', '" +
                sched.getDuration() + "', '" +
                sched.getTakeTime() + "', '" +
                sched.getStartDay() + "', '" +
                sched.getAlert() + "', '" +
                sched.getMedNum() + "')";

        System.out.println(insertStm);

        db.execSQL(insertStm);
        System.out.println("Confirm");
        db.close();
    }

    public Schedule getSched(int MedID){ //Thinking can get passed by a name to id and then passed for this, best way to find.

        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT * FROM " + SCHED_TABLE + " WHERE " + SCHED_MED_ID + " = '" + MedID + "';";
        Cursor c = db.rawQuery(queryStm, null);

        if (c != null) {
            c.moveToFirst();
        }

        Schedule sched = new Schedule(c.getString(0), c.getString(1), c.getString(2), c.getString(3), Integer.parseInt(c.getString(4)), MedID);
        db.close();
        return  sched;
    }

    public String getSchedArray(int MedID){
        String schedList = "";
        SQLiteDatabase db = this.getReadableDatabase();
        int counter = 0;
            String queryStm1 = "SELECT Count(" + SCHED_SCHEDULE + ") FROM " + SCHED_TABLE + " WHERE " + SCHED_MED_ID + " = '" + MedID + "';";

            Cursor c1 = db.rawQuery(queryStm1, null);
            c1.moveToFirst();
            int total = c1.getInt(0);

            String queryStm = "SELECT " + SCHED_SCHEDULE + " FROM " + SCHED_TABLE + " WHERE " + SCHED_MED_ID + " = '" + MedID + "';";
            Cursor c = db.rawQuery(queryStm, null);
            while (total > counter) {
                if (c != null) {
                    c.moveToFirst();
                    schedList = c.getString(0);
                    c.moveToNext();
                    counter++;
                }
            }
        db.close();
        return schedList;

    }

    public void editSched(Schedule sched, int MedNum){

        SQLiteDatabase db = this.getWritableDatabase();
        String updateSTM = "UPDATE " + SCHED_TABLE + " SET " +
                SCHED_INTERVAL + " = '" + sched.getInterval() + "', " +
                SCHED_DURATION + " = '" + sched.getDuration() + "', " +
                SCHED_SCHEDULE + " = '" + sched.getTakeTime() + "', " +
                SCHED_START + " = '" + sched.getStartDay() + "', " +
                SCHED_ALERT + " = '" + sched.getAlert()
                + "' WHERE " + SCHED_MED_ID + " = '" + MedNum + "';";

        System.out.println(updateSTM);

        db.execSQL(updateSTM);

        String queryStm = "SELECT " + SCHED_SCHEDULE + " FROM " + SCHED_TABLE + " WHERE " + SCHED_MED_ID + " = '" + MedNum + "';";
        Cursor c = db.rawQuery(queryStm, null);

            if (c != null) {
                c.moveToFirst();
                String schedList = c.getString(0);
                System.out.println(schedList);
            }

        db.close();
    }

    public void deleteSched(int MedID){

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSTM = "DELETE FROM " + SCHED_TABLE + " WHERE " + SCHED_MED_ID + " = '" + MedID + "';";
        db.execSQL(deleteSTM);
        db.close();
    }
    public int medTimer(int MedID){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT " + MED_QUANTITY + " FROM " + MED_TABLE_NAME + " WHERE " + MED_ID + " = '" + MedID + "';";
        Cursor c = db.rawQuery(queryStm, null);

        if (c != null) {
            c.moveToFirst();
        }

        int quant = c.getInt(0);
        System.out.println( "THIS THING ->" + quant);
        quant = quant - 1;

        SQLiteDatabase db2 = this.getWritableDatabase();
        String updateStm = "UPDATE " + MED_TABLE_NAME + " SET " + MED_QUANTITY + " = '" + quant + "' WHERE " + MED_ID + " = '" + MedID + "';";
        db2.execSQL(updateStm);
        db2.close();
        db.close();
        return  quant;
    }
    }
