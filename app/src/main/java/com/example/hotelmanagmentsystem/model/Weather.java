package com.example.hotelmanagmentsystem.model;

public class Weather {
    private Long date;
    private String timetrue;
    private double temp;
    private String icon;


    public Weather(Long date, String timetrue, double temp, String icon) {
        this.date = date;
        this.timetrue = timetrue;
        this.temp = temp;
        this.icon = icon;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTimetrue() {
        return timetrue;
    }

    public void setTimetrue(String timetrue) {
        this.timetrue = timetrue;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
