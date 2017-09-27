package com.effone.reservopia.model;

import io.realm.RealmObject;

/**
 * Created by sumanth.peddinti on 9/27/2017.
 */

public class TitleNames  extends RealmObject {
    private String Text;

    private String Value;

    public String getText ()
    {
        return Text;
    }

    public void setText (String Text)
    {
        this.Text = Text;
    }

    public String getValue ()
    {
        return Value;
    }

    public void setValue (String Value)
    {
        this.Value = Value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Text = "+Text+", Value = "+Value+"]";
    }
}