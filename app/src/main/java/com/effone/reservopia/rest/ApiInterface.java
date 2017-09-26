package com.effone.reservopia.rest;



import com.effone.reservopia.model.AppointmentBookingModel;
import com.effone.reservopia.model.Confirmation;
import com.effone.reservopia.model.DateTime;
import com.effone.reservopia.model.LocationAndService;
import com.effone.reservopia.model.Response;
import com.effone.reservopia.model.UpCommingAppointmentModel;

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
    Call<UpCommingAppointmentModel> getUpCommingAppointmentDetails(@Header("Token") String apiKey,@Header("Content-Type") String contentType, @Query("orgID") int orgID,
                                                                   @Query("email") String email);
    @GET("Appointment/GetLocationNService")
    Call<LocationAndService> getLocationNService(@Header("Token") String apiKey, @Query("orgID") int locId);

    @Headers("Content-type: application/json")
    @POST("Appointment/ManageAppointment")
    Call<Response> createAppointment(@Header("Token") String apiKey, @Body AppointmentBookingModel mAppointmentBookingModel);


    @GET("Appointment/GetAppointmentSlots")
    Call<DateTime> getDateTimeSlots(@Header("Token") String apiKey, @Query("locId") int locId,
                                    @Query("serviceId") int serviceId, @Query("startTime") String date, @Query("localTimeZone") String timeZone);


    @GET("Appointment/GetAppointmentByConfirmationNo")
    Call<Confirmation> getConfirmationDetails(@Header("Token") String apiKey, @Query("confirmationNo") String confiramtionNo);


}
