package com.effone.reservopia.model;

import java.io.Serializable;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */

public class AppointmentBookingModel implements Serializable {
    private String CancelledBy;

    private String IsAssigned;

    private String LocID;

    private String IsLoggedIn;

    private String UserID;

    private String AssignedTo;

    private String AppointmentID;

    private String SendTextReminder;

    private String AuditID;

    private String IsCheckedIn;

    private String AppointmentTypeRefID;

    private String SendEmailReminder;

    private String ScheduledTimeZone;

    private String IsCancelled;

    private String OrgID;

    private String ConfirmationNo;

    private String ServiceID;

    private String IsEndUser;

    private String CancelTypeRefID;

    private String EndTime;

    private String AdditionalEmail;

    private String StartTime;

    public String getCancelledBy ()
    {
        return CancelledBy;
    }

    public void setCancelledBy (String CancelledBy)
    {
        this.CancelledBy = CancelledBy;
    }

    public String getIsAssigned ()
    {
        return IsAssigned;
    }

    public void setIsAssigned (String IsAssigned)
    {
        this.IsAssigned = IsAssigned;
    }

    public String getLocID ()
    {
        return LocID;
    }

    public void setLocID (String LocID)
    {
        this.LocID = LocID;
    }

    public String getIsLoggedIn ()
    {
        return IsLoggedIn;
    }

    public void setIsLoggedIn (String IsLoggedIn)
    {
        this.IsLoggedIn = IsLoggedIn;
    }

    public String getUserID ()
    {
        return UserID;
    }

    public void setUserID (String UserID)
    {
        this.UserID = UserID;
    }

    public String getAssignedTo ()
    {
        return AssignedTo;
    }

    public void setAssignedTo (String AssignedTo)
    {
        this.AssignedTo = AssignedTo;
    }

    public String getAppointmentID ()
    {
        return AppointmentID;
    }

    public void setAppointmentID (String AppointmentID)
    {
        this.AppointmentID = AppointmentID;
    }

    public String getSendTextReminder ()
    {
        return SendTextReminder;
    }

    public void setSendTextReminder (String SendTextReminder)
    {
        this.SendTextReminder = SendTextReminder;
    }

    public String getAuditID ()
    {
        return AuditID;
    }

    public void setAuditID (String AuditID)
    {
        this.AuditID = AuditID;
    }

    public String getIsCheckedIn ()
    {
        return IsCheckedIn;
    }

    public void setIsCheckedIn (String IsCheckedIn)
    {
        this.IsCheckedIn = IsCheckedIn;
    }

    public String getAppointmentTypeRefID ()
    {
        return AppointmentTypeRefID;
    }

    public void setAppointmentTypeRefID (String AppointmentTypeRefID)
    {
        this.AppointmentTypeRefID = AppointmentTypeRefID;
    }

    public String getSendEmailReminder ()
    {
        return SendEmailReminder;
    }

    public void setSendEmailReminder (String SendEmailReminder)
    {
        this.SendEmailReminder = SendEmailReminder;
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

    public String getOrgID ()
    {
        return OrgID;
    }

    public void setOrgID (String OrgID)
    {
        this.OrgID = OrgID;
    }

    public String getConfirmationNo ()
    {
        return ConfirmationNo;
    }

    public void setConfirmationNo (String ConfirmationNo)
    {
        this.ConfirmationNo = ConfirmationNo;
    }

    public String getServiceID ()
    {
        return ServiceID;
    }

    public void setServiceID (String ServiceID)
    {
        this.ServiceID = ServiceID;
    }

    public String getIsEndUser ()
    {
        return IsEndUser;
    }

    public void setIsEndUser (String IsEndUser)
    {
        this.IsEndUser = IsEndUser;
    }

    public String getCancelTypeRefID ()
    {
        return CancelTypeRefID;
    }

    public void setCancelTypeRefID (String CancelTypeRefID)
    {
        this.CancelTypeRefID = CancelTypeRefID;
    }

    public String getEndTime ()
    {
        return EndTime;
    }

    public void setEndTime (String EndTime)
    {
        this.EndTime = EndTime;
    }

    public String getAdditionalEmail ()
    {
        return AdditionalEmail;
    }

    public void setAdditionalEmail (String AdditionalEmail)
    {
        this.AdditionalEmail = AdditionalEmail;
    }

    public String getStartTime ()
    {
        return StartTime;
    }

    public void setStartTime (String StartTime)
    {
        this.StartTime = StartTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CancelledBy = "+CancelledBy+", IsAssigned = "+IsAssigned+", LocID = "+LocID+", IsLoggedIn = "+IsLoggedIn+", UserID = "+UserID+", AssignedTo = "+AssignedTo+", AppointmentID = "+AppointmentID+", SendTextReminder = "+SendTextReminder+", AuditID = "+AuditID+", IsCheckedIn = "+IsCheckedIn+", AppointmentTypeRefID = "+AppointmentTypeRefID+", SendEmailReminder = "+SendEmailReminder+", ScheduledTimeZone = "+ScheduledTimeZone+", IsCancelled = "+IsCancelled+", OrgID = "+OrgID+", ConfirmationNo = "+ConfirmationNo+", ServiceID = "+ServiceID+", IsEndUser = "+IsEndUser+", CancelTypeRefID = "+CancelTypeRefID+", EndTime = "+EndTime+", AdditionalEmail = "+AdditionalEmail+", StartTime = "+StartTime+"]";
    }
}
