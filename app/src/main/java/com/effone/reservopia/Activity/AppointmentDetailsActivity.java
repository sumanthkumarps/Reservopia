package com.effone.reservopia.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.common.ResvUtils;
import com.effone.reservopia.model.AppointmentDataTime;
import com.effone.reservopia.model.History;

public class AppointmentDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private  History mAppointmentDateTime;
    private TextView mTvConfirmationId,mTvEmail,mTvUserName,mTvAppointmentID,
    mTvLocName,mTvServiceName,mTvAppointmentDateTime,mTvScheduledTimeZone,mTvAddress,mTvTitile,mTvOrgName,mTvEditAppointment;
    private Toolbar toolbar;

    private LinearLayout mLiOrgName,li_last_name;

    private ImageView mIvBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        mAppointmentDateTime = (History) getIntent().getSerializableExtra("selectedItem");
        ResvUtils.enableBackButton(this);
        getUpComingAppointmentList();
    }

    private void getUpComingAppointmentList() {
        mLiOrgName=(LinearLayout)findViewById(R.id.li_orgName);
        mLiOrgName.setVisibility(View.GONE);
        li_last_name=(LinearLayout)findViewById(R.id.li_last_name);
        li_last_name.setVisibility(View.GONE);
        mTvTitile=(TextView)findViewById(R.id.tv_title);
        mIvBackBtn=(ImageView)findViewById(R.id.iv_back_btn);
        mIvBackBtn.setOnClickListener(this);
        mTvConfirmationId=(TextView)findViewById(R.id.tv_confimration);
        mTvEmail=(TextView)findViewById(R.id.tv_email);
        mTvUserName=(TextView)findViewById(R.id.tv_userName);
        mTvLocName=(TextView)findViewById(R.id.tv_locName);
        mTvAppointmentID=(TextView)findViewById(R.id.tv_appointment_id);
        mTvAppointmentID.setVisibility(View.GONE);
        mTvServiceName=(TextView)findViewById(R.id.tv_serviceName);
        mTvAppointmentDateTime=(TextView)findViewById(R.id.tv_appointment_date);
        mTvScheduledTimeZone=(TextView)findViewById(R.id.tv_timeZone);
        mTvAddress=(TextView)findViewById(R.id.tv_address);
        mTvEditAppointment=(TextView)findViewById(R.id.tv_editAppointment);
        mTvEditAppointment.setVisibility(View.VISIBLE);
        mTvEditAppointment.setOnClickListener(this);
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
        mTvAddress.setText("NO ADRESS NODE IN API");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back_btn){
            finish();
        }else if(view.getId() == R.id.tv_editAppointment){
            Intent intent= new Intent(this,LocationServiceActivity.class);
            intent.putExtra("check",true);
            intent.putExtra("id",mAppointmentDateTime.getAppointmentID());
            intent.putExtra("service_id",mAppointmentDateTime.getServiceID());
            startActivity(intent);
        }
    }
}
