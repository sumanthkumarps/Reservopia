package com.effone.mobile;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.util.Arrays;
import java.util.Calendar;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.mobile.Activity.AppointmentAcknowledgementActivity;
import com.effone.mobile.Activity.AppointmentDetailsActivity;
import com.effone.mobile.Activity.AppointmentHistoryActivity;
import com.effone.mobile.Activity.LocationServiceActivity;
import com.effone.mobile.Activity.LoginActivity;
import com.effone.mobile.Activity.MultipleLocationServiceActivity;
import com.effone.mobile.Activity.NetworkErrorActivity;
import com.effone.mobile.Activity.RegisterActivity;
import com.effone.mobile.Activity.SearchAppointmentActivity;
import com.effone.mobile.adapter.AppointmentListAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.model.AppointmentDataTime;
import com.effone.mobile.model.GetTimeZones;
import com.effone.mobile.model.History;
import com.effone.mobile.model.LocationAndService;
import com.effone.mobile.model.LocationAndServiceResult;
import com.effone.mobile.model.Locations;
import com.effone.mobile.model.LocationsXServices;
import com.effone.mobile.model.ProviderTable;
import com.effone.mobile.model.Providers;
import com.effone.mobile.model.Result;
import com.effone.mobile.model.Services;
import com.effone.mobile.model.TimeZoneDetails;
import com.effone.mobile.model.Title;
import com.effone.mobile.model.TitleNames;
import com.effone.mobile.model.UpCommingAppointmentModel;
import com.effone.mobile.realmdb.LocXServiceTable;
import com.effone.mobile.realmdb.LocationTable;
import com.effone.mobile.realmdb.ProvidersTable;
import com.effone.mobile.realmdb.ServiceProvidedTable;
import com.effone.mobile.realmdb.ServiceTable;
import com.effone.mobile.receivers.NetworkChangeReceiver;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{

    private TextView mTvBookingAppiontemnt, mTvHistory, mTvContactUs, mTvAboutUsText,mTvDateTime,mTvTime;
    private ImageView mImgIcon;
    private  Calendar mCalendar;
    private ImageView mIvBackBtn;
    private TextView mTvTitle;
    private ListView mLvAppointmentList;

    private AppointmentListAdapter mAppointmentListAdapter;
    private String TAG="MainActivity";
    private ArrayList<Locations> mLocation;
    private ArrayList<Services> mService;
    private ArrayList<TitleNames> mTitle;
    private ArrayList<ProviderTable> mProvider;
    private ArrayList<TimeZoneDetails> mTimeZoneDetails;
    private Realm mRealm;
    private ArrayList<LocationsXServices> mLocationXService;
    //private BroadcastReceiver mNetworkReceiver;
    private static LinearLayout linearLayout;
   // private NetworkChangeReceiver networkChangeReceiver;
    private ProgressDialog mCommonProgressDialog;
    private  TextView mTvCountAppointment;
    private  TextView mIvLogout;
    private TextView mTvSearch;
    private ImageView mIvSearch;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        mIvLogout=(TextView)findViewById(R.id.iv_home_btn);
        changingLogoutImages();
        mIvLogout.setOnClickListener(this);
       // mNetworkReceiver = new NetworkChangeReceiver();
       // networkChangeReceiver =new NetworkChangeReceiver();
        mCalendar= Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        Log.e("Time"+tz.getDisplayName(),"");
        declarations();
       //registerNetworkBroadcastForNougat();
        mRealm=Realm.getDefaultInstance();

        if(!AppPreferene.with(this).getPreLoad()){
            getLocationAndServicesAndSave();
            getTitleAndSave();
            getTimeZoneAndSave();
        }
    }

    private void getTimeZoneAndSave() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<GetTimeZones> call = apiService.getTimeZoneDetails(getString(R.string.token));
        call.enqueue(new Callback<GetTimeZones>() {
            @Override
            public void onResponse(Call<GetTimeZones> call, Response<GetTimeZones> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                mTimeZoneDetails=response.body().getResult();
                insertTimeZoneIntoDatabase();
            }

            @Override
            public void onFailure(Call<GetTimeZones> call, Throwable t) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Log.e(TAG, t.toString());
            }

        });

    }

    private void insertTimeZoneIntoDatabase() {
        for(int i=0;i<mTimeZoneDetails.size();i++){
            mRealm.beginTransaction();
            TimeZoneDetails timeZoneDetails=new TimeZoneDetails();
            timeZoneDetails.setDisplayName(mTimeZoneDetails.get(i).getDisplayName());
            timeZoneDetails.setDisplayShortName(mTimeZoneDetails.get(i).getDisplayShortName());
            timeZoneDetails.setId(mTimeZoneDetails.get(i).getId());
            timeZoneDetails.setStandardName(mTimeZoneDetails.get(i).getStandardName());
            mRealm.insert(timeZoneDetails);
            mRealm.commitTransaction();
        }
        AppPreferene.with(this).setPreLoad(true);
    }

    private void getTitleAndSave() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Title> call = apiService.getTitleDetails(getString(R.string.token));
        call.enqueue(new Callback<Title>() {
            @Override
            public void onResponse(Call<Title> call, Response<Title> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                mTitle=response.body().getResult();
                insertTitleDataIntoDatabase();
            }
            @Override
            public void onFailure(Call<Title> call, Throwable t) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Log.e(TAG, t.toString());
            }
        });

    }

  /*  protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        //unregisterNetworkChanges();
    }


    /*private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }*/
    private void getLocationAndServicesAndSave() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<LocationAndService> call = apiService.getLocationNService(getString(R.string.token), getString(R.string.org_id));
        call.enqueue(new Callback<LocationAndService>() {
            @Override
            public void onResponse(Call<LocationAndService> call, Response<LocationAndService> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                LocationAndServiceResult locationAndService = response.body().getResult();
                mLocation = locationAndService.getLocations();
                mService = locationAndService.getServices();
                mProvider=locationAndService.getProviders();
          //      mLocationXService = locationAndService.getLocationsXServices();
                saveToRealm();
            }

            @Override
            public void onFailure(Call<LocationAndService> call, Throwable t) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Log.e(TAG, t.toString());
            }
        });

    }

    private void saveToRealm() {
        try {
            insertLocationDataIntoDatabase();
            insertServiceDataIntoDatabase();
            insertProviderDataIntoDatabase();
            //insertLocXServDataIntoDatabase();
        }catch (Exception e){
            Log.e("",""+e);
        }
    }

    private void insertProviderDataIntoDatabase() {

        for (int i = 0; i < mProvider.size(); i++) {
            mRealm.beginTransaction();
            ProviderTable providers=new ProviderTable();
            providers.setUserID(mProvider.get(i).getUserID());
            providers.setUserName(mProvider.get(i).getUserName());
            providers.setDateOfBirth(mProvider.get(i).getDateOfBirth());
            providers.setFirstName(mProvider.get(i).getFirstName());
            providers.setLastName(mProvider.get(i).getLastName());
            providers.setEmail(mProvider.get(i).getEmail());
            providers.setGender(mProvider.get(i).getGender());
            mRealm.insert(providers);
            mRealm.commitTransaction();
        }
    }

    private void insertTitleDataIntoDatabase() {
        for(int i=0;i<mTitle.size();i++)
        {
            mRealm.beginTransaction();
            TitleNames  titleNames=mRealm.createObject(TitleNames.class);
            titleNames.setText(mTitle.get(i).getText());
            titleNames.setValue(mTitle.get(i).getValue());
            mRealm.insert(titleNames);
            mRealm.commitTransaction();
        }
    }

    private void insertLocXServDataIntoDatabase() {
        for (int i = 0; i < mLocationXService.size(); i++) {
            mRealm.beginTransaction();
            LocXServiceTable locationsXService = mRealm.createObject(LocXServiceTable.class);
            locationsXService.setLocXServiceID (mLocationXService.get(i).getLocXServiceID());
            locationsXService.setLocID(mLocationXService.get(i).getLocID());
            locationsXService.setServiceID (mLocationXService.get(i).getServiceID());
            mRealm.insert(locationsXService);
            mRealm.commitTransaction();
        }
        AppPreferene.with(this).setPreLoad(true);
        if(mService.size() == 1 || mService == null){
            AppPreferene.with(this).setMulitpleService(true);
        }else{
            AppPreferene.with(this).setMulitpleService(false);
        }
    }

    private void insertServiceDataIntoDatabase() {
        try {
            for (int i = 0; i < mService.size(); i++) {
                mRealm.beginTransaction();
                ServiceTable services = new ServiceTable();
                services.setServiceID(mService.get(i).getServiceID());
                services.setServiceName(mService.get(i).getServiceName());
                services.setDescription(mService.get(i).getDescription());
                services.setDuration(mService.get(i).getDuration());
                services.setIsActive(mService.get(i).getIsActive());
                services.setProviders(new RealmList<com.effone.mobile.realmdb.ProvidersTable>(mService.get(i).getProviders().toArray(new com.effone.mobile.realmdb.ProvidersTable[mService.get(i).getProviders().size()])));
                mRealm.insert(services);
                mRealm.commitTransaction();
            }
        }catch(Exception e){
            Log.e("",""+e);
        }
    }
    private void insertLocationDataIntoDatabase() {
        for (int i = 0; i < mLocation.size(); i++) {
            //If name is not blank creating a new Inventory object
            try {
                mRealm.beginTransaction();
                LocationTable locationTable = new LocationTable();;

                //Adding the given name to locationTable name
                locationTable.setLocID(Integer.parseInt(mLocation.get(i).getLocID()));
                locationTable.setLocName(mLocation.get(i).getLocName());
                locationTable.setPhone1(mLocation.get(i).getPhone1());
                locationTable.setPhone2(mLocation.get(i).getPhone2());
                locationTable.setAptEndRangeDate(mLocation.get(i).getAptEndRangeDate());
                locationTable.setAptStartRangeDate(mLocation.get(i).getAptStartRangeDate());
                locationTable.setAdvanceBookingDays(Integer.parseInt(mLocation.get(i).getAdvanceBookingDays()));
                locationTable.setAuditID(Integer.parseInt(mLocation.get(i).getAuditID()));
                locationTable.setCutOffTime(mLocation.get(i).getCutOffTime());
                locationTable.setFax(mLocation.get(i).getFax());
           /* locationTable.setIntrinsic(mLocation.get(i).getIsIntrinsic());
            locationTable.setUnUsed(mLocation.get(i).getIsUnUsed());*/
                locationTable.setLongitude(mLocation.get(i).getLongitude());
                locationTable.setLocationTimeZone(mLocation.get(i).getLocationTimeZone());
                locationTable.setLatitude(mLocation.get(i).getLatitude());
                locationTable.setOrgID(mLocation.get(i).getOrgID());
            /*locationTable.setActive(mLocation.get(i).getIsActive());
            locationTable.setAppointmentsForever(mLocation.get(i).getIsAppointmentsForever());*/
                //locationTable.setLeadTime(mLocation.get(i).getLeadTime());
                locationTable.setAddressLine1(mLocation.get(i).getAddress().getAddressLine1());
                locationTable.setAddressLine2(mLocation.get(i).getAddress().getAddressLine2());
                locationTable.setAddressLine3(mLocation.get(i).getAddress().getAddressLine3());
                locationTable.setCity(mLocation.get(i).getAddress().getCity());
                locationTable.setState(mLocation.get(i).getAddress().getState());
                locationTable.setZip(mLocation.get(i).getAddress().getZip());
                locationTable.setCountry(mLocation.get(i).getAddress().getCountry());
                locationTable.setServiceProvidedTables(new RealmList<ServiceProvidedTable>(mLocation.get(i).getServicesProvided().toArray(new ServiceProvidedTable[mLocation.get(i).getServicesProvided().size()])));//mLocation.get(i).getServicesProvided());
                // locationTable.add(locationTable);
           /* realm.beginTransaction();
            realm.copyToRealm(locationTable);
            realm.commitTransaction();*/
                mRealm.insert(locationTable);
                mRealm.commitTransaction();
            }catch(Exception e){
                Log.e("Realm Exception:",""+e);
            }
        }
    }
    LinearLayout mTvEmptyView;

    private void declarations() {
        mTvEmptyView=(LinearLayout) findViewById(R.id.tv_empty_view);
        mTvSearch=(TextView)findViewById(R.id.tv_search_and_title);
        mIvSearch=(ImageView)findViewById(R.id.iv_search);
        mTvBookingAppiontemnt = (TextView) findViewById(R.id.tv_booking_app);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvBackBtn = (ImageView) findViewById(R.id.iv_back_btn);
        mIvBackBtn.setVisibility(View.GONE);
        mTvTitle.setText(getString(R.string.app_name));
        mTvContactUs = (TextView) findViewById(R.id.tv_contact_us);
        mTvHistory = (TextView) findViewById(R.id.tv_history);
        mTvAboutUsText = (TextView) findViewById(R.id.tv_about_text);
        mImgIcon = (ImageView) findViewById(R.id.img_icon);
        mTvDateTime=(TextView)findViewById(R.id.tv_dateandtime);
        mTvTime=(TextView)findViewById(R.id.tv_time);
        mTvBookingAppiontemnt.setOnClickListener(this);
        mTvHistory.setOnClickListener(this);
        mTvContactUs.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
        mLvAppointmentList=(ListView)findViewById(R.id.lv_upcomingAppointent);


        settingData();
        settingAboutUs();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!ResvUtils.Operations.isOnline(this)) {
            ResvUtils.Operations.showNoNetworkActivity(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvCountAppointment=(TextView)findViewById(R.id.tv_count_appointments);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        checkingUpcomingAppointment();
        changingLogoutImages();
    }

    private void checkingUpcomingAppointment() {
        if(!AppPreferene.with(this).getEmail().equals("")) {
            upcomingAppointmentList();

        }
        else{
         removingCompleteData();
        }
    }

    private void removingCompleteData() {
        mTvCountAppointment.setText(""+0);
        mLvAppointmentList.setAdapter(null);
        mLvAppointmentList.setVisibility(View.GONE);
        mTvEmptyView.setVisibility(View.VISIBLE);
    }

    List<History> histories;

    private void upcomingAppointmentList() {

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

        Call<UpCommingAppointmentModel> call = apiService.getUpCommingAppointmentDetails(getString(R.string.token),"application/json", getString(R.string.org_id),AppPreferene.with(this).getEmail());

          call.enqueue(new Callback<UpCommingAppointmentModel>() {
            @Override
            public void onResponse(Call<UpCommingAppointmentModel> call, Response<UpCommingAppointmentModel> response) {
                response.raw().request().url();
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                try {
                    Result results = response.body().getResult();
                    histories = Arrays.asList(results.getUpcoming());
                    if(histories.size()>0) {
                        mAppointmentListAdapter = new AppointmentListAdapter(MainActivity.this, histories);
                        mTvEmptyView.setVisibility(View.GONE);
                        mLvAppointmentList.setVisibility(View.VISIBLE);
                        mLvAppointmentList.setAdapter(mAppointmentListAdapter);
                        mLvAppointmentList.setOnItemClickListener(MainActivity.this);
                    }else
                        removingCompleteData();
                    mTvCountAppointment.setText("" + histories.size());
                }catch (Exception e){
                    mLvAppointmentList.setEmptyView(findViewById(R.id.tv_history));
                }
            }

            @Override
            public void onFailure(Call<UpCommingAppointmentModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                mLvAppointmentList.setEmptyView(findViewById(R.id.tv_history));

            }
        });



    }

    private void settingAboutUs() {

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        History appointmentDataTime=(History) mLvAppointmentList.getItemAtPosition(i);
        Intent intent=new Intent(this,AppointmentDetailsActivity.class);
        intent.putExtra("selectedItem",appointmentDataTime);
        startActivity(intent);
    }


    private void settingData() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
        String formattedDate = df.format(mCalendar.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.US);
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
            mTvDateTime.setText(formattedDate);
            mTvTime.setText(dayOfTheWeek);

    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_search:
                openActivity(this, SearchAppointmentActivity.class);
                break;
            case R.id.tv_booking_app:

                if(ResvUtils.Operations.isOnline(this)) {
                    if(AppPreferene.with(this).getPreLoad()) {
                        openActivity(this, LocationServiceActivity.class);
                      /*  if (AppPreferene.with(this).getMulitpleService()) {

                        } *//*else {
                            openActivity(this, MultipleLocationServiceActivity.class);
                        }*/
                    }
                }else{

                        ResvUtils.Operations.showNoNetworkActivity(this);

                }

                break;
            case R.id.tv_history:
                if(ResvUtils.Operations.isOnline(this)) {
                openActivity(this, AppointmentHistoryActivity.class);
                }else{
                    ResvUtils.Operations.showNoNetworkActivity(this);
                }
                break;
            case R.id.iv_home_btn:
                if(AppPreferene.with(this).getUserId().equals("")){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra(getString(R.string.isFromHomeScreen),true);
                    startActivity(intent);
                }else {
                    ResvUtils.createYesOrNoDialog(this, "Are you sure you want to logout? ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            switch (id) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    removeDataFromPreference();
                                    changingLogoutImages();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.cancel();

                                    break;
                            }
                        }
                    });
                }

                break;
        }

    }

    private void changingLogoutImages() {
        if(AppPreferene.with(this).getUserId().equals("")){
            mIvLogout.setText(getString(R.string.login));
        }else {
            mIvLogout.setText(getString(R.string.logout));
        }
    }

    private void removeDataFromPreference() {
        AppPreferene.with(this).setEmail("");
        AppPreferene.with(this).setUserId("");
        AppPreferene.with(this).setOrgination("");
        AppPreferene.with(this).setAddress("");
        checkingUpcomingAppointment();
    }

    public List<AppointmentDataTime> jsonObject(String response) {
        List<AppointmentDataTime> listItems=new ArrayList<>();
            Gson gson = new Gson();
            Type type = new TypeToken<List<AppointmentDataTime>>(){}.getType();
            listItems = gson.fromJson(response, type);
          return listItems;
    }

    public void openActivity(Context context,Class<?> calledActivity){
        Intent intent = new Intent(context,calledActivity);
        startActivity(intent);
    }
}
