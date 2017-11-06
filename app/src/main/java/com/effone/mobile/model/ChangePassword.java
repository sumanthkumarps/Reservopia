package com.effone.mobile.model;

/**
 * Created by sarith.vasu on 30-10-2017.
 */

public class  ChangePassword {
    private String StatusCode;

    private String Result;

    private String Message;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public String getResult ()
    {
        return Result;
    }

    public void setResult (String Result)
    {
        this.Result = Result;
    }

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+", Message = "+Message+"]";
    }
}
