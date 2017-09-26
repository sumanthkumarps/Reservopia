package com.effone.reservopia.model;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */

public class Time {
    private ArrayList<TimeSlots> TimeSlots;

    private String AppointmentDate;

    private ArrayList<TimeSlotStrings> TimeSlotStrings;

    private String AppointmentDateString;

    public ArrayList<TimeSlots> getTimeSlots ()
    {
        return TimeSlots;
    }

    public void setTimeSlots (ArrayList<TimeSlots> TimeSlots)
    {
        this.TimeSlots = TimeSlots;
    }

    public String getAppointmentDate ()
    {
        return AppointmentDate;
    }

    public void setAppointmentDate (String AppointmentDate)
    {
        this.AppointmentDate = AppointmentDate;
    }

    public ArrayList<TimeSlotStrings> getTimeSlotStrings ()
    {
        return TimeSlotStrings;
    }

    public void setTimeSlotStrings (ArrayList<TimeSlotStrings> TimeSlotStrings)
    {
        this.TimeSlotStrings = TimeSlotStrings;
    }

    public String getAppointmentDateString ()
    {
        return AppointmentDateString;
    }

    public void setAppointmentDateString (String AppointmentDateString)
    {
        this.AppointmentDateString = AppointmentDateString;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [TimeSlots = "+TimeSlots+", AppointmentDate = "+AppointmentDate+", TimeSlotStrings = "+TimeSlotStrings+", AppointmentDateString = "+AppointmentDateString+"]";
    }
}
