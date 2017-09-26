package com.effone.reservopia.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.model.Confirmation;
import com.effone.reservopia.model.ConfirmationDetails;
import com.effone.reservopia.rest.ApiClient;
import com.effone.reservopia.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentAcknowledgementActivity extends AppCompatActivity {
    private static final String TAG ="" ;

    private TextView mTvConfirmationId,mTvEmail,mTvUserName,mTvLastName,mTvTitle,
            mTvLocName,mTvServiceName,mTvAppointmentDateTime,mTvScheduledTimeZone,mTvAddress,mTvOrgName,mTvEditAppointment,mTvHeading;
    private String confirmationCode;

    private ImageView mTvImgBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        confirmationCode=getIntent().getStringExtra(getString(R.string.confirmation_no));
        getUpComingAppointmentList();
    }

    private void getUpComingAppointmentList() {
        mTvImgBackButton=(ImageView) findViewById(R.id.iv_back_btn);
        mTvImgBackButton.setVisibility(View.GONE);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.confirmation_details);
        mTvHeading=(TextView)findViewById(R.id.tv_upcoming);
        mTvHeading.setText(R.string.confirmation_details);
        mTvConfirmationId=(TextView)findViewById(R.id.tv_confimration);
        mTvEmail=(TextView)findViewById(R.id.tv_email);
        mTvUserName=(TextView)findViewById(R.id.tv_userName);
        mTvLocName=(TextView)findViewById(R.id.tv_locName);
        mTvLastName=(TextView)findViewById(R.id.tv_appointment_id);
        mTvServiceName=(TextView)findViewById(R.id.tv_serviceName);
        mTvAppointmentDateTime=(TextView)findViewById(R.id.tv_appointment_date);
        mTvScheduledTimeZone=(TextView)findViewById(R.id.tv_timeZone);
        mTvAddress=(TextView)findViewById(R.id.tv_address);
        mTvOrgName=(TextView)findViewById(R.id.tv_orgName);
        mTvEditAppointment=(TextView)findViewById(R.id.tv_editAppointment);
        mTvEditAppointment.setVisibility(View.GONE);
        gettingDataFromServer();
    }

    private void gettingDataFromServer() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Confirmation> call = apiService.getConfirmationDetails(getString(R.string.token), confirmationCode);
        call.enqueue(new Callback<Confirmation>() {
            @Override
            public void onResponse(Call<Confirmation> call, Response<Confirmation> response) {
                ConfirmationDetails confirmationDetails = response.body().getResult();

                mTvConfirmationId.setText(confirmationDetails.getConfirmationNo());
                mTvLastName.setText(confirmationDetails.getLastName());
                mTvLocName.setText(confirmationDetails.getLocName());
                mTvUserName.setText(confirmationDetails.getFirstName());
                mTvEmail.setText(confirmationDetails.getEmail());
                mTvServiceName.setText(confirmationDetails.getServiceName());
                mTvAppointmentDateTime.setText(Html.fromHtml(confirmationDetails.getScheduledDateTime()));
                mTvScheduledTimeZone.setText(confirmationDetails.getScheduledTimeZone());
                mTvAddress.setText(confirmationDetails.getAddress()+" "+confirmationDetails.getCity()+" "+confirmationDetails.getState()
                        +" "+confirmationDetails.getZip() );
                mTvOrgName.setText(confirmationDetails.getOrgName());

            }

            @Override
            public void onFailure(Call<Confirmation> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

}
