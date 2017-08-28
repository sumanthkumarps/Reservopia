package com.effone.reservopia.model;

/**
 * Created by sumanth.peddinti on 8/24/2017.
 */

public class AppointmentDataTime {
    private String time;

    private String location;

    private String serviceTypeId;

    private String locationId;

    private String serviceType;

    private String date;

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getServiceTypeId ()
    {
        return serviceTypeId;
    }

    public void setServiceTypeId (String serviceTypeId)
    {
        this.serviceTypeId = serviceTypeId;
    }

    public String getLocationId ()
    {
        return locationId;
    }

    public void setLocationId (String locationId)
    {
        this.locationId = locationId;
    }

    public String getServiceType ()
    {
        return serviceType;
    }

    public void setServiceType (String serviceType)
    {
        this.serviceType = serviceType;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [time = "+time+", location = "+location+", serviceTypeId = "+serviceTypeId+", locationId = "+locationId+", serviceType = "+serviceType+", date = "+date+"]";
    }
}
