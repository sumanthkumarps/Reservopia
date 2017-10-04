package com.effone.reservopia.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by sumanth.peddinti on 9/27/2017.
 */

public class TimeZoneDetails extends RealmObject implements Serializable{
    private String StandardName;

    private String DisplayShortName;

    private String Id;

    private String DisplayName;

    public String getStandardName ()
    {
        return StandardName;
    }

    public void setStandardName (String StandardName)
    {
        this.StandardName = StandardName;
    }

    public String getDisplayShortName ()
    {
        return DisplayShortName;
    }

    public void setDisplayShortName (String DisplayShortName)
    {
        this.DisplayShortName = DisplayShortName;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getDisplayName ()
    {
        return DisplayName;
    }

    public void setDisplayName (String DisplayName)
    {
        this.DisplayName = DisplayName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StandardName = "+StandardName+", DisplayShortName = "+DisplayShortName+", Id = "+Id+", DisplayName = "+DisplayName+"]";
    }
}
