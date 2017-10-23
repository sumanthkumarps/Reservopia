package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.model.Confirmation;
import com.effone.mobile.model.ConfirmationDetails;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentAcknowledgementActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="" ;

    private TextView mTvConfirmationId,mTvEmail,mTvUserName,mTvLastName,mTvTitle,mTvPhoneNUmber,
            mTvLocName,mTvServiceName,mTvAppointmentDateTime,mTvScheduledTimeZone,mTvAddress,mTvOrgName,mTvEditAppointment,mTvHeading;
    private String confirmationCode;

    private ImageView mTvImgBackButton;
    private ImageView mIvHomeBtn;
    private ProgressDialog mCommonProgressDialog;
    private boolean isRegisteredUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_acknowledgement);
        confirmationCode=getIntent().getStringExtra(getString(R.string.confirmation_no));
        isRegisteredUser=getIntent().getBooleanExtra(getString(R.string.password),false);

        getUpComingAppointmentList();
    }

    private void getUpComingAppointmentList() {
        mTvImgBackButton=(ImageView) findViewById(R.id.iv_back_btn);
        mTvImgBackButton.setVisibility(View.GONE);
        mIvHomeBtn=(ImageView)findViewById(R.id.iv_home_btn);
        mIvHomeBtn.setOnClickListener(this);
        mIvHomeBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home_btn));
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.confirmation_details);

        mTvConfirmationId=(TextView)findViewById(R.id.tv_appointment_date);
        mTvEmail=(TextView)findViewById(R.id.tv_email);
        mTvUserName=(TextView)findViewById(R.id.tv_first_name);
        mTvLocName=(TextView)findViewById(R.id.tv_locName);
        mTvLastName=(TextView)findViewById(R.id.tv_last);
        mTvServiceName=(TextView)findViewById(R.id.tv_service);
        mTvAppointmentDateTime=(TextView)findViewById(R.id.tv_appointment_date);
        mTvScheduledTimeZone=(TextView)findViewById(R.id.tv_timeZone);
        mTvAddress=(TextView)findViewById(R.id.tv_address);
        mTvOrgName=(TextView)findViewById(R.id.tv_orgName);
        mTvPhoneNUmber=(TextView)findViewById(R.id.tv_phone_number);

        gettingDataFromServer();
    }

    private void gettingDataFromServer() {
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

        Call<Confirmation> call = apiService.getConfirmationDetails(getString(R.string.token), confirmationCode,getString(R.string.org_id));
        call.enqueue(new Callback<Confirmation>() {
            @Override
            public void onResponse(Call<Confirmation> call, Response<Confirmation> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                if(response.body()!=null) {
                    ConfirmationDetails confirmationDetails = response.body().getResult();
                    if(isRegisteredUser) {
                        AppPreferene.with(AppointmentAcknowledgementActivity.this).setUserId(confirmationDetails.getUserID());
                        AppPreferene.with(AppointmentAcknowledgementActivity.this).setEmail(confirmationDetails.getEmail());
                    }
                    mTvConfirmationId.setText(confirmationDetails.getConfirmationNo());
                    mTvLastName.setText(confirmationDetails.getLastName());
                    mTvLocName.setText(confirmationDetails.getLocName());
                    mTvUserName.setText(confirmationDetails.getFirstName());
                    mTvEmail.setText(confirmationDetails.getEmail());
                    mTvServiceName.setText(confirmationDetails.getServiceName());
                    mTvAppointmentDateTime.setText(Html.fromHtml(confirmationDetails.getScheduledDateTime()));
                    mTvScheduledTimeZone.setText(confirmationDetails.getScheduledTimeZone());
                    AppPreferene.with(AppointmentAcknowledgementActivity.this).setAddress(confirmationDetails.getAddress1() + " " + confirmationDetails.getCity() + " " + confirmationDetails.getState()
                            + " " + confirmationDetails.getZip());
                    AppPreferene.with(AppointmentAcknowledgementActivity.this).setOrgination(confirmationDetails.getOrgName());
                    mTvAddress.setText(confirmationDetails.getAddress1() + " " + confirmationDetails.getCity() + " " + confirmationDetails.getState()
                            + " " + confirmationDetails.getZip());
                    mTvOrgName.setText(confirmationDetails.getOrgName());
                }
                else{
                    ResvUtils.createErrorAlert(AppointmentAcknowledgementActivity.this,getString(R.string.error),""+response.message());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          finish();
                        }
                    }, 5000);
                }

            }

            @Override
            public void onFailure(Call<Confirmation> call, Throwable t) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_home_btn){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
