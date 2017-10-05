package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 9/20/2017.
 */

public class Confirmation {
    private String StatusCode;

    private ConfirmationDetails Result;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public ConfirmationDetails getResult ()
    {
        return Result;
    }

    public void setResult (ConfirmationDetails Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
