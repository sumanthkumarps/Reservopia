package com.effone.mobile.model;

import java.util.ArrayList;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class LocationAndServiceResult {
    private ArrayList<Services> Services;

    private ArrayList<Locations> Locations;

    private ArrayList<LocationsXServices> LocationsXServices;

    public ArrayList<Services> getServices ()
    {
        return Services;
    }

    public void setServices (ArrayList<Services> Services)
    {
        this.Services = Services;
    }

    public ArrayList<Locations> getLocations ()
    {
        return Locations;
    }

    public void setLocations (ArrayList<Locations> Locations)
    {
        this.Locations = Locations;
    }

    public ArrayList<LocationsXServices> getLocationsXServices ()
    {
        return LocationsXServices;
    }

    public void setLocationsXServices (ArrayList<LocationsXServices> LocationsXServices)
    {
        this.LocationsXServices = LocationsXServices;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Services = "+Services+", Locations = "+Locations+", LocationsXServices = "+LocationsXServices+"]";
    }
}
