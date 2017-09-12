package com.effone.reservopia.model;

/**
 * Created by sarith.vasu on 29-08-2017.
 */

public class HistoryAppointment {
    private String appointment_id;
    private String name;
    private String date;
    private String location;
    private String serivice;

    public String getSerivice() {
        return serivice;
    }

    public void setSerivice(String serivice) {
        this.serivice = serivice;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



}
