package com.effone.reservopia.Activity;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.adapter.AppointmentListAdapter;
import com.effone.reservopia.adapter.LocationAdapter;
import com.effone.reservopia.adapter.ServiceTypeAdapter;
import com.effone.reservopia.adapter.TimeZoneAdapter;
import com.effone.reservopia.model.AppointmentDataTime;
import com.effone.reservopia.model.Locations;
import com.effone.reservopia.model.TimeZoneDetails;
import com.effone.reservopia.realmdb.LocationTable;
import com.effone.reservopia.realmdb.ServiceProvidedTable;
import com.effone.reservopia.realmdb.ServiceTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LocationServiceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ListView mLvServiceType;
    private TextView mTvTitle,mTvBookAppoin;
    private ServiceTypeAdapter mServiceTypeAdapter;
    private AppCompatSpinner mSpinner,mTimeZone;
    private int countOfServiceType=0;
    private Realm mRealm;
    private String appointment_id=""+0,service_ID;
    private ImageView mIvBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_service2);

        appointment_id= getIntent().getStringExtra("id");
        service_ID= getIntent().getStringExtra("service_id");
        if(appointment_id == null)
            appointment_id = ""+0;

        mIvBackBtn=(ImageView)findViewById(R.id.iv_back_btn);
        mIvBackBtn.setOnClickListener(this);
        mLvServiceType=(ListView)findViewById(R.id.lv_service_type);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvBookAppoin=(TextView)findViewById(R.id.tv_book_appointment);
        mTvBookAppoin.setOnClickListener(this);
        mTvTitle.setText(getString(R.string.service_type));
        mSpinner=(AppCompatSpinner)findViewById(R.id.sp_location);
        mTimeZone=(AppCompatSpinner)findViewById(R.id.sp_timeZone);
       /* mLvServiceType.setOnItemClickListener(this);*/
        mRealm= Realm.getDefaultInstance();
        getLOcationAndServiceFromRealm();



    }
    ServiceTable mServiceTable;
    LocationTable mLocationTable;
    TimeZoneDetails mTimeZoneDetails;
    private void getLOcationAndServiceFromRealm() {
        mSpinner.setAdapter(new LocationAdapter(this,mRealm.where(LocationTable.class).findAll()));
        mLocationTable=(LocationTable)mSpinner.getItemAtPosition(0);
        mTimeZone.setAdapter(new TimeZoneAdapter(this,mRealm.where(TimeZoneDetails.class).findAll()));
        mLocationTable=(LocationTable)mSpinner.getItemAtPosition(0);
        mTimeZoneDetails=(TimeZoneDetails)mTimeZone.getItemAtPosition(0);
        mSpinner.setOnItemSelectedListener(this);
        /*RealmList<ServiceTable> serviceTables=new RealmList<ServiceTable>();
        serviceTables.addAll(mRealm.where(ServiceTable.class).findAll());
        mLvServiceType.setAdapter(new ServiceTypeAdapter(this,serviceTables));*/

    }




    public List<AppointmentDataTime> jsonObject(String response) {
        List<AppointmentDataTime> listItems=new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<AppointmentDataTime>>(){}.getType();
        listItems = gson.fromJson(response, type);
        return listItems;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent inte=new Intent(this,AppointementBookingActivity.class);
        startActivity(inte);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_book_appointment){
            mServiceTable=(ServiceTable)mLvServiceType.getItemAtPosition(0);


            Intent inte=new Intent(this,AppointementBookingActivity.class);
            inte.putExtra("id",appointment_id);
            inte.putExtra("Location",""+mLocationTable.getLocID());
            inte.putExtra("Service",mServiceTable.getServiceID());
            inte.putExtra("TimeZone",mTimeZoneDetails.getStandardName());
            startActivity(inte);
        }else if(view.getId() == R.id.iv_back_btn){
            finish();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.sp_location) {
            mLocationTable = (LocationTable) mSpinner.getSelectedItem();
            setServiceList(mLocationTable.getLocID());

        }else  if(spinner.getId() == R.id.sp_timeZone) {
            mTimeZoneDetails=(TimeZoneDetails)mTimeZone.getSelectedItem();
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
