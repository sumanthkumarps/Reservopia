package com.effone.reservopia.model;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class WorkHours {
    private String LocID;

    private String HoursRange;

    private String BreakEndHour;

    private String WorkStartHour;

    private String Weekday;

    private String IsClosedForLunch;

    private String WorkEndHour;

    private String LocXWorkHourID;

    private String BreakStartHour;

    private String AuditID;

    private String IsWorkingDay;

    public String getLocID ()
    {
        return LocID;
    }

    public void setLocID (String LocID)
    {
        this.LocID = LocID;
    }

    public String getHoursRange ()
    {
        return HoursRange;
    }

    public void setHoursRange (String HoursRange)
    {
        this.HoursRange = HoursRange;
    }

    public String getBreakEndHour ()
    {
        return BreakEndHour;
    }

    public void setBreakEndHour (String BreakEndHour)
    {
        this.BreakEndHour = BreakEndHour;
    }

    public String getWorkStartHour ()
    {
        return WorkStartHour;
    }

    public void setWorkStartHour (String WorkStartHour)
    {
        this.WorkStartHour = WorkStartHour;
    }

    public String getWeekday ()
    {
        return Weekday;
    }

    public void setWeekday (String Weekday)
    {
        this.Weekday = Weekday;
    }

    public String getIsClosedForLunch ()
    {
        return IsClosedForLunch;
    }

    public void setIsClosedForLunch (String IsClosedForLunch)
    {
        this.IsClosedForLunch = IsClosedForLunch;
    }

    public String getWorkEndHour ()
    {
        return WorkEndHour;
    }

    public void setWorkEndHour (String WorkEndHour)
    {
        this.WorkEndHour = WorkEndHour;
    }

    public String getLocXWorkHourID ()
    {
        return LocXWorkHourID;
    }

    public void setLocXWorkHourID (String LocXWorkHourID)
    {
        this.LocXWorkHourID = LocXWorkHourID;
    }

    public String getBreakStartHour ()
    {
        return BreakStartHour;
    }

    public void setBreakStartHour (String BreakStartHour)
    {
        this.BreakStartHour = BreakStartHour;
    }

    public String getAuditID ()
    {
        return AuditID;
    }

    public void setAuditID (String AuditID)
    {
        this.AuditID = AuditID;
    }

    public String getIsWorkingDay ()
    {
        return IsWorkingDay;
    }

    public void setIsWorkingDay (String IsWorkingDay)
    {
        this.IsWorkingDay = IsWorkingDay;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [LocID = "+LocID+", HoursRange = "+HoursRange+", BreakEndHour = "+BreakEndHour+", WorkStartHour = "+WorkStartHour+", Weekday = "+Weekday+", IsClosedForLunch = "+IsClosedForLunch+", WorkEndHour = "+WorkEndHour+", LocXWorkHourID = "+LocXWorkHourID+", BreakStartHour = "+BreakStartHour+", AuditID = "+AuditID+", IsWorkingDay = "+IsWorkingDay+"]";
    }
}
