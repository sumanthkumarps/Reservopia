package com.effone.reservopia.model;

/**
 * Created by sumanth.peddinti on 10/4/2017.
 */

public class User {
    private String DisplayUserName;

    private String Phone;

    private String Password;

    private String UserID;

    private String LastName;

    private String Title;

    private String AuditID;

    private String DateOfBirth;

    private String IsTempPassword;

    private String Email;

    private String OrgID;

    private String IsActive;

    private UserAddress Address;

    private String Gender;

    private String IsEndUser;

    private String FirstName;

    private String PreferredLocID;

    private String PrimaryLocID;

    public String getDisplayUserName ()
    {
        return DisplayUserName;
    }

    public void setDisplayUserName (String DisplayUserName)
    {
        this.DisplayUserName = DisplayUserName;
    }

    public String getPhone ()
    {
        return Phone;
    }

    public void setPhone (String Phone)
    {
        this.Phone = Phone;
    }

    public String getPassword ()
    {
        return Password;
    }

    public void setPassword (String Password)
    {
        this.Password = Password;
    }

    public String getUserID ()
    {
        return UserID;
    }

    public void setUserID (String UserID)
    {
        this.UserID = UserID;
    }

    public String getLastName ()
    {
        return LastName;
    }

    public void setLastName (String LastName)
    {
        this.LastName = LastName;
    }

    public String getTitle ()
    {
        return Title;
    }

    public void setTitle (String Title)
    {
        this.Title = Title;
    }

    public String getAuditID ()
    {
        return AuditID;
    }

    public void setAuditID (String AuditID)
    {
        this.AuditID = AuditID;
    }

    public String getDateOfBirth ()
    {
        return DateOfBirth;
    }

    public void setDateOfBirth (String DateOfBirth)
    {
        this.DateOfBirth = DateOfBirth;
    }

    public String getIsTempPassword ()
    {
        return IsTempPassword;
    }

    public void setIsTempPassword (String IsTempPassword)
    {
        this.IsTempPassword = IsTempPassword;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getOrgID ()
    {
        return OrgID;
    }

    public void setOrgID (String OrgID)
    {
        this.OrgID = OrgID;
    }

    public String getIsActive ()
    {
        return IsActive;
    }

    public void setIsActive (String IsActive)
    {
        this.IsActive = IsActive;
    }

    public UserAddress getAddress ()
    {
        return Address;
    }

    public void setAddress (UserAddress Address)
    {
        this.Address = Address;
    }

    public String getGender ()
    {
        return Gender;
    }

    public void setGender (String Gender)
    {
        this.Gender = Gender;
    }

    public String getIsEndUser ()
    {
        return IsEndUser;
    }

    public void setIsEndUser (String IsEndUser)
    {
        this.IsEndUser = IsEndUser;
    }

    public String getFirstName ()
    {
        return FirstName;
    }

    public void setFirstName (String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getPreferredLocID ()
    {
        return PreferredLocID;
    }

    public void setPreferredLocID (String PreferredLocID)
    {
        this.PreferredLocID = PreferredLocID;
    }

    public String getPrimaryLocID ()
    {
        return PrimaryLocID;
    }

    public void setPrimaryLocID (String PrimaryLocID)
    {
        this.PrimaryLocID = PrimaryLocID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DisplayUserName = "+DisplayUserName+", Phone = "+Phone+", Password = "+Password+", UserID = "+UserID+", LastName = "+LastName+", Title = "+Title+", AuditID = "+AuditID+", DateOfBirth = "+DateOfBirth+", IsTempPassword = "+IsTempPassword+", Email = "+Email+", OrgID = "+OrgID+", IsActive = "+IsActive+", Address = "+Address+", Gender = "+Gender+", IsEndUser = "+IsEndUser+", FirstName = "+FirstName+", PreferredLocID = "+PreferredLocID+", PrimaryLocID = "+PrimaryLocID+"]";
    }
}
