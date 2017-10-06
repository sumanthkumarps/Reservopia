package com.effone.mobile.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.mobile.R;
import com.effone.mobile.adapter.LocationAdapter;
import com.effone.mobile.adapter.ServiceTypeAdapter;
import com.effone.mobile.adapter.TimeSlotAdapter;
import com.effone.mobile.adapter.TimeZoneAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.model.AppointmentBookingModel;
import com.effone.mobile.model.AppointmentDataTime;
import com.effone.mobile.model.DateTime;
import com.effone.mobile.model.Time;
import com.effone.mobile.model.TimeSlotStrings;
import com.effone.mobile.model.TimeZoneDetails;
import com.effone.mobile.realmdb.LocationTable;
import com.effone.mobile.realmdb.ServiceProvidedTable;
import com.effone.mobile.realmdb.ServiceTable;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationServiceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner mLvServiceType;
    private TextView mTvTitle,mTvBookAppoin;
    private ServiceTypeAdapter mServiceTypeAdapter;
    private AppCompatSpinner mSpinner,mTimeZone;
    private int countOfServiceType=0;
    private Realm mRealm;
    private String appointment_id=""+0,service_ID;
    private ImageView mIvBackBtn;



    //adding newly dude to merge of 2 screens

    private static final String TAG = "";
    private String[] arraySpinner;
    private Calendar myCalendar;
    private EditText mEtDate;
    private GridView mGvTimeSlots;
    private TextView mTvSubmit;
    private ArrayList<Time> movies;
    private TimeSlotAdapter adapter;
    private String serviceTable;

    private ProgressDialog mCommonProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_service2);
        myCalendar = Calendar.getInstance();
        appointment_id= getIntent().getStringExtra("id");
        service_ID= getIntent().getStringExtra("service_id");
        if(appointment_id == null)
            appointment_id = ""+0;

        mIvBackBtn=(ImageView)findViewById(R.id.iv_back_btn);
        mIvBackBtn.setOnClickListener(this);
        mLvServiceType=(Spinner)findViewById(R.id.lv_service_type);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvBookAppoin=(TextView)findViewById(R.id.tv_book_appointment);
        mTvBookAppoin.setOnClickListener(this);
        mTvTitle.setText(getString(R.string.service_type));
        mSpinner=(AppCompatSpinner)findViewById(R.id.sp_location);
        mTimeZone=(AppCompatSpinner)findViewById(R.id.sp_timeZone);
       /* mLvServiceType.setOnItemClickListener(this);*/
        mRealm= Realm.getDefaultInstance();
        getLOcationAndServiceFromRealm();

        mGvTimeSlots = (GridView) findViewById(R.id.gv_timeSlots);
        mGvTimeSlots.setOnItemClickListener(this);



    }
    ServiceTable mServiceTable;
    ServiceTable getmServiceTable;
    LocationTable mLocationTable;
    TimeZoneDetails mTimeZoneDetails;
    private void getLOcationAndServiceFromRealm() {
        mSpinner.setAdapter(new LocationAdapter(this,mRealm.where(LocationTable.class).findAll()));
        mLocationTable=(LocationTable)mSpinner.getItemAtPosition(0);

        mTimeZone.setAdapter(new TimeZoneAdapter(this,mRealm.where(TimeZoneDetails.class).findAll().sort("StandardName", Sort.ASCENDING)));
        mLocationTable=(LocationTable)mSpinner.getItemAtPosition(0);
        mTimeZoneDetails=(TimeZoneDetails)mTimeZone.getItemAtPosition(0);
        mSpinner.setOnItemSelectedListener(this);
        RealmList<ServiceTable> serviceTables=new RealmList<ServiceTable>();
        serviceTables.addAll(mRealm.where(ServiceTable.class).findAll());
        mLvServiceType.setAdapter(new ServiceTypeAdapter(this,serviceTables));
        getmServiceTable=(ServiceTable)mLvServiceType.getItemAtPosition(0);
        declarations();
        changeButtonTitle(false);
    }


    private void settingDataIntoGrid(String Date,String locationTable,String serviceTable, String timezone) {
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

        Call<DateTime> call = apiService.getDateTimeSlots(getString(R.string.token),locationTable,serviceTable,Date,timezone);
        call.enqueue(new Callback<DateTime>() {
            @Override
            public void onResponse(Call<DateTime> call, Response<DateTime> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                try {
                    movies = response.body().getResult();
                    if (movies.size() > 0 && movies.get(0).getTimeSlotStrings().size() != 0) {
                        adapter = new TimeSlotAdapter(LocationServiceActivity.this, movies.get(1).getTimeSlotStrings());
                        mGvTimeSlots.setAdapter(adapter);
                    } else {
                        Toast.makeText(LocationServiceActivity.this, "No Time SLots", Toast.LENGTH_SHORT).show();
                        TextView emptyView = (TextView)findViewById(android.R.id.empty);
                        emptyView.setText("No Time Slots Found");
                        mGvTimeSlots.setEmptyView(emptyView);
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
       /* mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postingData();
           //     openActivity(LocationServiceActivity.this, AppointmentAcknowledgementActivity.class);
            }
        });*/
        mEtDate.setText(formattedDate);
        settingDataIntoGrid(formattedDate,""+mLocationTable.getLocID(),getmServiceTable.getServiceID(),mTimeZoneDetails.getStandardName());
        mEtDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(LocationServiceActivity.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void postingData(String locationTable, String serviceTable,String timeZoneTable) {
        if(timeSlotStrings != null) {
            AppointmentBookingModel body = new AppointmentBookingModel();
            body.setAppointmentID(appointment_id);
            body.setLocID("" + locationTable);
            if(AppPreferene.with(LocationServiceActivity.this).getUserId().equals(""))
                body.setUserID("0");
            else
                body.setUserID(AppPreferene.with(LocationServiceActivity.this).getUserId());
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
                Call<com.effone.mobile.model.Response> response = apiService.createAppointment(getString(R.string.token), body);
                response.enqueue(new Callback<com.effone.mobile.model.Response>() {
                    @Override
                    public void onResponse(Call<com.effone.mobile.model.Response> call, Response<com.effone.mobile.model.Response> rawResponse) {
                        try {
                            if(!rawResponse.body().getResult().getOperation().equals("501")) {
                                if (rawResponse.body().getResult().getID() != null) {
// Toast.makeText(LocationServiceActivity.this, "done" + rawResponse.body().getResult().getID(), Toast.LENGTH_SHORT).show();
                                    //get your response....
                                    //Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + rawResponse.body());
                                    Intent intent = new Intent(LocationServiceActivity.this, AppointmentAcknowledgementActivity.class);
                                    intent.putExtra(getString(R.string.confirmation_no), rawResponse.body().getResult().getID());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    ResvUtils.createErrorAlert(LocationServiceActivity.this, getString(R.string.error), "" + rawResponse.message());
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 5000);
                                }
                            }else{
                                Toast.makeText(LocationServiceActivity.this,"Appointment has been Booked ",Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.effone.mobile.model.Response> call, Throwable throwable) {
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
       changeButtonTitle(true);
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
        if(view.getId()==R.id.tv_book_appointment){
            if(isBtnTitleChanged){
                Log.e("LocaitonService","update gird");
                settingDataIntoGrid(mEtDate.getText().toString(),""+mLocationTable.getLocID(),getmServiceTable.getServiceID(),mTimeZoneDetails.getStandardName());
                changeButtonTitle(false);
            }else{
                Log.e("LocaitonService","post");

                postingData(""+mLocationTable.getLocID(),getmServiceTable.getServiceID(),mTimeZoneDetails.getStandardName());
            }


   /*         mServiceTable=(ServiceTable)mLvServiceType.getItemAtPosition(0);


            Intent inte=new Intent(this,LocationServiceActivity.class);
            inte.putExtra("id",appointment_id);
            inte.putExtra("Location",""+mLocationTable.getLocID());
            inte.putExtra("Service",mServiceTable.getServiceID());
            inte.putExtra("TimeZone",mTimeZoneDetails.getStandardName());

            startActivity(inte);*/
        }else if(view.getId() == R.id.iv_back_btn){
            finish();
        }
    }

    public List<AppointmentDataTime> jsonObject(String response) {
        List<AppointmentDataTime> listItems=new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<AppointmentDataTime>>(){}.getType();
        listItems = gson.fromJson(response, type);
        return listItems;
    }

/*    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent inte=new Intent(this,LocationServiceActivity.class);
        startActivity(inte);
    }*/


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.sp_location) {
            mLocationTable = (LocationTable) mSpinner.getSelectedItem();
            setServiceList(mLocationTable.getLocID());
            changeButtonTitle(true);

        }else  if(spinner.getId() == R.id.sp_timeZone) {
            mTimeZoneDetails=(TimeZoneDetails)mTimeZone.getSelectedItem();
            changeButtonTitle(true);
        }else if(spinner.getId() == R.id.lv_service_type){
            getmServiceTable=(ServiceTable)mLvServiceType.getSelectedItem();
            changeButtonTitle(true);
        }


    }

   private Boolean  isBtnTitleChanged;
    private void changeButtonTitle(boolean b) {
        if(b){
            mTvBookAppoin.setText(getString(R.string.getDetails));
            isBtnTitleChanged=b;

        }else {
            mTvBookAppoin.setText(getString(R.string.booking_app));
            isBtnTitleChanged=b;
        }
    }


    private void setServiceList(int locID) {
        LocationTable locationById = mRealm.where(LocationTable.class).equalTo("LocID", locID).findFirst();
        RealmList<ServiceProvidedTable>  servicesByLocId=locationById.getServiceProvidedTables();
        RealmList<ServiceTable> serviceTable=new RealmList<ServiceTable>();
        for (ServiceProvidedTable serviceProvided:servicesByLocId) {
            serviceTable.add( mRealm.where(ServiceTable.class).equalTo("ServiceID", serviceProvided.getServiceID()).findFirst());
        }
        mLvServiceType.setAdapter(new ServiceTypeAdapter(this,serviceTable));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mLocationTable=(LocationTable)mSpinner.getSelectedItem();
    }
}