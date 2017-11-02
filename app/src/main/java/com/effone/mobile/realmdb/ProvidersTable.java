package com.effone.mobile.realmdb;

import io.realm.RealmObject;

/**
 * Created by sarith.vasu on 30-10-2017.
 */

public class ProvidersTable  extends RealmObject {
    private int ProviderXServiceID;

    private int AuditID;

    private int OrgID;

    private int ServiceID;

    private int UserID;

    public int getUserID() { return this.UserID; }

    public void setUserID(int UserID) { this.UserID = UserID; }


    public int getServiceID() { return this.ServiceID; }

    public void setServiceID(int ServiceID) { this.ServiceID = ServiceID; }



    public int getAuditID() { return this.AuditID; }

    public void setAuditID(int AuditID) { this.AuditID = AuditID; }


    public int getOrgID() { return this.OrgID; }

    public void setOrgID(int OrgID) { this.OrgID = OrgID; }
    public int getProviderXServiceID() { return this.ProviderXServiceID; }

    public void setProviderXServiceID(int ProviderXServiceID) { this.ProviderXServiceID = ProviderXServiceID; }
}
