package com.effone.reservopia.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.adapter.AppointmentListAdapter;
import com.effone.reservopia.adapter.ServiceTypeAdapter;
import com.effone.reservopia.model.AppointmentDataTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocationServiceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
  private String response="[ \n" +
          "   { \n" +
          "      \"AppointmentID\":3,\n" +
          "      \"LocID\":2,\n" +
          "      \"UserID\":5,\n" +
          "      \"ConfirmationNo\":10003,\n" +
          "      \"AppointmentTypeRefID\":27,\n" +
          "      \"ServiceID\":2,\n" +
          "      \"StartTime\":\"09/01/2017 08:00 AM\",\n" +
          "      \"EndTime\":\"09/01/2017 08:30 AM\",\n" +
          "      \"ScheduledTimeZone\":\"India Standard Time\",\n" +
          "      \"SendEmailReminder\":false,\n" +
          "      \"SendTextReminder\":false,\n" +
          "      \"AdditionalEmail\":\"\",\n" +
          "      \"IsLoggedIn\":false,\n" +
          "      \"IsCheckedIn\":false,\n" +
          "      \"IsCancelled\":false,\n" +
          "      \"CancelTypeRefID\":null,\n" +
          "      \"CancelledBy\":null,\n" +
          "      \"IsAssigned\":false,\n" +
          "      \"AssignedTo\":null,\n" +
          "      \"AuditID\":5,\n" +
          "      \"OrgID\":0,\n" +
          "      \"UserName\":\"Naveed Farooqui\",\n" +
          "      \"Email\":\"mdnaveed29@gmail.com\",\n" +
          "      \"LocName\":\"Online meetings\",\n" +
          "      \"ServiceName\":\"No specific service\",\n" +
          "      \"AppointmentDateTime\":\"<b>Friday, Sep. 01, 2017</b> from <b>08:00 AM</b> - <b>08:30 AM</b>\"\n" +
          "   },\n" +
          "   { \n" +
          "      \"AppointmentID\":2,\n" +
          "      \"LocID\":2,\n" +
          "      \"UserID\":5,\n" +
          "      \"ConfirmationNo\":10002,\n" +
          "      \"AppointmentTypeRefID\":27,\n" +
          "      \"ServiceID\":2,\n" +
          "      \"StartTime\":\"08/30/2017 12:00 PM\",\n" +
          "      \"EndTime\":\"08/30/2017 12:30 PM\",\n" +
          "      \"ScheduledTimeZone\":\"India Standard Time\",\n" +
          "      \"SendEmailReminder\":false,\n" +
          "      \"SendTextReminder\":false,\n" +
          "      \"AdditionalEmail\":\"\",\n" +
          "      \"IsLoggedIn\":false,\n" +
          "      \"IsCheckedIn\":false,\n" +
          "      \"IsCancelled\":false,\n" +
          "      \"CancelTypeRefID\":null,\n" +
          "      \"CancelledBy\":null,\n" +
          "      \"IsAssigned\":false,\n" +
          "      \"AssignedTo\":null,\n" +
          "      \"AuditID\":5,\n" +
          "      \"OrgID\":0,\n" +
          "      \"UserName\":\"Naveed Farooqui\",\n" +
          "      \"Email\":\"mdnaveed29@gmail.com\",\n" +
          "      \"LocName\":\"Online meetings\",\n" +
          "      \"ServiceName\":\"No specific service\",\n" +
          "      \"AppointmentDateTime\":\"<b>Wednesday, Aug. 30, 2017</b> from <b>12:00 PM</b> - <b>12:30 PM</b>\"\n" +
          "   }\n" +
          "]";
    private ListView mLvServiceType;
    private TextView mTvTitle,mTvBookAppoin;
    private ServiceTypeAdapter mServiceTypeAdapter;
    private AppCompatSpinner mSpinner;
    private int countOfServiceType=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(countOfServiceType >1) {
            setContentView(R.layout.activity_location_service);
        }else{
            setContentView(R.layout.location_service2);
        }

        mLvServiceType=(ListView)findViewById(R.id.lv_service_type);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.service_type));
        mSpinner=(AppCompatSpinner)findViewById(R.id.sp_location);
        ArrayList<String> locations=new ArrayList<>();
        locations.add("Banglaore");
        locations.add("Mangalore");
        locations.add("Hindupur");
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.date_time_slot_grid,locations.toArray());
        mSpinner.setAdapter(adapter);
        if(countOfServiceType >1) {
            mServiceTypeAdapter = new ServiceTypeAdapter(this, jsonObject(response), 1);
        }else{
                 basedOnCondition();
            mServiceTypeAdapter = new ServiceTypeAdapter(this, jsonObject(response), 0);
        }
        mLvServiceType.setAdapter(mServiceTypeAdapter);
        mLvServiceType.setOnItemClickListener(this);



    }

    private void basedOnCondition() {
        mTvBookAppoin=(TextView)findViewById(R.id.tv_book_appointment);
        mTvBookAppoin.setOnClickListener(this);
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
        Intent inte=new Intent(this,AppointementBookingActivity.class);
        startActivity(inte);
    }
}
