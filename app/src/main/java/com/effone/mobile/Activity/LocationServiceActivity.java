package com.effone.mobile.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.adapter.LocationAdapter;
import com.effone.mobile.adapter.ProviderTypeAdapter;
import com.effone.mobile.adapter.ServiceTypeAdapter;
import com.effone.mobile.adapter.TimeSlotAdapter;
import com.effone.mobile.adapter.TimeZoneAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.model.AppointmentBookingModel;
import com.effone.mobile.model.AppointmentDataTime;
import com.effone.mobile.model.DateTime;
import com.effone.mobile.model.History;
import com.effone.mobile.model.ProviderTable;
import com.effone.mobile.model.Time;
import com.effone.mobile.model.TimeSlotStrings;
import com.effone.mobile.model.TimeZoneDetails;
import com.effone.mobile.realmdb.LocationTable;
import com.effone.mobile.realmdb.ProviderTables;
import com.effone.mobile.realmdb.ProvidersTable;
import com.effone.mobile.realmdb.ServiceProvidedTable;
import com.effone.mobile.realmdb.ServiceTable;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationServiceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner mLvServiceType,mSpProviderType;
    private TextView mTvTitle,mTvProvider;
    private Button mTvBookAppoin;
    private ServiceTypeAdapter mServiceTypeAdapter;
    private AppCompatSpinner mSpinner,mTimeZone;
    private int countOfServiceType=0;
    private Realm mRealm;
    private LinearLayout mTvEmptyView;
    private String appointment_id=""+0,service_ID,mShTimeZone;
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

    private  TextView mTvDateOfSlots;
    private ProgressDialog mCommonProgressDialog;
    private String mSechLocationId;
    private String mScheduledDate;
    private Calendar mCalendar;
    String[] timedate;

    String formatedDate;
    private History mAppointmentDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_service2);
        ResvUtils.enableHomeButton(this);
        myCalendar = Calendar.getInstance();
        appointment_id= getIntent().getStringExtra("id");
        service_ID= getIntent().getStringExtra("service_id");
        mShTimeZone=getIntent().getStringExtra("timeZone");
        mSechLocationId=getIntent().getStringExtra("location_id");
        mScheduledDate=getIntent().getStringExtra("date");

        mAppointmentDateTime = (History) getIntent().getSerializableExtra("selectedItem");
        if(mScheduledDate!=null) {
            timedate = mScheduledDate.split(",");
            String splitedDate = timedate[1].replace(".", "").trim() +  timedate[2].substring(0, 5);
            formatedDate = ResvUtils.parseDateToddMMyyyy(splitedDate, "MMM dd yyyy", "MM/dd/yyyy");
        }

        if(appointment_id == null)
            appointment_id = ""+0;

        mIvBackBtn=(ImageView)findViewById(R.id.iv_back_btn);
        mTvEmptyView=(LinearLayout) findViewById(R.id.tv_empty_view);
        mIvBackBtn.setOnClickListener(this);
        mLvServiceType=(Spinner)findViewById(R.id.lv_service_type);
        mSpProviderType=(Spinner)findViewById(R.id.sp_provider);
        mTvProvider=(TextView)findViewById(R.id.tv_provider);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvBookAppoin=(Button) findViewById(R.id.tv_book_appointment);
        mTvBookAppoin.setOnClickListener(this);
        mTvBookAppoin.setTransformationMethod(null);
        mTvTitle.setText(getString(R.string.service_type));
        mSpinner=(AppCompatSpinner)findViewById(R.id.sp_location);
        mTimeZone=(AppCompatSpinner)findViewById(R.id.sp_timeZone);

       /* mLvServiceType.setOnItemClickListener(this);*/
        mRealm= Realm.getDefaultInstance();
        getLOcationAndServiceFromRealm();
        mTvDateOfSlots=(TextView)findViewById(R.id.date);
        mGvTimeSlots = (GridView) findViewById(R.id.gv_timeSlots);
        mGvTimeSlots.setOnItemClickListener(this);
    }
    ServiceTable mServiceTable;
    ServiceTable getmServiceTable;
    LocationTable mLocationTable;
    TimeZoneDetails mTimeZoneDetails;
    private void getLOcationAndServiceFromRealm() {

        if(mSechLocationId==null||mSechLocationId.equals("")){
            mSpinner.setAdapter(new LocationAdapter(this,mRealm.where(LocationTable.class).findAll()));
        }else {
            LocationTable locationTable = mRealm.where(LocationTable.class).equalTo("LocID", Integer.parseInt(mSechLocationId)).findFirst();
            RealmResults<LocationTable> locationTables=mRealm.where(LocationTable.class).findAll();
            mSpinner.setAdapter(new LocationAdapter(this, locationTables ));
            int positio=locationTables.indexOf(locationTable);
            mSpinner.setSelection(positio);
        }

        if(mShTimeZone==null||mShTimeZone.equals("")) {
            TimeZone tz = TimeZone.getDefault();
            TimeZoneDetails defaultTimeZone = mRealm.where(TimeZoneDetails.class).equalTo("StandardName", tz.getDisplayName()).findFirst();
            RealmResults<TimeZoneDetails> timeZoneDetailses = mRealm.where(TimeZoneDetails.class).findAll().sort("StandardName", Sort.ASCENDING);
            int position = timeZoneDetailses.indexOf(defaultTimeZone);
            mTimeZone.setAdapter(new TimeZoneAdapter(this, timeZoneDetailses));
            mTimeZone.setSelection(position);
        }
        else {
            TimeZoneDetails defaultTimeZone = mRealm.where(TimeZoneDetails.class).equalTo("StandardName", mShTimeZone).findFirst();
            RealmResults<TimeZoneDetails> timeZoneDetailses = mRealm.where(TimeZoneDetails.class).findAll().sort("StandardName", Sort.ASCENDING);
            int position = timeZoneDetailses.indexOf(defaultTimeZone);
            mTimeZone.setAdapter(new TimeZoneAdapter(this, timeZoneDetailses));
            mTimeZone.setSelection(position);
        }
        mSpinner.setOnItemSelectedListener(this);
        mTimeZone.setOnItemSelectedListener(this);
        mLvServiceType.setOnItemSelectedListener(this);
        if(service_ID==null||service_ID.equals("")) {
            RealmList<ServiceTable> serviceTables = new RealmList<ServiceTable>();
            serviceTables.addAll(mRealm.where(ServiceTable.class).findAll());
            mLvServiceType.setAdapter(new ServiceTypeAdapter(this, serviceTables));
            getmServiceTable = (ServiceTable) mLvServiceType.getItemAtPosition(0);
        }
        else{
            RealmList<ServiceTable> serviceTables = new RealmList<ServiceTable>();
            ServiceTable serviceTable=mRealm.where(ServiceTable.class).equalTo("ServiceID", service_ID).findFirst();
            serviceTables.addAll(mRealm.where(ServiceTable.class).findAll());
            mLvServiceType.setAdapter(new ServiceTypeAdapter(this, serviceTables));
            mLvServiceType.setSelection(serviceTables.indexOf(serviceTable));
            mLvServiceType.setEnabled(false);
            getmServiceTable = (ServiceTable) mLvServiceType.getItemAtPosition(serviceTables.indexOf(serviceTable));
        }

        showingDataInProviders();
        getingDataForProvider(mRealm.where(ProviderTable.class).findAll().size());
        declarations();

    }
    ProviderTable mProviderTable;
    private void showingDataInProviders() {
        RealmList<ProviderTable> providerTable = new RealmList<ProviderTable>();
        providerTable.addAll(mRealm.where(ProviderTable.class).findAll());
        mSpProviderType.setAdapter(new ProviderTypeAdapter(this, providerTable));
       mProviderTable = (ProviderTable) mSpProviderType.getItemAtPosition(0);
    }

    private void getingDataForProvider(int providerCount) {

        if(providerCount== 0){
            mTvProvider.setVisibility(View.GONE);
            mSpProviderType.setVisibility(View.GONE);
        }else{
            mTvProvider.setVisibility(View.VISIBLE);
            mSpProviderType.setVisibility(View.VISIBLE);

        }
    }


    private void settingDataIntoGrid(final String Date, String locationTable, String serviceTable, String timezone, int providerId) {
        timeSlotStrings=null;
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

        Call<DateTime> call = apiService.getDateTimeSlots(getString(R.string.token),getString(R.string.org_id),locationTable,serviceTable,Date,timezone, 0);
        call.enqueue(new Callback<DateTime>() {
            @Override
            public void onResponse(Call<DateTime> call, Response<DateTime> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                try {
                    response.raw().request().url();
                    String url=response.raw().request().url().toString();
                    movies = response.body().getResult();
                    if (movies.size() > 0 && movies.get(0).getTimeSlotStrings().size() != 0) {
                        if(movies.get(0).getTimeSlotStrings().size() != 0) {
                            adapter = new TimeSlotAdapter(LocationServiceActivity.this, movies.get(0).getTimeSlotStrings());
                            mGvTimeSlots.setAdapter(adapter);
                            mTvEmptyView.setVisibility(View.GONE);
                            mGvTimeSlots.setVisibility(View.VISIBLE);
                        }else{
                            emptyListview(Date);
                        }
                       /* changeButtonTitle(false);*/

                    } else {
                        emptyListview(Date);
                      //  Toast.makeText(LocationServiceActivity.this, "No Time SLots.", Toast.LENGTH_SHORT).show();
                      //  changeButtonTitle(true);

                    }
                }catch (Exception e) {
                  //  Toast.makeText(LocationServiceActivity.this, "No Time SLots", Toast.LENGTH_SHORT).show();
                    emptyListview(Date);
/*                    changeButtonTitle(true);*/

                }
            }

            @Override
            public void onFailure(Call<DateTime>call, Throwable t) {
                // Log error here since request failed
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                emptyListview(Date);
                Log.e(TAG, t.toString());
            }
        });
    }

    private void emptyListview(String date) {
        mGvTimeSlots.setAdapter(null);
        mGvTimeSlots.setVisibility(View.GONE);
        mTvEmptyView.setVisibility(View.VISIBLE);
        mTvDateOfSlots.setText(ResvUtils.parseDateToddMMyyyy(date,"MM/dd/yyyy","MMM dd, yyyy"));
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
        final String formattedDate = df.format(myCalendar.getTime());
        mEtDate = (EditText) findViewById(R.id.et_date);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        boolean isFromedit=getIntent().getBooleanExtra("check",false);
        if(isFromedit){
            mTvTitle.setText(getString(R.string.edit_appointment));
        }
        else {
            mTvTitle.setText(getString(R.string.booking_app));
        }


       /* mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postingData();
           //     openActivity(LocationServiceActivity.this, AppointmentAcknowledgementActivity.class);
            }
        });*/
       if(mScheduledDate!=null){
           mEtDate.setText(formatedDate);
       }
       else{
           mEtDate.setText(formattedDate);
       }


        mEtDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                DatePickerDialog pickerDialog = new DatePickerDialog(LocationServiceActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
                DatePicker datePicker = pickerDialog.getDatePicker();
                if(mScheduledDate!=null) {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date updatedDate = null;
                    try {
                        updatedDate = inputFormat.parse(formatedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    myCalendar.setTime(updatedDate);
                    datePicker.updateDate(myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                }
                datePicker.setMinDate(System.currentTimeMillis()-1000);
                datePicker.setMaxDate(System.currentTimeMillis() + (long) (5.256e+9));
                pickerDialog.setCancelable(false);
            }
        });
        //settingDataIntoGrid(formattedDate,""+mLocationTable.getLocID(),getmServiceTable.getServiceID(),mTimeZoneDetails.getStandardName());
    }
        private  Boolean editAppointment=false;
    private void postingData(String locationTable, String serviceTable, String timeZoneTable, int providerTable) {
        if(timeSlotStrings != null) {
            AppointmentBookingModel body = new AppointmentBookingModel();

            if(mAppointmentDateTime!= null){
                body.setFirstName(mAppointmentDateTime.getFirstName());
                body.setLastName(mAppointmentDateTime.getLastName());
                body.setDateOfBirth(mAppointmentDateTime.getDateOfBirth());
                body.setGender(mAppointmentDateTime.getGender());
                body.setPhone(mAppointmentDateTime.getPhone());
                body.setTitle(mAppointmentDateTime.getTitle());
                editAppointment=true;

            }

            body.setAppointmentID(0);
            body.setLocID("" + locationTable);
            body.setProviderID(""+providerTable);
            if(AppPreferene.with(LocationServiceActivity.this).getUserId().equals(""))
                body.setUserID("0");
            else {
                body.setUserID(AppPreferene.with(LocationServiceActivity.this).getUserId());
            }
            body.setConfirmationNo(Integer.parseInt(appointment_id));
            body.setAppointmentTypeRefID(27);

            body.setServiceID("" + serviceTable);
            body.setStartTime("" + timeSlotStrings.getStartTime());
            body.setEndTime("" + timeSlotStrings.getEndTime());
            body.setScheduledTimeZone("" + timeZoneTable);

            body.setSendEmailReminder(0);
            body.setSendTextReminder(0);
            body.setAdditionalEmail("");
            body.setIsLoggedIn(0);
            body.setIsCheckedIn(0);
            body.setIsCancelled(0);
            body.setCancelTypeRefID("");
            body.setCancelledBy("");
            body.setIsAssigned(0);
            body.setAssignedTo("");
            body.setAuditID(""+3);
            body.setOrgID(""+getString(R.string.org_id));

            Intent intent =new Intent(this,RegisterActivity.class);
            intent.putExtra("appointment_details",body);
            intent.putExtra("editAppointment",editAppointment);
            startActivity(intent);
          /*  if (!body.getUserID().equals("0")) {

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
                                ResvUtils.createErrorAlert(LocationServiceActivity.this, getString(R.string.error), "Appointment has been Booked " );
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

            }*/
        }else{

            ResvUtils.createOKAlert(this, getResources().getString(R.string.time_slot_validation), getString(R.string.select_time_slot));

        }
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEtDate.setText(sdf.format(myCalendar.getTime()));
        settingDataIntoGrid(mEtDate.getText().toString(),""+mLocationTable.getLocID(),getmServiceTable.getServiceID(),mTimeZoneDetails.getStandardName(), mProviderTable.getUserID());
    }
    TimeSlotStrings timeSlotStrings;
    TextView GridViewItems,BackSelectedItem;
    int backposition = -1;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        timeSlotStrings=(TimeSlotStrings)adapterView.getItemAtPosition(position);
        if(adapter!=null&&adapter.getCount()>0){
            adapter.setSelectedPosition(position);
            adapter.notifyDataSetChanged();
        }

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
    protected void onStart() {
        super.onStart();
        if (!ResvUtils.Operations.isOnline(this)) {
            ResvUtils.Operations.showNoNetworkActivity(this);
        }
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back_btn){
            finish();
        }
        if(view.getId()==R.id.tv_book_appointment) {
 /*           if(isBtnTitleChanged){
                Log.e("LocaitonService","update gird");
                settingDataIntoGrid(mEtDate.getText().toString(),""+mLocationTable.getLocID(),getmServiceTable.getServiceID(),mTimeZoneDetails.getStandardName());
               *//* changeButtonTitle(false);*//*
            }else{
                Log.e("LocaitonService","post");*/

            postingData("" + mLocationTable.getLocID(), getmServiceTable.getServiceID(), mTimeZoneDetails.getStandardName(),mProviderTable.getUserID());



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
     /*       changeButtonTitle(true);*/

        }else  if(spinner.getId() == R.id.sp_timeZone) {
            mTimeZoneDetails=(TimeZoneDetails)mTimeZone.getSelectedItem();
       /*     changeButtonTitle(true);*/
        }else if(spinner.getId() == R.id.lv_service_type){
            getmServiceTable=(ServiceTable)mLvServiceType.getSelectedItem();
            setProviderList(getmServiceTable.getServiceID());
        /*    changeButtonTitle(true);*/
        }else if(spinner.getId() == R.id.sp_provider){
            mProviderTable = (ProviderTable) mSpProviderType.getSelectedItem();
        /*    changeButtonTitle(true);*/

        }


        if(mLocationTable!= null && getmServiceTable != null && mTimeZoneDetails != null && mProviderTable != null ){
            settingDataIntoGrid(mEtDate.getText().toString().trim(),""+mLocationTable.getLocID(),getmServiceTable.getServiceID(),mTimeZoneDetails.getStandardName(),mProviderTable.getUserID());
        }

    }

    private void setProviderList(String serviceID) {
        RealmList<ProviderTable> providerTable=new RealmList<>();
        ServiceTable serviceById = mRealm.where(ServiceTable.class).equalTo("ServiceID", serviceID).findFirst();
        RealmList<ProvidersTable> providersByServiceId=serviceById.getProviders();
        for (ProvidersTable serviceProvided:providersByServiceId) {
            providerTable.add( mRealm.where(ProviderTable.class).equalTo("UserID", serviceProvided.getUserID()).findFirst());
        }
        mSpProviderType.setAdapter(new ProviderTypeAdapter(this,providerTable));
    }

    private Boolean  isBtnTitleChanged;
    private  int initalizedCount=0;
    private void changeButtonTitle(boolean b) {
        if(b){
            mTvBookAppoin.setText(getString(R.string.getDetails));
            //removeTimeSlots();
            isBtnTitleChanged=b;
            initalizedCount=+1;
             removeTimeSlots();
        }else {
            mTvBookAppoin.setText(getString(R.string.booking_app));
            isBtnTitleChanged=b;
        }
    }

    private void removeTimeSlots() {

       // changeButtonTitle(true);
        mGvTimeSlots.setAdapter(null);
        mGvTimeSlots.setVisibility(View.GONE);
        mTvEmptyView.setVisibility(View.VISIBLE);
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