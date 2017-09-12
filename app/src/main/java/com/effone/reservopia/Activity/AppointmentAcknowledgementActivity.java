package com.effone.reservopia.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.effone.reservopia.R;

public class AppointmentAcknowledgementActivity extends AppCompatActivity {
    private TextView mTvName,mTvService,mTvDate,mTvAdress,mTvPhone,mTVCOnfirmationNo,mTvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_acknowledgement);
        init();
    }

    private void init() {
        mTvName=(TextView)findViewById(R.id.tv_name_value);
        mTvAdress=(TextView)findViewById(R.id.tv_address_value);
        mTvService=(TextView)findViewById(R.id.tv_service_value);
        mTvPhone=(TextView)findViewById(R.id.tv_phone_value);
        mTvDate=(TextView)findViewById(R.id.tv_date_value);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTVCOnfirmationNo=(TextView)findViewById(R.id.tv_confirmation_no_value);
        mTvTitle.setText(getString(R.string.appointment_confirmation));
        mTvName.setText("Sumanth");
        mTvAdress.setText("effone Technology,Rajajinagar");
        mTvService.setText("Hair cut");
        mTvPhone.setText("9846375423");
        mTvDate.setText("08/29/2017: 01:30 PM");
        mTVCOnfirmationNo.setText("2343279");
    }

}
