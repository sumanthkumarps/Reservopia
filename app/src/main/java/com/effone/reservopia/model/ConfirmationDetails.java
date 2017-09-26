package com.effone.reservopia.model;

/**
 * Created by sumanth.peddinti on 9/20/2017.
 */

public class ConfirmationDetails {
    private String LocName;

    private String ScheduledTimeZone;

    private String State;

    private String ServiceName;

    private String Email;

    private String ConfirmationNo;

    private String Address;

    private String ScheduledDateTime;

    private String FirstName;

    private String OrgName;

    private String Zip;

    private String LastName;

    private String City;

    public String getLocName ()
    {
        return LocName;
    }

    public void setLocName (String LocName)
    {
        this.LocName = LocName;
    }

    public String getScheduledTimeZone ()
    {
        return ScheduledTimeZone;
    }

    public void setScheduledTimeZone (String ScheduledTimeZone)
    {
        this.ScheduledTimeZone = ScheduledTimeZone;
    }

    public String getState ()
    {
        return State;
    }

    public void setState (String State)
    {
        this.State = State;
    }

    public String getServiceName ()
    {
        return ServiceName;
    }

    public void setServiceName (String ServiceName)
    {
        this.ServiceName = ServiceName;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getConfirmationNo ()
    {
        return ConfirmationNo;
    }

    public void setConfirmationNo (String ConfirmationNo)
    {
        this.ConfirmationNo = ConfirmationNo;
    }

    public String getAddress ()
    {
        return Address;
    }

    public void setAddress (String Address)
    {
        this.Address = Address;
    }

    public String getScheduledDateTime ()
    {
        return ScheduledDateTime;
    }

    public void setScheduledDateTime (String ScheduledDateTime)
    {
        this.ScheduledDateTime = ScheduledDateTime;
    }

    public String getFirstName ()
    {
        return FirstName;
    }

    public void setFirstName (String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getOrgName ()
    {
        return OrgName;
    }

    public void setOrgName (String OrgName)
    {
        this.OrgName = OrgName;
    }

    public String getZip ()
    {
        return Zip;
    }

    public void setZip (String Zip)
    {
        this.Zip = Zip;
    }

    public String getLastName ()
    {
        return LastName;
    }

    public void setLastName (String LastName)
    {
        this.LastName = LastName;
    }

    public String getCity ()
    {
        return City;
    }

    public void setCity (String City)
    {
        this.City = City;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [LocName = "+LocName+", ScheduledTimeZone = "+ScheduledTimeZone+", State = "+State+", ServiceName = "+ServiceName+", Email = "+Email+", ConfirmationNo = "+ConfirmationNo+", Address = "+Address+", ScheduledDateTime = "+ScheduledDateTime+", FirstName = "+FirstName+", OrgName = "+OrgName+", Zip = "+Zip+", LastName = "+LastName+", City = "+City+"]";
    }
}
