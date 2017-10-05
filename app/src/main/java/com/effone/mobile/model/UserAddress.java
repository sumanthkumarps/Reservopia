package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 10/4/2017.
 */

public class UserAddress {
    private String State;

    private String Zip;

    private String Country;

    private String AddressLine1;

    private String City;

    private String AddressLine2;

    private String AddressLine3;

    public String getState ()
    {
        return State;
    }

    public void setState (String State)
    {
        this.State = State;
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
        return "ClassPojo [State = "+State+", Zip = "+Zip+", Country = "+Country+", AddressLine1 = "+AddressLine1+", City = "+City+", AddressLine2 = "+AddressLine2+", AddressLine3 = "+AddressLine3+"]";
    }
}
