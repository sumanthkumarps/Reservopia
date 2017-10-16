package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 10/16/2017.
 */

public class UserDetails {
    private String StatusCode;

    private UserDetailGet Result;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public UserDetailGet getResult ()
    {
        return Result;
    }

    public void setResult (UserDetailGet Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
