package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 9/27/2017.
 */

public class CheckMail {
    private String StatusCode;

    private EmailValid Result;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public EmailValid getResult ()
    {
        return Result;
    }

    public void setResult (EmailValid Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
