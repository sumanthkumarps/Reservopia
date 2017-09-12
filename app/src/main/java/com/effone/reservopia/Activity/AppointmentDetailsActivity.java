package com.effone.reservopia.Activity;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.model.AppointmentDataTime;

public class AppointmentDetailsActivity extends AppCompatActivity {
    private  AppointmentDataTime mAppointmentDateTime;
    private TextView mTvConfirmationId,mTvEmail,mTvUserName,mTvAppointmentID,
    mTvLocName,mTvServiceName,mTvAppointmentDateTime,mTvScheduledTimeZone,mTvAddress,mTvTitile;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        mAppointmentDateTime = (AppointmentDataTime) getIntent().getSerializableExtra("selectedItem");
        getUpComingAppointmentList();
    }

    private void getUpComingAppointmentList() {
        mTvTitile=(TextView)findViewById(R.id.tv_title);
        mTvConfirmationId=(TextView)findViewById(R.id.tv_confimration);
        mTvEmail=(TextView)findViewById(R.id.tv_email);
        mTvUserName=(TextView)findViewById(R.id.tv_userName);
        mTvLocName=(TextView)findViewById(R.id.tv_locName);
        mTvAppointmentID=(TextView)findViewById(R.id.tv_appointment_id);
        mTvServiceName=(TextView)findViewById(R.id.tv_serviceName);
        mTvAppointmentDateTime=(TextView)findViewById(R.id.tv_appointment_date);
        mTvScheduledTimeZone=(TextView)findViewById(R.id.tv_timeZone);
        mTvAddress=(TextView)findViewById(R.id.tv_address);
        settingValues();
    }

    private void settingValues() {
        mTvTitile.setText(getString(R.string.appointment_details));
        mTvConfirmationId.setText(mAppointmentDateTime.getConfirmationNo());
        mTvAppointmentID.setText(mAppointmentDateTime.getAppointmentID());
        mTvLocName.setText(mAppointmentDateTime.getLocName());
        mTvUserName.setText(mAppointmentDateTime.getUserName());
        mTvEmail.setText(mAppointmentDateTime.getEmail());
        mTvServiceName.setText(mAppointmentDateTime.getServiceName());
        mTvAppointmentDateTime.setText(Html.fromHtml(mAppointmentDateTime.getAppointmentDateTime()));
        mTvScheduledTimeZone.setText(mAppointmentDateTime.getScheduledTimeZone());
      /*  mTvAddress.setText("");*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
