package com.prettyneat.medicationreminderapplication;




public class Medication {
    DBHelper db;
    private String medicationName;
    private int medQuantity;
    private String takeType;
    private String medDoctor;
    private int medID;
    private int patientID;
    //private object medication(patientName, type, quantity, how to take etc)
    //other variables related to the patient

    //Constructor
    public Medication(String medication, int medicationQuant, String howToTake, String prescribedDoctor, int patientID1) {
        this.medicationName = medication;
        this.medQuantity = medicationQuant;
        this.takeType = howToTake;
        this.medDoctor = prescribedDoctor;
        this.patientID = patientID1;
    }

    public void setMedicationName(String medicationName1) {
        this.medicationName = medicationName1;

    }

    public String getMedicationName() {
        return medicationName;

    }

    public void setMedQuantity(int medQuantity1) {
        this.medQuantity = medQuantity1;

    }

    public int getMedQuantity() {
        return medQuantity;

    }

    public void settakeType(String howToTake) {
        this.takeType = howToTake;

    }

    public String gettakeType() {
        return takeType;

    }

    public void setTakeType(String takeType) {

        this.takeType = takeType;
    }

    public void setMedDoctor(String prescribedDoctor) {
        this.medDoctor = prescribedDoctor;

    }

    public String getMedDoctor() {
        return medDoctor;

    }

    public void setMedID(int medID) { //Usure if needed
        this.medID = medID;

    }

    public int getMedID() {
        return medID;

    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

}
