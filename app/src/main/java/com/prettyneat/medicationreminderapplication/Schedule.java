package com.prettyneat.medicationreminderapplication;


public class Schedule {
    DBHelper db;
    private String interval;
    private String duration;
    private String takeTime;
    private String startDay;
    private int alert;
    private int medNum;//To identify corresponding medicine

    public Schedule(String interval1, String duration1, String takeTime1, String startDay1, int alert1, int medNum1) {
        this.interval = interval1;
        this.duration = duration1;
        this.takeTime = takeTime1;
        this.startDay = " ";
        this.alert = alert1;
        this.medNum = medNum1;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public int getAlert() {
        return alert;
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }

    public int getMedNum() {
        return medNum;
    }

    public void setMedNum(int medNum) {
        this.medNum = medNum;
    }
}
