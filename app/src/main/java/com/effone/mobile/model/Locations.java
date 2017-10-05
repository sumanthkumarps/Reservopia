package com.effone.mobile.model;

import com.effone.mobile.realmdb.ServiceProvidedTable;

import io.realm.RealmList;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class Locations {
    private String LocName;

    private String LocID;

    private String Phone1;

    private String Phone2;

    private String AdvanceBookingDays;

    private String Fax;

    private String LeadTime;

    private String IsIntrinsic;

    private String IsUnUsed;

    private String AuditID;

    private String IsAppointmentsForever;

    private String AptStartRangeDate;

    private String IsActive;

    private String OrgID;

    private String CutOffTime;

    private Address Address;

    private String Latitude;

    private String LocationTimeZone;

    private String Longitude;

    private WorkHours[] WorkHours;

    private String AptEndRangeDate;

    private RealmList<ServiceProvidedTable> ServicesProvided;


    public RealmList<ServiceProvidedTable> getServicesProvided() {
        return ServicesProvided;
    }

    public void setServicesProvided(RealmList<ServiceProvidedTable> servicesProvided) {
        ServicesProvided = servicesProvided;
    }


    public String getLocName ()
    {
        return LocName;
    }

    public void setLocName (String LocName)
    {
        this.LocName = LocName;
    }

    public String getLocID ()
    {
        return LocID;
    }

    public void setLocID (String LocID)
    {
        this.LocID = LocID;
    }

    public String getPhone1 ()
    {
        return Phone1;
    }

    public void setPhone1 (String Phone1)
    {
        this.Phone1 = Phone1;
    }

    public String getPhone2 ()
    {
        return Phone2;
    }

    public void setPhone2 (String Phone2)
    {
        this.Phone2 = Phone2;
    }



    public String getAdvanceBookingDays ()
    {
        return AdvanceBookingDays;
    }

    public void setAdvanceBookingDays (String AdvanceBookingDays)
    {
        this.AdvanceBookingDays = AdvanceBookingDays;
    }

    public String getFax ()
    {
        return Fax;
    }

    public void setFax (String Fax)
    {
        this.Fax = Fax;
    }



    public String getLeadTime ()
    {
        return LeadTime;
    }

    public void setLeadTime (String LeadTime)
    {
        this.LeadTime = LeadTime;
    }

    public String getIsIntrinsic ()
    {
        return IsIntrinsic;
    }

    public void setIsIntrinsic (String IsIntrinsic)
    {
        this.IsIntrinsic = IsIntrinsic;
    }

    public String getIsUnUsed ()
    {
        return IsUnUsed;
    }

    public void setIsUnUsed (String IsUnUsed)
    {
        this.IsUnUsed = IsUnUsed;
    }

    public String getAuditID ()
    {
        return AuditID;
    }

    public void setAuditID (String AuditID)
    {
        this.AuditID = AuditID;
    }

    public String getIsAppointmentsForever ()
    {
        return IsAppointmentsForever;
    }

    public void setIsAppointmentsForever (String IsAppointmentsForever)
    {
        this.IsAppointmentsForever = IsAppointmentsForever;
    }

    public String getAptStartRangeDate ()
    {
        return AptStartRangeDate;
    }

    public void setAptStartRangeDate (String AptStartRangeDate)
    {
        this.AptStartRangeDate = AptStartRangeDate;
    }

    public String getIsActive ()
    {
        return IsActive;
    }

    public void setIsActive (String IsActive)
    {
        this.IsActive = IsActive;
    }

    public String getOrgID ()
    {
        return OrgID;
    }

    public void setOrgID (String OrgID)
    {
        this.OrgID = OrgID;
    }

    public String getCutOffTime ()
    {
        return CutOffTime;
    }

    public void setCutOffTime (String CutOffTime)
    {
        this.CutOffTime = CutOffTime;
    }

    public Address getAddress ()
    {
        return Address;
    }

    public void setAddress (Address Address)
    {
        this.Address = Address;
    }

    public String getLatitude ()
    {
        return Latitude;
    }

    public void setLatitude (String Latitude)
    {
        this.Latitude = Latitude;
    }

    public String getLocationTimeZone ()
    {
        return LocationTimeZone;
    }

    public void setLocationTimeZone (String LocationTimeZone)
    {
        this.LocationTimeZone = LocationTimeZone;
    }

    public String getLongitude ()
    {
        return Longitude;
    }

    public void setLongitude (String Longitude)
    {
        this.Longitude = Longitude;
    }

    public WorkHours[] getWorkHours() {

        return WorkHours;
    }

    public void setWorkHours (WorkHours[] WorkHours)
    {
        this.WorkHours = WorkHours;
    }

    public String getAptEndRangeDate ()
    {
        return AptEndRangeDate;
    }

    public void setAptEndRangeDate (String AptEndRangeDate)
    {
        this.AptEndRangeDate = AptEndRangeDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [LocName = "+LocName+", LocID = "+LocID+", Phone1 = "+Phone1+", Phone2 = "+Phone2+", ServicesProvided = "+AdvanceBookingDays+", Fax = "+Fax+", LeadTime = "+LeadTime+", IsIntrinsic = "+IsIntrinsic+", IsUnUsed = "+IsUnUsed+", AuditID = "+AuditID+", IsAppointmentsForever = "+IsAppointmentsForever+", AptStartRangeDate = "+AptStartRangeDate+", IsActive = "+IsActive+", OrgID = "+OrgID+", CutOffTime = "+CutOffTime+", Address = "+Address+", Latitude = "+Latitude+", LocationTimeZone = "+LocationTimeZone+", Longitude = "+Longitude+", WorkHours = "+WorkHours+", AptEndRangeDate = "+AptEndRangeDate+"]";
    }
}
