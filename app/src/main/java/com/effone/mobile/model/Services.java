package com.effone.mobile.model;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class Services {
    private String Description;

    private String ServiceName;

    private String IsActive;

    private String OrgID;

    private String ServiceID;

    private String Duration;

    private String IsDeleted;

    private String IsIntrinsic;

    private String[] WorkHours;

    private String IsUnUsed;

    private String AuditID;

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getServiceName ()
    {
        return ServiceName;
    }

    public void setServiceName (String ServiceName)
    {
        this.ServiceName = ServiceName;
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

    public String getServiceID ()
    {
        return ServiceID;
    }

    public void setServiceID (String ServiceID)
    {
        this.ServiceID = ServiceID;
    }

    public String getDuration ()
    {
        return Duration;
    }

    public void setDuration (String Duration)
    {
        this.Duration = Duration;
    }

    public String getIsDeleted ()
    {
        return IsDeleted;
    }

    public void setIsDeleted (String IsDeleted)
    {
        this.IsDeleted = IsDeleted;
    }

    public String getIsIntrinsic ()
    {
        return IsIntrinsic;
    }

    public void setIsIntrinsic (String IsIntrinsic)
    {
        this.IsIntrinsic = IsIntrinsic;
    }

    public String[] getWorkHours ()
    {
        return WorkHours;
    }

    public void setWorkHours (String[] WorkHours)
    {
        this.WorkHours = WorkHours;
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

    @Override
    public String toString()
    {
        return "ClassPojo [Description = "+Description+", ServiceName = "+ServiceName+", IsActive = "+IsActive+", OrgID = "+OrgID+", ServiceID = "+ServiceID+", Duration = "+Duration+", IsDeleted = "+IsDeleted+", IsIntrinsic = "+IsIntrinsic+", WorkHours = "+WorkHours+", IsUnUsed = "+IsUnUsed+", AuditID = "+AuditID+"]";
    }
}
