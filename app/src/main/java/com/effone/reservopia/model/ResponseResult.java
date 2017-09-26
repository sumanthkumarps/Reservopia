package com.effone.reservopia.model;

/**
 * Created by sarith.vasu on 25-09-2017.
 */

public class ResponseResult {

    private String Operation;

    private String ID;

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

    @Override
    public String toString()
    {
        return "ClassPojo [Operation = "+Operation+", ID = "+ID+"]";
    }

}
