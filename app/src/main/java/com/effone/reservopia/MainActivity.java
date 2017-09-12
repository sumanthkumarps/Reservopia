package com.effone.reservopia;

import android.content.Context;
import android.content.Intent;
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
import com.effone.reservopia.model.AppointmentDataTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private TextView mTvBookingAppiontemnt, mTvHistory, mTvContactUs, mTvAboutUsText,mTvDateTime;
    private ImageView mImgIcon;
    private  Calendar mCalendar;
    private ImageView mIvBackBtn;
    private TextView mTvTitle;
    private ListView mLvAppointmentList;

    private AppointmentListAdapter mAppointmentListAdapter;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendar= Calendar.getInstance();
        declarations();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
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
        String response="[ \n" +
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
                "      \"Email\":\"sumath@gmail.com\",\n" +
                "      \"LocName\":\"Online meetings\",\n" +
                "      \"ServiceName\":\"No specific service\",\n" +
                "      \"AppointmentDateTime\":\"<b>Friday, Sep. 01, 2017</b> <b><font color='#0F3F7B'>08:30 AM</font></b>\"\n" +
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
                "      \"Email\":\"sumath@gmail.com\",\n" +
                "      \"LocName\":\"Online meetings\",\n" +
                "      \"ServiceName\":\"No specific service\",\n" +
                "      \"AppointmentDateTime\":\"<b>Wednesday, Aug. 30, 2017</b> <b><font color='#0F3F7B'>12:30 PM</font></b>\"\n" +
                "   }\n" +", " +
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
                "      \"Email\":\"sumath@gmail.com\",\n" +
                "      \"LocName\":\"Online meetings\",\n" +
                "      \"ServiceName\":\"No specific service\",\n" +
                "      \"AppointmentDateTime\":\"<b>Tusday, Aug. 29, 2017</b> <b><font color='#0F3F7B'>11:30 AM</font></b>\"\n" +
                "   }\n"+
        "]";

        mAppointmentListAdapter=new AppointmentListAdapter(this, jsonObject(response));
        mLvAppointmentList.setAdapter(mAppointmentListAdapter);
        mLvAppointmentList.setOnItemClickListener(this);

    }

    private void settingAboutUs() {

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AppointmentDataTime appointmentDataTime=(AppointmentDataTime) mLvAppointmentList.getItemAtPosition(i);
        Intent intent=new Intent(this,AppointmentDetailsActivity.class);
        intent.putExtra("selectedItem",appointmentDataTime);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void settingData() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(mCalendar.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        if (formattedDate.contains("-")) {
            // Split it.
            String[] parts = formattedDate.split("-");
            String dayName = parts[0]+" "+parts[1]+" "+parts[2]+" "+"<font color='#0F3F7B'>"+dayOfTheWeek+"</font>";
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
