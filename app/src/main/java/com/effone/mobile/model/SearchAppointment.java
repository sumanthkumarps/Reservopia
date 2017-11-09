package com.effone.mobile.model;

import java.util.ArrayList;

/**
 * Created by sarith.vasu on 09-11-2017.
 */

public class SearchAppointment {
    private String StatusCode;

    private ArrayList<History> Result;

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public ArrayList<History> getResult() {
        return Result;
    }

    public void setResult(ArrayList<History> result) {
        Result = result;
    }
}
