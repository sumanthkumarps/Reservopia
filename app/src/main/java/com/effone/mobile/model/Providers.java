package com.effone.mobile.model;

import io.realm.RealmObject;

/**
 * Created by sarith.vasu on 30-10-2017.
 */

public class Providers  {
    private String OrgID;

    private String ServiceID;

    private String UserID;

    private String ProviderXServiceID;

    private String AuditID;

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

    public String getUserID ()
    {
        return UserID;
    }

    public void setUserID (String UserID)
    {
        this.UserID = UserID;
    }

    public String getProviderXServiceID ()
    {
        return ProviderXServiceID;
    }

    public void setProviderXServiceID (String ProviderXServiceID)
    {
        this.ProviderXServiceID = ProviderXServiceID;
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
        return "ClassPojo [OrgID = "+OrgID+", ServiceID = "+ServiceID+", UserID = "+UserID+", ProviderXServiceID = "+ProviderXServiceID+", AuditID = "+AuditID+"]";
    }
}
