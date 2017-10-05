package com.effone.mobile.model;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class LocationAndService
{
    private String StatusCode;

    private LocationAndServiceResult Result;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public LocationAndServiceResult getResult ()
    {
        return Result;
    }

    public void setResult (LocationAndServiceResult Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
