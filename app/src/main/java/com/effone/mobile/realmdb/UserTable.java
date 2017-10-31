package com.effone.mobile.realmdb;

import io.realm.RealmObject;

/**
 * Created by sarith.vasu on 30-10-2017.
 */

public class UserTable extends RealmObject{
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

    private String Phone;

    public String getPhone() { return this.Phone; }

    public void setPhone(String Phone) { this.Phone = Phone; }

    private String Gender;

    public String getGender() { return this.Gender; }

    public void setGender(String Gender) { this.Gender = Gender; }

    private String DateOfBirth;

    public String getDateOfBirth() { return this.DateOfBirth; }

    public void setDateOfBirth(String DateOfBirth) { this.DateOfBirth = DateOfBirth; }
}
