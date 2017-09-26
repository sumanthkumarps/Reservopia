package com.effone.reservopia.model;

import java.io.Serializable;

public class History implements Serializable
{
    private String LocName;

    private String AppointmentDateTime;

    private String UserID;

    private String IsLoggedIn;

    private String UserName;

    private String SendEmailReminder;

    private String AppointmentTypeRefID;

    private String AuditID;

    private String ServiceName;

    private String ConfirmationNo;

    private String StartTime;

    private String CancelledBy;

    private String LocID;

    private String IsAssigned;

    private String AssignedTo;

    private String SendTextReminder;

    private String AppointmentID;

    private String IsCheckedIn;

    private String ScheduledTimeZone;

    private String IsCancelled;

    private String Email;

    private String ServiceID;

    private String OrgID;

    private String AdditionalEmail;

    private String EndTime;

    private String CancelTypeRefID;

    public String getLocName ()
    {
        return LocName;
    }

    public void setLocName (String LocName)
    {
        this.LocName = LocName;
    }

    public String getAppointmentDateTime ()
    {
        return AppointmentDateTime;
    }

    public void setAppointmentDateTime (String AppointmentDateTime)
    {
        this.AppointmentDateTime = AppointmentDateTime;
    }

    public String getUserID ()
    {
        return UserID;
    }

    public void setUserID (String UserID)
    {
        this.UserID = UserID;
    }

    public String getIsLoggedIn ()
    {
        return IsLoggedIn;
    }

    public void setIsLoggedIn (String IsLoggedIn)
    {
        this.IsLoggedIn = IsLoggedIn;
    }

    public String getUserName ()
    {
        return UserName;
    }

    public void setUserName (String UserName)
    {
        this.UserName = UserName;
    }

    public String getSendEmailReminder ()
    {
        return SendEmailReminder;
    }

    public void setSendEmailReminder (String SendEmailReminder)
    {
        this.SendEmailReminder = SendEmailReminder;
    }

    public String getAppointmentTypeRefID ()
    {
        return AppointmentTypeRefID;
    }

    public void setAppointmentTypeRefID (String AppointmentTypeRefID)
    {
        this.AppointmentTypeRefID = AppointmentTypeRefID;
    }

    public String getAuditID ()
    {
        return AuditID;
    }

    public void setAuditID (String AuditID)
    {
        this.AuditID = AuditID;
    }

    public String getServiceName ()
    {
        return ServiceName;
    }

    public void setServiceName (String ServiceName)
    {
        this.ServiceName = ServiceName;
    }

    public String getConfirmationNo ()
    {
        return ConfirmationNo;
    }

    public void setConfirmationNo (String ConfirmationNo)
    {
        this.ConfirmationNo = ConfirmationNo;
    }

    public String getStartTime ()
    {
        return StartTime;
    }

    public void setStartTime (String StartTime)
    {
        this.StartTime = StartTime;
    }

    public String getCancelledBy ()
{
    return CancelledBy;
}

    public void setCancelledBy (String CancelledBy)
    {
        this.CancelledBy = CancelledBy;
    }

    public String getLocID ()
    {
        return LocID;
    }

    public void setLocID (String LocID)
    {
        this.LocID = LocID;
    }

    public String getIsAssigned ()
    {
        return IsAssigned;
    }

    public void setIsAssigned (String IsAssigned)
    {
        this.IsAssigned = IsAssigned;
    }

    public String getAssignedTo ()
{
    return AssignedTo;
}

    public void setAssignedTo (String AssignedTo)
    {
        this.AssignedTo = AssignedTo;
    }

    public String getSendTextReminder ()
    {
        return SendTextReminder;
    }

    public void setSendTextReminder (String SendTextReminder)
    {
        this.SendTextReminder = SendTextReminder;
    }

    public String getAppointmentID ()
    {
        return AppointmentID;
    }

    public void setAppointmentID (String AppointmentID)
    {
        this.AppointmentID = AppointmentID;
    }

    public String getIsCheckedIn ()
    {
        return IsCheckedIn;
    }

    public void setIsCheckedIn (String IsCheckedIn)
    {
        this.IsCheckedIn = IsCheckedIn;
    }

    public String getScheduledTimeZone ()
    {
        return ScheduledTimeZone;
    }

    public void setScheduledTimeZone (String ScheduledTimeZone)
    {
        this.ScheduledTimeZone = ScheduledTimeZone;
    }

    public String getIsCancelled ()
    {
        return IsCancelled;
    }

    public void setIsCancelled (String IsCancelled)
    {
        this.IsCancelled = IsCancelled;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getServiceID ()
    {
        return ServiceID;
    }

    public void setServiceID (String ServiceID)
    {
        this.ServiceID = ServiceID;
    }

    public String getOrgID ()
    {
        return OrgID;
    }

    public void setOrgID (String OrgID)
    {
        this.OrgID = OrgID;
    }

    public String getAdditionalEmail ()
    {
        return AdditionalEmail;
    }

    public void setAdditionalEmail (String AdditionalEmail)
    {
        this.AdditionalEmail = AdditionalEmail;
    }

    public String getEndTime ()
    {
        return EndTime;
    }

    public void setEndTime (String EndTime)
    {
        this.EndTime = EndTime;
    }

    public String getCancelTypeRefID ()
{
    return CancelTypeRefID;
}

    public void setCancelTypeRefID (String CancelTypeRefID)
    {
        this.CancelTypeRefID = CancelTypeRefID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [LocName = "+LocName+", AppointmentDateTime = "+AppointmentDateTime+", UserID = "+UserID+", IsLoggedIn = "+IsLoggedIn+", UserName = "+UserName+", SendEmailReminder = "+SendEmailReminder+", AppointmentTypeRefID = "+AppointmentTypeRefID+", AuditID = "+AuditID+", ServiceName = "+ServiceName+", ConfirmationNo = "+ConfirmationNo+", StartTime = "+StartTime+", CancelledBy = "+CancelledBy+", LocID = "+LocID+", IsAssigned = "+IsAssigned+", AssignedTo = "+AssignedTo+", SendTextReminder = "+SendTextReminder+", AppointmentID = "+AppointmentID+", IsCheckedIn = "+IsCheckedIn+", ScheduledTimeZone = "+ScheduledTimeZone+", IsCancelled = "+IsCancelled+", Email = "+Email+", ServiceID = "+ServiceID+", OrgID = "+OrgID+", AdditionalEmail = "+AdditionalEmail+", EndTime = "+EndTime+", CancelTypeRefID = "+CancelTypeRefID+"]";
    }
}