package com.effone.reservopia.model;

import java.util.ArrayList;

import javax.crypto.AEADBadTagException;

/**
 * Created by sumanth.peddinti on 9/27/2017.
 */

public class Title {
    private String StatusCode;

    private ArrayList<TitleNames> Result;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public ArrayList<TitleNames> getResult ()
    {
        return Result;
    }

    public void setResult ( ArrayList<TitleNames> Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
