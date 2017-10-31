package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 9/20/2017.
 */

public class Response {
    private String StatusCode;

    private ResponseResult Result;

    private String Message;

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    private String UserType;



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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StatusCode = "+StatusCode+", Result = "+Result+"]";
    }
}
