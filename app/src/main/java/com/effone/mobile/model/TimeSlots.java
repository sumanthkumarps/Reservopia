package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */

public class TimeSlots {
    private String EndTime;

    private String StartTime;

    public String getEndTime ()
    {
        return EndTime;
    }

    public void setEndTime (String EndTime)
    {
        this.EndTime = EndTime;
    }

    public String getStartTime ()
    {
        return StartTime;
    }

    public void setStartTime (String StartTime)
    {
        this.StartTime = StartTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [EndTime = "+EndTime+", StartTime = "+StartTime+"]";
    }
}
