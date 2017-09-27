package com.effone.reservopia.model;


import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 9/27/2017.
 */

public class GetTimeZones {
    private String StatusCode;

    private ArrayList<TimeZoneDetails> Result;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public ArrayList<TimeZoneDetails> getResult ()
    {
        return Result;
    }

    public void setResult (ArrayList<TimeZoneDetails> Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
