package com.effone.reservopia.realmdb;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class ServiceTable extends RealmObject implements Serializable {
    public String ServiceID;

    public String getServiceID() { return this.ServiceID; }

    public void setServiceID(String ServiceID) { this.ServiceID = ServiceID; }

    public int OrgID;

    public int getOrgID() { return this.OrgID; }

    public void setOrgID(int OrgID) { this.OrgID = OrgID; }

    public String ServiceName;

    public String getServiceName() { return this.ServiceName; }

    public void setServiceName(String ServiceName) { this.ServiceName = ServiceName; }

    public String Description;

    public String getDescription() { return this.Description; }

    public void setDescription(String Description) { this.Description = Description; }

    public String Duration;

    public String getDuration() { return this.Duration; }

    public void setDuration(String Duration) { this.Duration = Duration; }

    public boolean IsIntrinsic;

    public boolean getIsIntrinsic() { return this.IsIntrinsic; }

    public void setIsIntrinsic(boolean IsIntrinsic) { this.IsIntrinsic = IsIntrinsic; }

    public String IsActive;

    public String getIsActive() { return this.IsActive; }

    public void setIsActive(String IsActive) { this.IsActive = IsActive; }

    public boolean IsDeleted;

    public boolean getIsDeleted() { return this.IsDeleted; }

    public void setIsDeleted(boolean IsDeleted) { this.IsDeleted = IsDeleted; }

    public boolean IsUnUsed;

    public boolean getIsUnUsed() { return this.IsUnUsed; }

    public void setIsUnUsed(boolean IsUnUsed) { this.IsUnUsed = IsUnUsed; }

    public int AuditID;

    public int getAuditID() { return this.AuditID; }

    public void setAuditID(int AuditID) { this.AuditID = AuditID; }
}
