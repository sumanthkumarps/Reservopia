package com.effone.reservopia.realmdb;

import io.realm.RealmObject;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class LocXServiceTable extends RealmObject{
    private String LocID;

    private String OrgID;

    private String ServiceID;

    private String LocXServiceID;

    public String getLocID() {
        return LocID;
    }

    public void setLocID(String locID) {
        LocID = locID;
    }

    public String getOrgID() {
        return OrgID;
    }

    public void setOrgID(String orgID) {
        OrgID = orgID;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getLocXServiceID() {
        return LocXServiceID;
    }

    public void setLocXServiceID(String locXServiceID) {
        LocXServiceID = locXServiceID;
    }
}
