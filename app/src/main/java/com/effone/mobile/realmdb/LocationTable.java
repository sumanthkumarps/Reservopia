package com.effone.mobile.realmdb;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class LocationTable extends RealmObject implements Serializable {
    private int AdvanceBookingDays;

    private String Fax;

    private int LeadTime;

    private int AuditID;

    private String AptStartRangeDate;

    private String OrgID;

    private String CutOffTime;

    private String Latitude;

    private String LocationTimeZone;

    private String Longitude;

    private String AptEndRangeDate;

    private int LocID;

    private String LocName;

    private String Phone1;

    private String Phone2;

    private String AddressLine1;

    private String AddressLine2;

    private String AddressLine3;

    private String City;

    private String State;

    private String Zip;

    private String Country;

    private RealmList<ServiceProvidedTable> serviceProvidedTables;

    public RealmList<ServiceProvidedTable> getServiceProvidedTables() {
        return serviceProvidedTables;
    }

    public void setServiceProvidedTables(RealmList<ServiceProvidedTable> serviceProvidedTables) {
        this.serviceProvidedTables = serviceProvidedTables;
    }

    public int getAdvanceBookingDays() {
        return AdvanceBookingDays;
    }

    public void setAdvanceBookingDays(int advanceBookingDays) {
        this.AdvanceBookingDays = advanceBookingDays;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        this.Fax = fax;
    }

    public int getLeadTime() {
        return LeadTime;
    }

    public void setLeadTime(int leadTime) {
        this.LeadTime = leadTime;
    }

    public int getAuditID() {
        return AuditID;
    }

    public void setAuditID(int auditID) {
        this.AuditID = auditID;
    }

    public String getAptStartRangeDate() {
        return AptStartRangeDate;
    }

    public void setAptStartRangeDate(String aptStartRangeDate) {
        this.AptStartRangeDate = aptStartRangeDate;
    }
    public String getOrgID() {
        return OrgID;
    }

    public void setOrgID(String orgID) {
        this.OrgID = orgID;
    }

    public String getCutOffTime() {
        return CutOffTime;
    }

    public void setCutOffTime(String cutOffTime) {
        this.CutOffTime = cutOffTime;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        this.Latitude = latitude;
    }

    public String getLocationTimeZone() {
        return LocationTimeZone;
    }

    public void setLocationTimeZone(String locationTimeZone) {
        this.LocationTimeZone = locationTimeZone;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        this.Longitude = longitude;
    }

    public String getAptEndRangeDate() {
        return AptEndRangeDate;
    }

    public void setAptEndRangeDate(String aptEndRangeDate) {
        this.AptEndRangeDate = aptEndRangeDate;
    }

    public int getLocID() {
        return LocID;
    }

    public void setLocID(int locID) {
        this.LocID = locID;
    }

    public String getLocName() {
        return LocName;
    }

    public void setLocName(String locName) {
        this.LocName = locName;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        this.Phone1 = phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        this.Phone2 = phone2;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.AddressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return AddressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.AddressLine3 = addressLine3;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        this.State = state;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        this.Zip = zip;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }
}
