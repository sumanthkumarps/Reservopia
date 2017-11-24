package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 11/24/2017.
 */

public class TimeSlotsStartEnd {
    private String EndTime;

    private String StartTime;

    private  String DisplayTime;

    public String getDisplayTime() {
        return DisplayTime;
    }

    public void setDisplayTime(String displayTime) {
        DisplayTime = displayTime;
    }

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


}
