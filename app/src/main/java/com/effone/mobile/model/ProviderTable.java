package com.effone.mobile.model;

import io.realm.RealmObject;

/**
 * Created by sarith.vasu on 30-10-2017.
 */

public class ProviderTable extends RealmObject{
    private int UserID;

    public int getUserID() { return this.UserID; }

    public void setUserID(int UserID) { this.UserID = UserID; }

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

    private boolean DisplayUserName;

    public boolean getDisplayUserName() { return this.DisplayUserName; }

    public void setDisplayUserName(boolean DisplayUserName) { this.DisplayUserName = DisplayUserName; }

    private String DateOfBirth;

    public String getDateOfBirth() { return this.DateOfBirth; }

    public void setDateOfBirth(String DateOfBirth) { this.DateOfBirth = DateOfBirth; }

    private String ProfilePicture;

    public String getProfilePicture() { return this.ProfilePicture; }

    public void setProfilePicture(String ProfilePicture) { this.ProfilePicture = ProfilePicture; }

    private String Phone;

    public String getPhone() { return this.Phone; }

    public void setPhone(String Phone) { this.Phone = Phone; }

    private String Email;

    public String getEmail() { return this.Email; }

    public void setEmail(String Email) { this.Email = Email; }

    private String Password;

    public String getPassword() { return this.Password; }

    public void setPassword(String Password) { this.Password = Password; }

    private boolean IsTempPassword;

    public boolean getIsTempPassword() { return this.IsTempPassword; }

    public void setIsTempPassword(boolean IsTempPassword) { this.IsTempPassword = IsTempPassword; }

    private String PreferredLocID;

    public String getPreferredLocID() { return this.PreferredLocID; }

    public void setPreferredLocID(String PreferredLocID) { this.PreferredLocID = PreferredLocID; }

    private String PrimaryLocID;

    public String getPrimaryLocID() { return this.PrimaryLocID; }

    public void setPrimaryLocID(String PrimaryLocID) { this.PrimaryLocID = PrimaryLocID; }

    private boolean IsActive;

    public boolean getIsActive() { return this.IsActive; }

    public void setIsActive(boolean IsActive) { this.IsActive = IsActive; }

    private String ImageFormat;

    public String getImageFormat() { return this.ImageFormat; }

    public void setImageFormat(String ImageFormat) { this.ImageFormat = ImageFormat; }

    private int AuditID;

    public int getAuditID() { return this.AuditID; }

    public void setAuditID(int AuditID) { this.AuditID = AuditID; }

    private String UserName;

    public String getUserName() { return this.UserName; }

    public void setUserName(String UserName) { this.UserName = UserName; }

    private int UserXOrgID;

    public int getUserXOrgID() { return this.UserXOrgID; }

    public void setUserXOrgID(int UserXOrgID) { this.UserXOrgID = UserXOrgID; }

    private String Role;

    public String getRole() { return this.Role; }

    public void setRole(String Role) { this.Role = Role; }

    private int RoleID;

    public int getRoleID() { return this.RoleID; }

    public void setRoleID(int RoleID) { this.RoleID = RoleID; }

    private int PlanID;

    public int getPlanID() { return this.PlanID; }

    public void setPlanID(int PlanID) { this.PlanID = PlanID; }

    private int OrgID;

    public int getOrgID() { return this.OrgID; }

    public void setOrgID(int OrgID) { this.OrgID = OrgID; }

    private boolean IsEndUser;

    public boolean getIsEndUser() { return this.IsEndUser; }

    public void setIsEndUser(boolean IsEndUser) { this.IsEndUser = IsEndUser; }

    private String BaseUrl;

    public String getBaseUrl() { return this.BaseUrl; }

    public void setBaseUrl(String BaseUrl) { this.BaseUrl = BaseUrl; }

    private String LoginUrl;

    public String getLoginUrl() { return this.LoginUrl; }

    public void setLoginUrl(String LoginUrl) { this.LoginUrl = LoginUrl; }

    private boolean IsLogIn;

    public boolean getIsLogIn() { return this.IsLogIn; }

    public void setIsLogIn(boolean IsLogIn) { this.IsLogIn = IsLogIn; }

    private String ProPicBase64;

    public String getProPicBase64() { return this.ProPicBase64; }

    public void setProPicBase64(String ProPicBase64) { this.ProPicBase64 = ProPicBase64; }
}
