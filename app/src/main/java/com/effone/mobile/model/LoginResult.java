package com.effone.mobile.model;

/**
 * Created by sarith.vasu on 12-10-2017.
 */

public class LoginResult {
    private Integer StatusCode;
    private String Message;
    private Boolean Result;


    public Integer getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public Boolean getResult() {
        return Result;
    }

    public void setResult(Boolean result) {
        this.Result = result;
    }




}
