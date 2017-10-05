
package com.effone.mobile.model;

import java.io.Serializable;

public class Result implements Serializable
{
    private History[] Upcoming;

    private History[] History;

    public History[] getUpcoming ()
    {
        return Upcoming;
    }

    public void setUpcoming (History[] Upcoming)
    {
        this.Upcoming = Upcoming;
    }

    public History[] getHistory ()
    {
        return History;
    }

    public void setHistory (History[] History)
    {
        this.History = History;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Upcoming = "+Upcoming+", History = "+History+"]";
    }
}

