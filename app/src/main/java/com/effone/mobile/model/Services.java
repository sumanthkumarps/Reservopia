package com.effone.mobile.model;

import java.util.ArrayList;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class Services {
    private ArrayList<Providers> Providers;

    private String Description;

    private String ServiceName;

    private String IsActive;

    private String OrgID;

    private String ServiceID;

    private String Duration;

    private String IsDeleted;

    private String IsIntrinsic;

    private String IsUnUsed;

    private String AuditID;

    public ArrayList<Providers>  getProviders ()
    {
        return Providers;
    }

    public void setProviders (ArrayList<Providers>  Providers)
    {
        this.Providers = Providers;
    }

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
        return "ClassPojo [Providers = "+Providers+", Description = "+Description+", ServiceName = "+ServiceName+", IsActive = "+IsActive+", OrgID = "+OrgID+", ServiceID = "+ServiceID+", Duration = "+Duration+", IsDeleted = "+IsDeleted+", IsIntrinsic = "+IsIntrinsic+", IsUnUsed = "+IsUnUsed+", AuditID = "+AuditID+"]";
    }
}
