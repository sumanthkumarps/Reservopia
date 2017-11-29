package com.effone.mobile.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.effone.mobile.model.TimeSlots;
import com.effone.mobile.model.TimeSlotsStartEnd;
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
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

    private Spinner mLvServiceType, mSpProviderType;
    private TextView mTvTitle, mTvProvider;
    private Button mTvBookAppoin;
    private ServiceTypeAdapter mServiceTypeAdapter;
    private AppCompatSpinner mSpinner, mTimeZone;
    private int countOfServiceType = 0;
    private Realm mRealm;
    private LinearLayout mTvEmptyView;
    private String appointment_id = "" + 0, service_ID, mShTimeZone;
    private ImageView mIvBackBtn;


    //adding newly dude to merge of 2 screens

    private static final String TAG = "";
    private String[] arraySpinner;
    private Calendar myCalendar;
    private EditText mEtDate;
    private GridView mGvTimeSlots;
    private TextView mTvSubmit;
    private ArrayList<Time> movies,popupTimeSlots;
    private TimeSlotAdapter adapter;
    private String serviceTable;

    private TextView mTvDateOfSlots;
    private ProgressDialog mCommonProgressDialog;
    private String mSechLocationId;
    private String mScheduledDate;
    private Calendar mCalendar;
    String[] timedate;
    private  int duration,maxDuration;
    String formatedDate;
    private History mAppointmentDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_service2);
        ResvUtils.enableHomeButton(this);
        myCalendar = Calendar.getInstance();
        appointment_id = getIntent().getStringExtra("id");
        service_ID = getIntent().getStringExtra("service_id");
        mShTimeZone = getIntent().getStringExtra("timeZone");
        mSechLocationId = getIntent().getStringExtra("location_id");
        mScheduledDate = getIntent().getStringExtra("date");

        mAppointmentDateTime = (History) getIntent().getSerializableExtra("selectedItem");
        if (mScheduledDate != null) {
            timedate = mScheduledDate.split(",");
            String splitedDate = timedate[1].replace(".", "").trim() + timedate[2].substring(0, 5);
            formatedDate = ResvUtils.parseDateToddMMyyyy(splitedDate, "MMM dd yyyy", "MM/dd/yyyy");
        }

        if (appointment_id == null)
            appointment_id = "" + 0;

        mIvBackBtn = (ImageView) findViewById(R.id.iv_back_btn);
        mTvEmptyView = (LinearLayout) findViewById(R.id.tv_empty_view);
        mIvBackBtn.setOnClickListener(this);
        mLvServiceType = (Spinner) findViewById(R.id.lv_service_type);
        mSpProviderType = (Spinner) findViewById(R.id.sp_provider);
        mTvProvider = (TextView) findViewById(R.id.tv_provider);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvBookAppoin = (Button) findViewById(R.id.tv_book_appointment);
        mTvBookAppoin.setOnClickListener(this);
        mTvBookAppoin.setTransformationMethod(null);
        mTvTitle.setText(getString(R.string.service_type));
        mSpinner = (AppCompatSpinner) findViewById(R.id.sp_location);
        mTimeZone = (AppCompatSpinner) findViewById(R.id.sp_timeZone);

       /* mLvServiceType.setOnItemClickListener(this);*/
        mRealm = Realm.getDefaultInstance();
        getLOcationAndServiceFromRealm();
        mTvDateOfSlots = (TextView) findViewById(R.id.date);
        mGvTimeSlots = (GridView) findViewById(R.id.gv_timeSlots);

        mGvTimeSlots.setFocusable(false);
        mGvTimeSlots.setOnItemClickListener(this);
    }

    ServiceTable mServiceTable;
    ServiceTable getmServiceTable;
    LocationTable mLocationTable;
    TimeZoneDetails mTimeZoneDetails;
    LocationTable locationTable;

    private void getLOcationAndServiceFromRealm() {

        if (mSechLocationId == null || mSechLocationId.equals("")) {
            mSpinner.setAdapter(new LocationAdapter(this, mRealm.where(LocationTable.class).findAll()));
        } else {
            locationTable = mRealm.where(LocationTable.class).equalTo("LocID", Integer.parseInt(mSechLocationId)).findFirst();
            RealmResults<LocationTable> locationTables = mRealm.where(LocationTable.class).findAll();
            mSpinner.setAdapter(new LocationAdapter(this, locationTables));
            int positio = locationTables.indexOf(locationTable);
            mSpinner.setSelection(positio);
        }

        if (mShTimeZone == null || mShTimeZone.equals("")) {
            TimeZone tz = TimeZone.getDefault();
            TimeZoneDetails defaultTimeZone = mRealm.where(TimeZoneDetails.class).equalTo("StandardName", tz.getDisplayName()).findFirst();
            RealmResults<TimeZoneDetails> timeZoneDetailses = mRealm.where(TimeZoneDetails.class).findAll().sort("StandardName", Sort.ASCENDING);
            int position = timeZoneDetailses.indexOf(defaultTimeZone);
            mTimeZone.setAdapter(new TimeZoneAdapter(this, timeZoneDetailses));
            mTimeZone.setSelection(position);
        } else {
            TimeZoneDetails defaultTimeZone = mRealm.where(TimeZoneDetails.class).equalTo("StandardName", mShTimeZone).findFirst();
            RealmResults<TimeZoneDetails> timeZoneDetailses = mRealm.where(TimeZoneDetails.class).findAll().sort("StandardName", Sort.ASCENDING);
            int position = timeZoneDetailses.indexOf(defaultTimeZone);
            mTimeZone.setAdapter(new TimeZoneAdapter(this, timeZoneDetailses));
            mTimeZone.setSelection(position);
        }
        mSpinner.setOnItemSelectedListener(this);
        mTimeZone.setOnItemSelectedListener(this);
        mLvServiceType.setOnItemSelectedListener(this);
        if (service_ID == null || service_ID.equals("")) {
            RealmList<ServiceTable> serviceTables = new RealmList<ServiceTable>();
            serviceTables.addAll(mRealm.where(ServiceTable.class).findAll());
            mLvServiceType.setAdapter(new ServiceTypeAdapter(this, serviceTables));
            getmServiceTable = (ServiceTable) mLvServiceType.getItemAtPosition(0);
        } else {
            RealmList<ServiceTable> serviceTables = new RealmList<ServiceTable>();
            ServiceTable serviceTable = mRealm.where(ServiceTable.class).equalTo("ServiceID", service_ID).findFirst();
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

    private void gettingDurationAndMaxDuration(String loc_id, String service_ID) {
        try {
            ServiceProvidedTable serviceProvidedTable = mRealm.where(ServiceProvidedTable.class).equalTo("ServiceID", service_ID).equalTo("LocID", loc_id).findFirst();
            duration = Integer.parseInt(serviceProvidedTable.getAppointmentDuration());
            if (serviceProvidedTable.getMaxAppointmentDuration() != null) {
                String[] times = serviceProvidedTable.getMaxAppointmentDuration().toString().split(" ");
                maxDuration = Integer.parseInt(times[0]);
            } else maxDuration = 0;
        } catch (Exception e) {
            duration=0;
            maxDuration=0;
        }
    }

    ProviderTable mProviderTable;

    private void showingDataInProviders() {
        RealmList<ProviderTable> providerTable = new RealmList<ProviderTable>();
        providerTable.addAll(mRealm.where(ProviderTable.class).findAll());
        mSpProviderType.setAdapter(new ProviderTypeAdapter(this, providerTable));
        mProviderTable = (ProviderTable) mSpProviderType.getItemAtPosition(0);
    }

    private void getingDataForProvider(int providerCount) {

        if (providerCount == 0) {
            mTvProvider.setVisibility(View.GONE);
            mSpProviderType.setVisibility(View.GONE);
        } else {
            mTvProvider.setVisibility(View.VISIBLE);
            mSpProviderType.setVisibility(View.VISIBLE);

        }
    }


    private void settingDataIntoGrid(final String Date, String locationTable, String serviceTable, String timezone, int providerId) {
        timeSlotStrings = null;
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

        Call<DateTime> call = apiService.getDateTimeSlots(getString(R.string.token), getString(R.string.org_id), locationTable, serviceTable, Date, timezone, 0);
        call.enqueue(new Callback<DateTime>() {
            @Override
            public void onResponse(Call<DateTime> call, Response<DateTime> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                try {

                    response.raw().request().url();
                    String url = response.raw().request().url().toString();
                    movies = response.body().getResult();
                    if (movies.size() > 0 && movies.get(0).getTimeSlotStrings().size() != 0) {
                        if (movies.get(0).getTimeSlotStrings().size() != 0) {
                            adapter = new TimeSlotAdapter(LocationServiceActivity.this, movies.get(0).getTimeSlotStrings());
                            mGvTimeSlots.setAdapter(adapter);
                            mTvEmptyView.setVisibility(View.GONE);
                            mGvTimeSlots.setVisibility(View.VISIBLE);
                        } else {
                            emptyListview(Date);
                        }
                       /* changeButtonTitle(false);*/

                    } else {
                        emptyListview(Date);
                        //  Toast.makeText(LocationServiceActivity.this, "No Time SLots.", Toast.LENGTH_SHORT).show();
                        //  changeButtonTitle(true);

                    }
                } catch (Exception e) {
                    //  Toast.makeText(LocationServiceActivity.this, "No Time SLots", Toast.LENGTH_SHORT).show();
                    emptyListview(Date);
/*                    changeButtonTitle(true);*/

                }
            }

            @Override
            public void onFailure(Call<DateTime> call, Throwable t) {
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
        mTvDateOfSlots.setText(ResvUtils.parseDateToddMMyyyy(date, "MM/dd/yyyy", "MMM dd, yyyy"));
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
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        boolean isFromedit = getIntent().getBooleanExtra("check", false);
        if (isFromedit) {
            mTvTitle.setText(getString(R.string.edit_appointment));
        } else {
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
        if (mScheduledDate != null) {
            mEtDate.setText(formatedDate);
        } else {
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
                if (mScheduledDate != null) {
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
                datePicker.setMinDate(System.currentTimeMillis() - 1000);
                datePicker.setMaxDate(System.currentTimeMillis() + (long) (5.256e+9));
                pickerDialog.setCancelable(false);
            }
        });
        //settingDataIntoGrid(formattedDate,""+mLocationTable.getLocID(),getmServiceTable.getServiceID(),mTimeZoneDetails.getStandardName());
    }

    private Boolean editAppointment = false;

    private void postingData(String locationTable, String serviceTable, String timeZoneTable, int providerTable) {
        if (timeSlotStrings != null) {
            AppointmentBookingModel body = new AppointmentBookingModel();

            if (mAppointmentDateTime != null) {
                body.setFirstName(mAppointmentDateTime.getFirstName());
                body.setLastName(mAppointmentDateTime.getLastName());
                body.setDateOfBirth(mAppointmentDateTime.getDateOfBirth());
                body.setGender(mAppointmentDateTime.getGender());
                body.setPhone(mAppointmentDateTime.getPhone());
                body.setTitle(mAppointmentDateTime.getTitle());
                body.setEmail(mAppointmentDateTime.getEmail());
                body.setUserID(mAppointmentDateTime.getUserID());
                editAppointment = true;

            }

            body.setAppointmentID(0);
            body.setLocID("" + locationTable);
            body.setProviderID("" + providerTable);
            if (AppPreferene.with(LocationServiceActivity.this).getUserId().equals("")) {
                if (!editAppointment)
                    body.setUserID("0");
            } else {
                body.setUserID(AppPreferene.with(LocationServiceActivity.this).getUserId());
            }
            body.setConfirmationNo(Integer.parseInt(appointment_id));
            body.setAppointmentTypeRefID(27);
            body.setServiceID("" + serviceTable);
            body.setStartTime("" + timeSlotStrings.getStartTime());
            if(mTimeSlotStrings==null)
            body.setEndTime("" + timeSlotStrings.getEndTime());
            else
                body.setEndTime("" + mTimeSlotStrings.getStartTime());
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
            body.setAuditID("" + 3);
            body.setOrgID("" + getString(R.string.org_id));
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("appointment_details", body);
            intent.putExtra("editAppointment", editAppointment);
            mTvBookAppoin.setEnabled(true);
            startActivity(intent);

        } else {
            mTvBookAppoin.setEnabled(true);
            ResvUtils.createOKAlert(this, getResources().getString(R.string.time_slot_validation), getString(R.string.select_time_slot));

        }
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEtDate.setText(sdf.format(myCalendar.getTime()));
        settingDataIntoGrid(mEtDate.getText().toString(), "" + mLocationTable.getLocID(), getmServiceTable.getServiceID(), mTimeZoneDetails.getStandardName(), mProviderTable.getUserID());
    }

    TimeSlotStrings timeSlotStrings;
    TimeSlotStrings mTimeSlotStrings;
    TextView GridViewItems, BackSelectedItem;
    int backposition = -1;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        timeSlotStrings = (TimeSlotStrings) adapterView.getItemAtPosition(position);
        if (adapter != null && adapter.getCount() > 0) {
            adapter.setSelectedPosition(position);
            adapter.notifyDataSetChanged();
        }
        popupTimeSlots=new ArrayList<>();
        popupTimeSlots=movies;
       //Toast.makeText(LocationServiceActivity.this, movies.get(0).getTimeSlotStrings().get(position).getEndTime(),Toast.LENGTH_SHORT).show();
            // maxDuration is  not equals to zero then only we will show the popup alert for selection of time interval more than the duration time (selection of multiple time slots based on maxDuration). we are getting maxDuration value from the ServiceProviderTable from the database
        if(maxDuration != 0 && duration != 0) {
            //we are passing the selected item position and Duration and maxDuration
            addingDataIntoArray(position, duration, maxDuration);
        }

    }
/// here we adding the time slots in to new array

    private void addingDataIntoArray(int selectedItemPosition, int Duration,int maxDuration) {


        int index=0;
        try {
            //getting time slots to spinner and we are  passing index values 0
            index = getTimeSlotsForSpinner(index, selectedItemPosition, Duration, maxDuration);

            // showing the alert to the user for selection of multiple timeslots
            showingPopUpAlert(LocationServiceActivity.this,timeSlotStrings.getStartTime());
        }catch (Exception e){
            Log.e("timeSLot",e.getMessage());
        }

    }
   private ArrayList<TimeSlotStrings> timeSlotsStartEnds;

    private int getTimeSlotsForSpinner(int arrayPosition, int selectedItemPosition,int Duration,int maxDuration) {
        if(timeSlotsStartEnds!=null){
            timeSlotsStartEnds=null;
        }
        timeSlotsStartEnds =new ArrayList<TimeSlotStrings>();
        // her we are using for loop and we are declaring a new variable i, and loop start form the seleceted item positon and ends at the timeSlot array size
        //movies is main time slot arrayList of Time Obect
        for (int i = selectedItemPosition; i < movies.get(0).getTimeSlotStrings().size(); i++) {


            if((arrayPosition* Duration) <= (maxDuration-Duration)) {
                if(basedOnPostion(i,arrayPosition))
                arrayPosition++;
                else
                    break;
            }else{
                break;
            }


        }
        return  arrayPosition;
    }

    private boolean basedOnPostion(int index, int arrayPosition) {
        //if arrayPosition variable value=0 means we are inserting data into array directly
        if(arrayPosition == 0) {
            addingDataIntoList(arrayPosition,index);
            return true;
        }
        else{// if the arrayPosition variable value != 0 we are taking the difference between the current timeslot and previous timeslot the differnece matchs the duration then only we are inserting current time slot into the array
            if (gettingDifferencebetweenSlots(movies.get(0).getTimeSlotStrings().get(index - 1).getEndTime().toString(),
                movies.get(0).getTimeSlotStrings().get(index).getEndTime().toString())) {
            addingDataIntoList(arrayPosition,index);
                return true;
            } else
                return false;
        }
    }

    private void addingDataIntoList(int arrayPosition,int indexOfSlot) {
        // Due to the usage of same adpter for Gird and Spinner we are swapping data. end value is assigned to start time and start time is assigned to endTime
        TimeSlotStrings timeSlotsStartEnd=new TimeSlotStrings();
        timeSlotsStartEnd.setEndTime(popupTimeSlots.get(0).getTimeSlotStrings().get(indexOfSlot).getStartTime());
        timeSlotsStartEnd.setStartTime(popupTimeSlots.get(0).getTimeSlotStrings().get(indexOfSlot).getEndTime());
        timeSlotsStartEnds.add(arrayPosition,timeSlotsStartEnd);
    }

    private boolean gettingDifferencebetweenSlots(String start, String end) {
        long diffrence=duration*60*1000;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
        try {
            Date firstDate = dateFormat.parse(start);
            Date secondDate = dateFormat.parse(end);
            long diff=secondDate.getTime()-firstDate.getTime();
            if(diff == diffrence){
                return true;
            }else{
                return false ;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String parseDate(String time){
        String formattedDate;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
        DateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
        try {
            formattedDate = timeFormat.format(dateFormat.parse(time));
        } catch (Exception e){
             formattedDate= time;
        }
        return  formattedDate;
    }

    private void showingPopUpAlert(Context mContext, String startTime) {
        LayoutInflater layoutInflater =
                (LayoutInflater)getBaseContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);

        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        TextView startTimes=(TextView)popupView.findViewById(R.id.start_time);
        TextView msgDate=(TextView)popupView.findViewById(R.id.tv_date);
        msgDate.setText(getString(R.string.confirmation_timeSlot_msg)+" "
                + ResvUtils.parseDateToddMMyyyy(mEtDate.getText().toString(), "MM/dd/yyyy",
                "MMM dd, yyyy"));
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
        DateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
        try {
          String  formattedDate = timeFormat.format(dateFormat.parse(startTime));
            startTimes.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);

        Button btnSave = (Button)popupView.findViewById(R.id.btn_save);
        final Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.popupspinner);


       TimeSlotAdapter  timeSlotAdapter=new TimeSlotAdapter(getBaseContext(),timeSlotsStartEnds);
        popupSpinner.setAdapter(timeSlotAdapter);

     /*  popupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           public void onItemSelected(
                   AdapterView<?> adapterView, View view,
                   int i, long l) {
               mTimeSlotStrings = (TimeSlotStrings) adapterView.getItemAtPosition(i);
               timeSlotStrings.setEndTime(mTimeSlotStrings.getStartTime());
           }

           public void onNothingSelected(
                   AdapterView<?> adapterView) {

           }
       });*/
   /*     TimeSlotAdapter timeSlotAdapter = new TimeSlotAdapter(mContext, movies.get(0).getTimeSlotStrings());
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        popupSpinner.setAdapter(timeSlotAdapter);*/

        btnDismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }});
        btnSave.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                mTimeSlotStrings = (TimeSlotStrings) popupSpinner.getSelectedItem();
             //   timeSlotStrings.setEndTime(mTimeSlotStrings.getEndTime());

                popupWindow.dismiss();

            }});

      popupWindow.showAsDropDown(mLvServiceType, 50, -30);
    }

    private String[] removingNullValuesFromArray(String[] endTime) {
        List<String> list = new ArrayList<String>();

        for(String s : endTime) {
            if(s != null && s.length() > 0) {
                list.add(s);
            }
        }

       return endTime = list.toArray(new String[list.size()]);

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
            mTvBookAppoin.setEnabled(false);

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
            gettingDurationAndMaxDuration(String.valueOf(mLocationTable.getLocID()),getmServiceTable.getServiceID());
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