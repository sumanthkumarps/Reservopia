package com.effone.mobile.model;

/**
 * Created by sarith.vasu on 25-09-2017.
 */

public class ResponseResult {

    private String Operation;

    private String ID;

    private String UserType;



    public String getOperation ()
    {
        return Operation;
    }

    public void setOperation (String Operation)
    {
        this.Operation = Operation;
    }

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
    }
    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Operation = "+Operation+", ID = "+ID+"]";
    }

}
