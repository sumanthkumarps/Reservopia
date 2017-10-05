package com.effone.mobile.realmdb;

import io.realm.RealmObject;

/**
 * Created by sarith.vasu on 22-09-2017.
 */

public class ServiceProvidedTable extends RealmObject {

    private String LocID;

    private String NOA;

    private String ServiceID;

    private String LocXServiceID;

    private String MaxAppointmentDuration;

    private String AuditID;

    private String AppointmentDuration;

    public String getLocID ()
    {
        return LocID;
    }

    public void setLocID (String LocID)
    {
        this.LocID = LocID;
    }

    public String getNOA ()
    {
        return NOA;
    }

    public void setNOA (String NOA)
    {
        this.NOA = NOA;
    }

    public String getServiceID ()
    {
        return ServiceID;
    }

    public void setServiceID (String ServiceID)
    {
        this.ServiceID = ServiceID;
    }

    public String getLocXServiceID ()
    {
        return LocXServiceID;
    }

    public void setLocXServiceID (String LocXServiceID)
    {
        this.LocXServiceID = LocXServiceID;
    }

    public String getMaxAppointmentDuration ()
    {
        return MaxAppointmentDuration;
    }

    public void setMaxAppointmentDuration (String MaxAppointmentDuration)
    {
        this.MaxAppointmentDuration = MaxAppointmentDuration;
    }

    public String getAuditID ()
    {
        return AuditID;
    }

    public void setAuditID (String AuditID)
    {
        this.AuditID = AuditID;
    }

    public String getAppointmentDuration ()
    {
        return AppointmentDuration;
    }

    public void setAppointmentDuration (String AppointmentDuration)
    {
        this.AppointmentDuration = AppointmentDuration;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [LocID = "+LocID+", NOA = "+NOA+", ServiceID = "+ServiceID+", LocXServiceID = "+LocXServiceID+", MaxAppointmentDuration = "+MaxAppointmentDuration+", AuditID = "+AuditID+", AppointmentDuration = "+AppointmentDuration+"]";
    }
}
