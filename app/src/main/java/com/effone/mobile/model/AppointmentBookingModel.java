package com.effone.mobile.model;

import java.io.Serializable;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */

public class AppointmentBookingModel implements Serializable {
    private int AppointmentID;

    public int getAppointmentID() { return this.AppointmentID; }

    public void setAppointmentID(int AppointmentID) { this.AppointmentID = AppointmentID; }

    private String LocID;

    public String getLocID() { return this.LocID; }

    public void setLocID(String LocID) { this.LocID = LocID; }

    private String ProviderID;

    public String getProviderID() { return this.ProviderID; }

    public void setProviderID(String ProviderID) { this.ProviderID = ProviderID; }

    private String UserID;

    public String getUserID() { return this.UserID; }

    public void setUserID(String UserID) { this.UserID = UserID; }

    private String Title;

    public String getTitle() { return this.Title; }

    public void setTitle(String Title) { this.Title = Title; }

    private String FirstName;

    public String getFirstName() { return this.FirstName; }

    public void setFirstName(String FirstName) { this.FirstName = FirstName; }

    private String LastName;

    public String getLastName() { return this.LastName; }

    public void setLastName(String LastName) { this.LastName = LastName; }

    private String Phone;

    public String getPhone() { return this.Phone; }

    public void setPhone(String Phone) { this.Phone = Phone; }

    private String Gender;

    public String getGender() { return this.Gender; }

    public void setGender(String Gender) { this.Gender = Gender; }

    private String DateOfBirth;

    public String getDateOfBirth() { return this.DateOfBirth; }

    public void setDateOfBirth(String DateOfBirth) { this.DateOfBirth = DateOfBirth; }

    private int ConfirmationNo;

    public int getConfirmationNo() { return this.ConfirmationNo; }

    public void setConfirmationNo(int ConfirmationNo) { this.ConfirmationNo = ConfirmationNo; }

    private int AppointmentTypeRefID;

    public int getAppointmentTypeRefID() { return this.AppointmentTypeRefID; }

    public void setAppointmentTypeRefID(int AppointmentTypeRefID) { this.AppointmentTypeRefID = AppointmentTypeRefID; }

    private String ServiceID;

    public String getServiceID() { return this.ServiceID; }

    public void setServiceID(String ServiceID) { this.ServiceID = ServiceID; }

    private String StartTime;

    public String getStartTime() { return this.StartTime; }

    public void setStartTime(String StartTime) { this.StartTime = StartTime; }

    private String EndTime;

    public String getEndTime() { return this.EndTime; }

    public void setEndTime(String EndTime) { this.EndTime = EndTime; }

    private String ScheduledTimeZone;

    public String getScheduledTimeZone() { return this.ScheduledTimeZone; }

    public void setScheduledTimeZone(String ScheduledTimeZone) { this.ScheduledTimeZone = ScheduledTimeZone; }

    private int SendEmailReminder;

    public int getSendEmailReminder() { return this.SendEmailReminder; }

    public void setSendEmailReminder(int SendEmailReminder) { this.SendEmailReminder = SendEmailReminder; }

    private int SendTextReminder;

    public int getSendTextReminder() { return this.SendTextReminder; }

    public void setSendTextReminder(int SendTextReminder) { this.SendTextReminder = SendTextReminder; }

    private String AdditionalEmail;

    public String getAdditionalEmail() { return this.AdditionalEmail; }

    public void setAdditionalEmail(String AdditionalEmail) { this.AdditionalEmail = AdditionalEmail; }

    private int IsLoggedIn;

    public int getIsLoggedIn() { return this.IsLoggedIn; }

    public void setIsLoggedIn(int IsLoggedIn) { this.IsLoggedIn = IsLoggedIn; }

    private int IsCheckedIn;

    public int getIsCheckedIn() { return this.IsCheckedIn; }

    public void setIsCheckedIn(int IsCheckedIn) { this.IsCheckedIn = IsCheckedIn; }

    private int IsCancelled;

    public int getIsCancelled() { return this.IsCancelled; }

    public void setIsCancelled(int IsCancelled) { this.IsCancelled = IsCancelled; }

    private String CancelTypeRefID;

    public String getCancelTypeRefID() { return this.CancelTypeRefID; }

    public void setCancelTypeRefID(String CancelTypeRefID) { this.CancelTypeRefID = CancelTypeRefID; }

    private String CancelledBy;

    public String getCancelledBy() { return this.CancelledBy; }

    public void setCancelledBy(String CancelledBy) { this.CancelledBy = CancelledBy; }

    private int IsAssigned;

    public int getIsAssigned() { return this.IsAssigned; }

    public void setIsAssigned(int IsAssigned) { this.IsAssigned = IsAssigned; }

    private String AssignedTo;

    public String getAssignedTo() { return this.AssignedTo; }

    public void setAssignedTo(String AssignedTo) { this.AssignedTo = AssignedTo; }

    private String AuditID;

    public String getAuditID() { return this.AuditID; }

    public void setAuditID(String AuditID) { this.AuditID = AuditID; }

    private String OrgID;

    public String getOrgID() { return this.OrgID; }

    public void setOrgID(String OrgID) { this.OrgID = OrgID; }
}