package com.effone.mobile.rest;



import com.effone.mobile.model.AppointmentBookingModel;
import com.effone.mobile.model.BookingAppointmentUserDetails;
import com.effone.mobile.model.CancelAppointment;
import com.effone.mobile.model.CancelAppointmentBoby;
import com.effone.mobile.model.ChangePassword;
import com.effone.mobile.model.Confirmation;
import com.effone.mobile.model.DateTime;
import com.effone.mobile.model.ForgotPasswordResponse;
import com.effone.mobile.model.GetTimeZones;
import com.effone.mobile.model.History;
import com.effone.mobile.model.LocationAndService;
import com.effone.mobile.model.LoginResult;
import com.effone.mobile.model.Response;
import com.effone.mobile.model.SearchAppointment;
import com.effone.mobile.model.Title;
import com.effone.mobile.model.UpCommingAppointmentModel;
import com.effone.mobile.model.User;

import com.effone.mobile.model.UserDetails;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */


public interface ApiInterface {
    @GET("UserAppointment/GetUserAppointmentDetails")
    Call<UpCommingAppointmentModel> getUpCommingAppointmentDetails(@Header("Token") String apiKey, @Header("Content-Type") String contentType, @Query("orgID") String orgID,
                                                                   @Query("email") String email);

    @GET("Appointment/GetLocationNService")
    Call<LocationAndService> getLocationNService(@Header("Token") String apiKey, @Query("orgID") String locId);

    @Headers("Content-type: application/json")
    @POST("Appointment/ManageAppointment")
    Call<Response> createAppointment(@Header("Token") String apiKey, @Body AppointmentBookingModel mAppointmentBookingModel);


    @GET("Appointment/GetAppointmentSlots")
    Call<DateTime> getDateTimeSlots(@Header("Token") String apiKey, @Query("orgId") String orgId, @Query("locId") String locId,
                                    @Query("serviceId") String serviceId, @Query("startTime") String date, @Query("localTimeZone") String timeZone, @Query("providerId") int providerId);


    @GET("Appointment/GetAppointmentInfoByConformationNo")
    Call<Confirmation> getConfirmationDetails(@Header("Token") String apiKey, @Query("confirmationNo") String confirmation, @Query("orgID") String orgId);

    @GET("UserRegister/GetCultureSpecificTitles")
    Call<Title> getTitleDetails(@Header("Token") String apiKey);

    @GET("Common/GetTimeZones")
    Call<GetTimeZones> getTimeZoneDetails(@Header("Token") String apiKey);

    @GET("Register/IsUserEmailExist")
    Call<Response> getEmailExists(@Header("Token") String apiKey, @Query("email") String email, @Query("orgID") String org_id);

    @Headers("Content-type: application/json")
    @POST("Appointment/CreateUserNBookAppointment")
    Call<Response> createAcountAndAppointemnt(@Header("Token") String apiKey, @Body BookingAppointmentUserDetails bookingAppointmentUserDetails);

    @GET("access/login")
    Call<UserDetails> getLogin(@Header("Token") String apiKey, @Query("email") String email, @Query("password") String pass);

    @GET("UserRegister/GetUserDetails")
    Call<UserDetails> getUserDetails(@Header("Token") String apiKey, @Query("UserID") String user_id, @Query("email") String email);

    @GET("Register/ForgotPassword")
    Call<ForgotPasswordResponse> getForgotPassword(@Header("Token") String apiKey, @Query("orgID") String org_id, @Query("email") String email, @Query("isEndUser") String isEndUser);

    @Headers("Content-type: application/json")
    @POST("Register/ManageUser")
    Call<Response> createAcount(@Header("Token") String apiKey, @Body User UserDetails);


    @GET("Appointment/SearchAppointment")
    Call<SearchAppointment> getSearchAppointment(@Header("Token") String apiKey, @Query("orgID") String orgID, @Query("lastName") String lastName, @Query("dateOfBirth") String dateOfBirth, @Query("confirmationNo") String confirmationNo);


    /*need to be implement and change params*/
    @GET("Register/ResetUserPassword")
    Call<ChangePassword> getChangedPassword(@Header("Token") String apiKey, @Query("email") String email, @Query("oldPassword") String oldPass, @Query("newPassword") String password);

    @DELETE("UserAppointment/CancelAppointment")
    Call<CancelAppointment> delCancelAppointment(@Header("Token") String apiKey,@Query("confirmationNo") String confirmation);


}
