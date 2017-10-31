package com.effone.mobile.model;

/**
 * Created by sarith.vasu on 30-10-2017.
 */

public class ChangePassword {
    private int StatusCode;

    public int getStatusCode() { return this.StatusCode; }

    public void setStatusCode(int StatusCode) { this.StatusCode = StatusCode; }

    private String Message;

    public String getMessage() { return this.Message; }

    public void setMessage(String Message) { this.Message = Message; }

    private boolean Result;

    public boolean getResult() { return this.Result; }

    public void setResult(boolean Result) { this.Result = Result; }
}
