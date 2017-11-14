package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 11/14/2017.
 */

public class CancelAppointmentBoby {
    private String ConfirmationNo;

    private String IsEndUser;

    public String getConfirmationNo ()
    {
        return ConfirmationNo;
    }

    public void setConfirmationNo (String ConfirmationNo)
    {
        this.ConfirmationNo = ConfirmationNo;
    }

    public String getIsEndUser ()
    {
        return IsEndUser;
    }

    public void setIsEndUser (String IsEndUser)
    {
        this.IsEndUser = IsEndUser;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ConfirmationNo = "+ConfirmationNo+", IsEndUser = "+IsEndUser+"]";
    }
}
