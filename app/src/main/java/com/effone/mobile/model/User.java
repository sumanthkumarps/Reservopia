package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 10/4/2017.
 */

public class User {
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

    private String Gender;

    public String getGender() { return this.Gender; }

    public void setGender(String Gender) { this.Gender = Gender; }

    private String DisplayUserName;

    public String getDisplayUserName() { return this.DisplayUserName; }

    public void setDisplayUserName(String DisplayUserName) { this.DisplayUserName = DisplayUserName; }

    private String DateOfBirth;

    public String getDateOfBirth() { return this.DateOfBirth; }

    public void setDateOfBirth(String DateOfBirth) { this.DateOfBirth = DateOfBirth; }

    private String Phone;

    public String getPhone() { return this.Phone; }

    public void setPhone(String Phone) { this.Phone = Phone; }

    private String Email;

    public String getEmail() { return this.Email; }

    public void setEmail(String Email) { this.Email = Email; }

    private String Password;

    public String getPassword() { return this.Password; }

    public void setPassword(String Password) { this.Password = Password; }

    private int IsTempPassword;

    public int getIsTempPassword() { return this.IsTempPassword; }

    public void setIsTempPassword(int IsTempPassword) { this.IsTempPassword = IsTempPassword; }

    private String PreferredLocID;

    public String getPreferredLocID() { return this.PreferredLocID; }

    public void setPreferredLocID(String PreferredLocID) { this.PreferredLocID = PreferredLocID; }

    private String PrimaryLocID;

    public String getPrimaryLocID() { return this.PrimaryLocID; }

    public void setPrimaryLocID(String PrimaryLocID) { this.PrimaryLocID = PrimaryLocID; }

    private int IsActive;

    public int getIsActive() { return this.IsActive; }

    public void setIsActive(int IsActive) { this.IsActive = IsActive; }

    private int AuditID;

    public int getAuditID() { return this.AuditID; }

    public void setAuditID(int AuditID) { this.AuditID = AuditID; }

    private UserAddress Address;

    public UserAddress getAddress() { return this.Address; }

    public void setAddress(UserAddress Address) { this.Address = Address; }

    private int OrgID;

    public int getOrgID() { return this.OrgID; }

    public void setOrgID(int OrgID) { this.OrgID = OrgID; }

    private int IsEndUser;

    public int getIsEndUser() { return this.IsEndUser; }

    public void setIsEndUser(int IsEndUser) { this.IsEndUser = IsEndUser; }
}
