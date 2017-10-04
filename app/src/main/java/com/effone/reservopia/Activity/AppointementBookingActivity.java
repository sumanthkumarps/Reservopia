package com.effone.reservopia.Activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.reservopia.R;
import com.effone.reservopia.adapter.TimeSlotAdapter;
import com.effone.reservopia.common.AppPreferene;
import com.effone.reservopia.common.ResvUtils;
import com.effone.reservopia.model.AppointmentBookingModel;
import com.effone.reservopia.model.DateTime;
import com.effone.reservopia.model.Time;
import com.effone.reservopia.model.TimeSlotStrings;
import com.effone.reservopia.realmdb.LocationTable;
import com.effone.reservopia.realmdb.ServiceTable;
import com.effone.reservopia.rest.ApiClient;
import com.effone.reservopia.rest.ApiInterface;
import com.google.gson.Gson;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.effone.reservopia.R.string.service;

public class AppointementBookingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TAG = "";
    private String[] arraySpinner;
    private Calendar myCalendar;
    private EditText mEtDate;
    private GridView mGvTimeSlots;
    private TextView mTvSubmit;
    private TextView mTvTitle;
    private ArrayList<Time> movies;
    private  TimeSlotAdapter adapter;
    private String locationTable,timeZoneTable,appointment_id;
    private String serviceTable;
    private ImageView mIvBackBtn;
    private ProgressDialog mCommonProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement_booking);
       // mAppPreferences=new AppPreferences(this);
        myCalendar = Calendar.getInstance();
        mIvBackBtn=(ImageView)findViewById(R.id.iv_back_btn);
        mIvBackBtn.setOnClickListener(this);

        appointment_id= getIntent().getStringExtra("id");
        locationTable= getIntent().getStringExtra("Location");
        serviceTable=getIntent().getStringExtra("Service");
        timeZoneTable=getIntent().getStringExtra("TimeZone");
        Toast.makeText(this,"Appointment_id=="+ appointment_id+"LOC=="+locationTable+"Service =="+serviceTable,Toast.LENGTH_SHORT).show();
        mGvTimeSlots = (GridView) findViewById(R.id.gv_timeSlots);
        mGvTimeSlots.setOnItemClickListener(this);
        declarations();
    }

    private void settingDataIntoGrid(String Date) {
        if (mCommonProgressDialog == null) {
            mCommonProgressDialog = ResvUtils.createProgressDialog(this);
            mCommonProgressDialog.show();
            mCommonProgressDialog.setMessage("Please wait...");
            mCommonProgressDialog.setCancelable(false);
        } else {
            mCommonProgressDialog.show();
        }
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DateTime> call = apiService.getDateTimeSlots(getString(R.string.token),locationTable,serviceTable,Date,"India Standard Time");
        call.enqueue(new Callback<DateTime>() {
            @Override
            public void onResponse(Call<DateTime> call, Response<DateTime> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                try {
                    movies = response.body().getResult();
                    if (movies.size() > 0 && movies.get(0).getTimeSlotStrings().size() != 0) {
                        adapter = new TimeSlotAdapter(AppointementBookingActivity.this, movies.get(1).getTimeSlotStrings());
                        mGvTimeSlots.setAdapter(adapter);
                    } else {
                        Toast.makeText(AppointementBookingActivity.this, "No Time SLots", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    Log.e(TAG, e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<DateTime>call, Throwable t) {
                // Log error here since request failed
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Log.e(TAG, t.toString());
            }
        });
    }


    private void declarations() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(myCalendar.getTime());
        mEtDate = (EditText) findViewById(R.id.et_date);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.booking_app));
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postingData();
           //     openActivity(AppointementBookingActivity.this, AppointmentAcknowledgementActivity.class);
            }
        });
        mEtDate.setText(formattedDate);
        settingDataIntoGrid(formattedDate);
        mEtDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(AppointementBookingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void postingData() {
        if(timeSlotStrings != null) {
            AppointmentBookingModel body = new AppointmentBookingModel();
            body.setAppointmentID(appointment_id);
            body.setLocID("" + locationTable);
            if(AppPreferene.with(AppointementBookingActivity.this).getUserId().equals(""))
            body.setUserID("0");
            else
             body.setUserID(AppPreferene.with(AppointementBookingActivity.this).getUserId());
            body.setConfirmationNo("0");
            body.setAppointmentTypeRefID("27");
            body.setServiceID("" + serviceTable);
            body.setStartTime("" + timeSlotStrings.getStartTime());
            body.setEndTime("" + timeSlotStrings.getEndTime());
            body.setScheduledTimeZone("" + timeZoneTable);
            body.setSendEmailReminder("0");
            body.setSendTextReminder("0");
            body.setAdditionalEmail("");
            body.setIsLoggedIn("0");
            body.setIsCheckedIn("0");
            body.setIsCancelled("0");
            body.setCancelTypeRefID("null");
            body.setCancelledBy("null");
            body.setIsAssigned("0");
            body.setAssignedTo("null");
            body.setAuditID("5");

            if (!body.getUserID().equals("0")) {

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<com.effone.reservopia.model.Response> response = apiService.createAppointment(getString(R.string.token), body);
                response.enqueue(new Callback<com.effone.reservopia.model.Response>() {
                    @Override
                    public void onResponse(Call<com.effone.reservopia.model.Response> call, Response<com.effone.reservopia.model.Response> rawResponse) {
                        try {
                            if(!rawResponse.body().getResult().getOperation().equals("501")) {
                                if (rawResponse.body().getResult().getID() != null) {
// Toast.makeText(AppointementBookingActivity.this, "done" + rawResponse.body().getResult().getID(), Toast.LENGTH_SHORT).show();
                                    //get your response....
                                    //Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + rawResponse.body());
                                    Intent intent = new Intent(AppointementBookingActivity.this, AppointmentAcknowledgementActivity.class);
                                    intent.putExtra(getString(R.string.confirmation_no), rawResponse.body().getResult().getID());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    ResvUtils.createErrorAlert(AppointementBookingActivity.this, getString(R.string.error), "" + rawResponse.message());
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 5000);
                                }
                            }else{
                                Toast.makeText(AppointementBookingActivity.this,"Appointment has been Booked ",Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.effone.reservopia.model.Response> call, Throwable throwable) {
                        // other stuff...
                        Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + throwable.toString());
                    }
                });
            }else {
                Intent intent =new Intent(this,RegisterActivity.class);
                intent.putExtra("appointment_details",body);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this,"Select one time Slots",Toast.LENGTH_SHORT).show();
        }
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEtDate.setText(sdf.format(myCalendar.getTime()));
        settingDataIntoGrid(sdf.format(myCalendar.getTime()));
    }
    TimeSlotStrings timeSlotStrings;
    TextView GridViewItems,BackSelectedItem;
    int backposition = -1;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        timeSlotStrings=(TimeSlotStrings)adapterView.getItemAtPosition(position);
       // call it here
        /*if(mGvTimeSlots.isItemChecked(position)) {
            view = mGvTimeSlots.getChildAt(position);
            view.setBackgroundColor(Color.WHITE);
        }else {
            view = mGvTimeSlots.getChildAt(position);
            view.setBackgroundColor(Color.RED); //the color code is the background color of GridView
        }
        adapter.notifyDataSetChanged();*/
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back_btn){
            finish();
        }
    }
}
