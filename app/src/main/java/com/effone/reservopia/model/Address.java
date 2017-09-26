package com.effone.reservopia.model;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class Address {
    private String AddressID;

    private String LocID;

    private String State;

    private String OrgID;

    private String UserID;

    private String Zip;

    private String Country;

    private String AddressLine1;

    private String City;

    private String AddressLine2;

    private String AuditID;

    private String AddressLine3;

    public String getAddressID ()
    {
        return AddressID;
    }

    public void setAddressID (String AddressID)
    {
        this.AddressID = AddressID;
    }

    public String getLocID ()
    {
        return LocID;
    }

    public void setLocID (String LocID)
    {
        this.LocID = LocID;
    }

    public String getState ()
    {
        return State;
    }

    public void setState (String State)
    {
        this.State = State;
    }

    public String getOrgID ()
    {
        return OrgID;
    }

    public void setOrgID (String OrgID)
    {
        this.OrgID = OrgID;
    }

    public String getUserID ()
    {
        return UserID;
    }

    public void setUserID (String UserID)
    {
        this.UserID = UserID;
    }

    public String getZip ()
    {
        return Zip;
    }

    public void setZip (String Zip)
    {
        this.Zip = Zip;
    }

    public String getCountry ()
    {
        return Country;
    }

    public void setCountry (String Country)
    {
        this.Country = Country;
    }

    public String getAddressLine1 ()
    {
        return AddressLine1;
    }

    public void setAddressLine1 (String AddressLine1)
    {
        this.AddressLine1 = AddressLine1;
    }

    public String getCity ()
    {
        return City;
    }

    public void setCity (String City)
    {
        this.City = City;
    }

    public String getAddressLine2 ()
    {
        return AddressLine2;
    }

    public void setAddressLine2 (String AddressLine2)
    {
        this.AddressLine2 = AddressLine2;
    }

    public String getAuditID ()
    {
        return AuditID;
    }

    public void setAuditID (String AuditID)
    {
        this.AuditID = AuditID;
    }

    public String getAddressLine3 ()
    {
        return AddressLine3;
    }

    public void setAddressLine3 (String AddressLine3)
    {
        this.AddressLine3 = AddressLine3;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [AddressID = "+AddressID+", LocID = "+LocID+", State = "+State+", OrgID = "+OrgID+", UserID = "+UserID+", Zip = "+Zip+", Country = "+Country+", AddressLine1 = "+AddressLine1+", City = "+City+", AddressLine2 = "+AddressLine2+", AuditID = "+AuditID+", AddressLine3 = "+AddressLine3+"]";
    }
}
