package com.effone.mobile.model;

/**
 * Created by sumanth.peddinti on 10/4/2017.
 */

public class BookingAppointmentUserDetails {
    private User User;

    private AppointmentBookingModel Appointment;

    public User getUser ()
    {
        return User;
    }

    public void setUser (User User)
    {
        this.User = User;
    }

    public AppointmentBookingModel getAppointment ()
    {
        return Appointment;
    }

    public void setAppointment (AppointmentBookingModel Appointment)
    {
        this.Appointment = Appointment;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [User = "+User+", Appointment = "+Appointment+"]";
    }
}
