package com.prettyneat.medicationreminderapplication;

public class Patient {


    private String patientName;
    private int[] medID = new int[10];
    private String[] medNames = new String[10];
    private int patientID;
    //private List<Medication> medicationList = new ArrayList<Medication>();
    //private object medication(patientName, type, quantity, how to take etc)
    //other variables related to the patient


    //Constructor
    public Patient(String patientName ) {

        this.patientName = patientName;
    }

    public void setpatientName(String patientName1) {
        this.patientName = patientName1;

    }

    public String getpatientName() {
        return patientName;
    }


    public void setPatientID(int patientIDS) {
        this.patientID = patientIDS;

    }

    public int getPatientID() {
        return patientID;

    }

    public void setPatientMedsID(int[] MedIDS){
        this.medID = MedIDS;
    }

    public int[] getpatientsMedsID(){
        return medID;
    }

    public void setpatientMedsName(String[] MedNames1){
        medNames = MedNames1;
    }

    public String[] getpatientsMedsName(){
        return medNames;
    }

}
