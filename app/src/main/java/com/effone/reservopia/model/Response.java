package com.effone.reservopia.model;

/**
 * Created by sumanth.peddinti on 9/20/2017.
 */

public class Response {
    private String StatusCode;

    private ResponseResult Result;

    public String getStatusCode ()
    {
        return StatusCode;
    }

    public void setStatusCode (String StatusCode)
    {
        this.StatusCode = StatusCode;
    }

    public ResponseResult getResult ()
    {
        return Result;
    }

    public void setResult (ResponseResult Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
