package com.effone.mobile.rest;



import com.effone.mobile.model.AppointmentBookingModel;
import com.effone.mobile.model.BookingAppointmentUserDetails;
import com.effone.mobile.model.Confirmation;
import com.effone.mobile.model.DateTime;
import com.effone.mobile.model.GetTimeZones;
import com.effone.mobile.model.LocationAndService;
import com.effone.mobile.model.Response;
import com.effone.mobile.model.Title;
import com.effone.mobile.model.UpCommingAppointmentModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */

public interface ApiInterface {
    @GET("UserAppointment/GetUserAppointmentDetails")
    Call<UpCommingAppointmentModel> getUpCommingAppointmentDetails(@Header("Token") String apiKey,@Header("Content-Type") String contentType, @Query("orgID") String orgID,
                                                                   @Query("email") String email);
    @GET("Appointment/GetLocationNService")
    Call<LocationAndService> getLocationNService(@Header("Token") String apiKey, @Query("orgID") String locId);

    @Headers("Content-type: application/json")
    @POST("Appointment/ManageAppointment")
    Call<Response> createAppointment(@Header("Token") String apiKey, @Body AppointmentBookingModel mAppointmentBookingModel);


    @GET("Appointment/GetAppointmentSlots")
    Call<DateTime> getDateTimeSlots(@Header("Token") String apiKey, @Query("locId") String locId,
                                    @Query("serviceId") String serviceId, @Query("startTime") String date, @Query("localTimeZone") String timeZone);


    @GET("Appointment/GetAppointmentInfoByConformationNo")
    Call<Confirmation> getConfirmationDetails(@Header("Token") String apiKey, @Query("confirmationNo") String confirmation, @Query("orgID") String orgId);

    @GET("UserRegister/GetCultureSpecificTitles")
    Call<Title> getTitleDetails(@Header("Token") String apiKey);

    @GET("Common/GetTimeZones")
    Call<GetTimeZones> getTimeZoneDetails(@Header("Token") String apiKey);

    @GET("Register/IsUserEmailExist")
    Call<Response> getEmailExists(@Header("Token") String apiKey, @Query("email") String email);

    @Headers("Content-type: application/json")
    @POST("Appointment/CreateUserNBookAppointment")
    Call<Response> createAcountAndAppointemnt(@Header("Token") String apiKey, @Body BookingAppointmentUserDetails bookingAppointmentUserDetails);

}