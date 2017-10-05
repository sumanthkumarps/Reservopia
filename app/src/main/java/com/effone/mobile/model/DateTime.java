package com.effone.mobile.model;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */

public class DateTime {private String StatusCode;

    private ArrayList<Time> Result;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public ArrayList<Time> getResult ()
    {
        return Result;
    }

    public void setResult (ArrayList<Time> Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
