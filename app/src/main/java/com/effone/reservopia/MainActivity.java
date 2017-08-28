package com.effone.reservopia;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.reservopia.Activity.AppointementBookingActivity;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvBookingAppiontemnt, mTvHistory, mTvContactUs, mTvAboutUsText,mTvDateTime;
    private ImageView mImgIcon;
    private  Calendar mCalendar;
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
        String response="[{\n" +
                "  \"date\":\"24/8/2017\",\n" +
                "  \"time\":\"1:19pm\",\n" +
                "  \"location\":\"Bangalore\",\n" +
                "  \"locationId\":\"1\",\n" +
                "  \"serviceType\":\"HairCut\",\n" +
                "  \"serviceTypeId\":\"1\"\n" +
                "},\n" +
                "{\n" +
                "  \"date\":\"24/8/2017\",\n" +
                "  \"time\":\"1:30pm\",\n" +
                "  \"location\":\"Bangalore\",\n" +
                "  \"locationId\":\"1\",\n" +
                "  \"serviceType\":\"HairCut\",\n" +
                "  \"serviceTypeId\":\"1\"\n" +
                "},\n" +
                "{\n" +
                "  \"date\":\"24/8/2017\",\n" +
                "  \"time\":\"2:19pm\",\n" +
                "  \"location\":\"Bangalore\",\n" +
                "  \"locationId\":\"1\",\n" +
                "  \"serviceType\":\"HairCut\",\n" +
                "  \"serviceTypeId\":\"1\"\n" +
                "}\n" +
                "\n" +
                "]";

        mAppointmentListAdapter=new AppointmentListAdapter(this, jsonObject(response));
        mLvAppointmentList.setAdapter(mAppointmentListAdapter);

    }

    private void settingAboutUs() {

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
            String dayName = parts[0]+"\n <br> "+parts[1]+" "+parts[2]+"\n "+"<font color='#EE0000'>"+dayOfTheWeek+"</font>";
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
