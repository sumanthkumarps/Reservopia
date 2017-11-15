package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.adapter.AppointmentListAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.model.CancelAppointment;
import com.effone.mobile.model.CancelAppointmentBoby;
import com.effone.mobile.model.ChangePassword;
import com.effone.mobile.model.History;
import com.effone.mobile.model.SearchAppointment;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AppointmentDetailsAct";
    private  History mAppointmentDateTime;
    private TextView mTvConfirmationId,mTvEmail,mTvUserName,mTvAppointmentID,
    mTvLocName,mTvServiceName,mTvAppointmentDateTime,mTvScheduledTimeZone,mTvAddress,mTvTitile,mTvOrgName,mTvPhoneNmber,mTvProviderName;
    private Button mTvEditAppointment,mTvCancelAppointment;
    private Toolbar toolbar;
    private ProgressDialog mCommonProgressDialog;
    private LinearLayout mLiOrgName,li_last_name;

    private ImageView mIvBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        mAppointmentDateTime = (History) getIntent().getSerializableExtra("selectedItem");
        ResvUtils.enableBackButton(this);
        ResvUtils.enableHomeButton(this);
        getUpComingAppointmentList();
    }

    private void getUpComingAppointmentList() {

        mTvTitile=(TextView)findViewById(R.id.tv_title);
        mIvBackBtn=(ImageView)findViewById(R.id.iv_back_btn);
        mIvBackBtn.setOnClickListener(this);


        mTvAppointmentDateTime  =(TextView)findViewById(R.id.tv_appointment_date);
        mTvScheduledTimeZone=(TextView)findViewById(R.id.tv_timeZone);

        mTvUserName=(TextView)findViewById(R.id.tv_first_name);
        mTvEmail=(TextView)findViewById(R.id.tv_email);
        mTvLocName=(TextView)findViewById(R.id.tv_locName);
        mTvServiceName=(TextView)findViewById(R.id.tv_service);
        mTvProviderName=(TextView)findViewById(R.id.tv_provider_name);
        mTvCancelAppointment=(Button)findViewById(R.id.tv_cancel_appointment);
        mTvConfirmationId=(TextView)findViewById(R.id.tv_confirmation);
        mTvOrgName=(TextView)findViewById(R.id.tv_orgName);
        mTvAddress=(TextView)findViewById(R.id.tv_address);
        mTvEditAppointment=(Button)findViewById(R.id.tv_editAppointment);
        mTvEditAppointment.setTransformationMethod(null);
        mTvCancelAppointment.setTransformationMethod(null);
        mTvCancelAppointment.setOnClickListener(this);
        mTvPhoneNmber=(TextView)findViewById(R.id.tv_phone_number);
        mTvEditAppointment.setVisibility(View.VISIBLE);
        mTvEditAppointment.setOnClickListener(this);
        settingValues();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!ResvUtils.Operations.isOnline(this)) {
            ResvUtils.Operations.showNoNetworkActivity(this);
        }
    }

    private void settingValues() {
        mTvTitile.setText(getString(R.string.appointment_details));
        mTvConfirmationId.setText(mAppointmentDateTime.getConfirmationNo());
        mTvLocName.setText(mAppointmentDateTime.getLocName());
        mTvUserName.setText(mAppointmentDateTime.getFirstName()+" "+mAppointmentDateTime.getLastName());
        mTvEmail.setText(mAppointmentDateTime.getEmail());
        mTvProviderName.setText(mAppointmentDateTime.getProviderName());
        mTvServiceName.setText(mAppointmentDateTime.getServiceName());
        mTvAppointmentDateTime.setText(Html.fromHtml(mAppointmentDateTime.getAppointmentDateTime()));
        mTvScheduledTimeZone.setText(mAppointmentDateTime.getScheduledTimeZone());
        mTvAddress.setText(mAppointmentDateTime.getAddress1()+" "+ mAppointmentDateTime.getAddress2()+" "+mAppointmentDateTime.getCity()+" "+mAppointmentDateTime.getState()+" "+mAppointmentDateTime.getZip());
        mTvOrgName.setText(mAppointmentDateTime.getOrgName());
        mTvPhoneNmber.setText(mAppointmentDateTime.getPhone());
        if(mAppointmentDateTime.getIsCancelled()) {
            mTvEditAppointment.setEnabled(false);
            mTvCancelAppointment.setText("Cancelled");
            mTvCancelAppointment.setBackgroundColor(Color.GRAY);
            mTvCancelAppointment.setTextColor(Color.RED);
        }

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

            if(!mAppointmentDateTime.getIsCancelled()) {
                Intent intent = new Intent(this, LocationServiceActivity.class);
                intent.putExtra("check", true);
                intent.putExtra("id", mAppointmentDateTime.getConfirmationNo());
                intent.putExtra("service_id", mAppointmentDateTime.getServiceID());
                intent.putExtra("timeZone", mAppointmentDateTime.getScheduledTimeZone());
                intent.putExtra("location_id", mAppointmentDateTime.getLocID());
                intent.putExtra("selectedItem", mAppointmentDateTime);
                //intent.putExtra("date",mAppointmentDateTime.getAppointmentDateTime());
                startActivity(intent);
            }
        } else if(view.getId() == R.id.tv_cancel_appointment){
            if(!mAppointmentDateTime.getIsCancelled()) {
                alertMsg(mAppointmentDateTime.getConfirmationNo());
            }
        }
    }

    private void alertMsg(final String confirmationNo) {
        ResvUtils.createYesOrNoDialog(AppointmentDetailsActivity.this, "Do you want cancel the appointment.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                switch (id) {
                    case DialogInterface.BUTTON_POSITIVE:
                        cancelAppointment(confirmationNo);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        });
    }

    private void cancelAppointment(String confirmationNo) {

        CancelAppointmentBoby cancelAppointmentBoby=new CancelAppointmentBoby();
        cancelAppointmentBoby.setConfirmationNo(mAppointmentDateTime.getConfirmationNo());
        cancelAppointmentBoby.setIsEndUser("1");
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

        Call<CancelAppointment> call = apiService.delCancelAppointment(getString(R.string.token),mAppointmentDateTime.getConfirmationNo());

        call.enqueue(new Callback<CancelAppointment>() {
            @Override
            public void onResponse(Call<CancelAppointment> call, Response<CancelAppointment> response) {
                response.raw().request().url();
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                if(response.isSuccessful()){
                    if(response.body().getResult()){
                        ResvUtils.createOKAlert(AppointmentDetailsActivity.this, "", "Appointment cancellation has done.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(AppointmentDetailsActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else {
                        ResvUtils.createOKAlert(AppointmentDetailsActivity.this, "", getString(R.string.unable_to), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                    }
                }else{
                    ResvUtils.createOKAlert(AppointmentDetailsActivity.this, "", getString(R.string.unable_to), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CancelAppointment> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                ResvUtils.createOKAlert(AppointmentDetailsActivity.this, "", "Unable to cancel the Appointment.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }
}
