package com.effone.reservopia;

import android.content.Context;
import android.content.Intent;

import java.util.Arrays;
import java.util.Calendar;
import android.net.ParseException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.reservopia.Activity.AppointementBookingActivity;
import com.effone.reservopia.Activity.AppointmentDetailsActivity;
import com.effone.reservopia.Activity.AppointmentHistoryActivity;
import com.effone.reservopia.Activity.LocationServiceActivity;
import com.effone.reservopia.adapter.AppointmentListAdapter;
import com.effone.reservopia.common.AppPreferene;
import com.effone.reservopia.model.AppointmentDataTime;
import com.effone.reservopia.model.History;
import com.effone.reservopia.model.LocationAndService;
import com.effone.reservopia.model.LocationAndServiceResult;
import com.effone.reservopia.model.Locations;
import com.effone.reservopia.model.LocationsXServices;
import com.effone.reservopia.model.Result;
import com.effone.reservopia.model.Services;
import com.effone.reservopia.model.UpCommingAppointmentModel;
import com.effone.reservopia.realmdb.LocXServiceTable;
import com.effone.reservopia.realmdb.LocationTable;
import com.effone.reservopia.realmdb.ServiceProvidedTable;
import com.effone.reservopia.realmdb.ServiceTable;
import com.effone.reservopia.rest.ApiClient;
import com.effone.reservopia.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private TextView mTvBookingAppiontemnt, mTvHistory, mTvContactUs, mTvAboutUsText,mTvDateTime;
    private ImageView mImgIcon;
    private  Calendar mCalendar;
    private ImageView mIvBackBtn;
    private TextView mTvTitle;
    private ListView mLvAppointmentList;

    private AppointmentListAdapter mAppointmentListAdapter;
    private String TAG="MainActivity";
    private ArrayList<Locations> mLocation;
    private ArrayList<Services> mService;
    private Realm mRealm;
    private ArrayList<LocationsXServices> mLocationXService;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendar= Calendar.getInstance();
        declarations();
        mRealm=Realm.getDefaultInstance();
        if(!AppPreferene.with(this).getPreLoad()){
            getLocationAndServicesAndSave();
        }
    }

    private void getLocationAndServicesAndSave() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<LocationAndService> call = apiService.getLocationNService(getString(R.string.token), 1);
        call.enqueue(new Callback<LocationAndService>() {
            @Override
            public void onResponse(Call<LocationAndService> call, Response<LocationAndService> response) {
                LocationAndServiceResult locationAndService = response.body().getResult();
                mLocation = locationAndService.getLocations();
                mService = locationAndService.getServices();
                mLocationXService = locationAndService.getLocationsXServices();
                saveToRealm();
            }

            @Override
            public void onFailure(Call<LocationAndService> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    private void saveToRealm() {
        try {
            insertLocationDataIntoDatabase();
            insertServiceDataIntoDatabase();
            insertLocXServDataIntoDatabase();

        }catch (Exception e){

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

    }

    private void insertServiceDataIntoDatabase() {
        for (int i = 0; i < mService.size(); i++) {
            mRealm.beginTransaction();
            ServiceTable services=new ServiceTable();
            services.setServiceID(mService.get(i).getServiceID());
            services.setServiceName(mService.get(i).getServiceName());
            services.setDescription(mService.get(i).getDescription());
            services.setDuration(mService.get(i).getDuration());
            services.setIsActive(mService.get(i).getIsActive());
            mRealm.insert(services);
            mRealm.commitTransaction();
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


    private void declarations() {
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
        mTvBookingAppiontemnt.setOnClickListener(this);
        mTvHistory.setOnClickListener(this);
        mTvContactUs.setOnClickListener(this);
        mLvAppointmentList=(ListView)findViewById(R.id.lv_upcomingAppointent);
        settingData();
        settingAboutUs();
        upcomingAppointmentList();
    }
    private void upcomingAppointmentList() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<UpCommingAppointmentModel> call = apiService.getUpCommingAppointmentDetails(getString(R.string.token),"application/json", 1, "abdulrahim.sk.dev@gmail.com");
        call.enqueue(new Callback<UpCommingAppointmentModel>() {
            @Override
            public void onResponse(Call<UpCommingAppointmentModel> call, Response<UpCommingAppointmentModel> response) {
                Result results = response.body().getResult();
                List<History> histories= Arrays.asList(results.getUpcoming());
                mAppointmentListAdapter=new AppointmentListAdapter(MainActivity.this, histories);
                mLvAppointmentList.setAdapter(mAppointmentListAdapter);
                mLvAppointmentList.setOnItemClickListener(MainActivity.this);
            }

            @Override
            public void onFailure(Call<UpCommingAppointmentModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
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
        if (formattedDate.contains("-")) {
            // Split it.
            String[] parts = formattedDate.split("-");
            String dayName = parts[0]+" "+parts[1]+" "+parts[2]+" "+"<font color='#174e9e'>"+dayOfTheWeek+"</font>";
            mTvDateTime.setText(Html.fromHtml(dayName) );
        } else {
            throw new IllegalArgumentException("String " + formattedDate + " does not contain -");
        }

        Log.e("MainActivity",formattedDate+" "+dayOfTheWeek);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_booking_app:
                openActivity(this, LocationServiceActivity.class);
                break;
            case R.id.tv_history:
                openActivity(this, AppointmentHistoryActivity.class);
                break;
        }

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
