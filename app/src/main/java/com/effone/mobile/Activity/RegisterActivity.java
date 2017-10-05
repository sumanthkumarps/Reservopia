package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.mobile.R;
import com.effone.mobile.adapter.TitleAdapter;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.model.AppointmentBookingModel;
import com.effone.mobile.model.BookingAppointmentUserDetails;
import com.effone.mobile.model.Response;
import com.effone.mobile.model.TitleNames;
import com.effone.mobile.model.User;
import com.effone.mobile.model.UserAddress;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sarith.vasu on 26-09-2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "";
    private EditText mEtEmail,mEtPhone,mEtFirstName,mEtLastName,mEtDateOfBirth,
            mEtAddress,mEtZip,mEtState,mEtPassword,mEtConfirmPassword,mEtCity;
    private RadioGroup mRGGender;
    private Button mBtSubmit;
    private String mStEmail,mStPhone,mStFirstName,mStLastName,mStDateOfBirth,
            mSttitle,mStGender,mStAddress,mStZip,mStState,mStPassword,mStConfirmPassword,mStCity;
    TextView mTvTitle;
    ApiInterface apiService;
    private CheckBox mCbCreateAccount;
    private Spinner mSpTitle;
   private AppointmentBookingModel appointmentBookingModel;
    private ProgressDialog mCommonProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ResvUtils.enableBackButton(this);

        appointmentBookingModel = (AppointmentBookingModel) getIntent().getSerializableExtra("appointment_details");
        apiService = ApiClient.getClient().create(ApiInterface.class);



        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText("Registration ");
        declarations();
    }
    private Realm mRealm;
    private void declarations() {
        mRealm= Realm.getDefaultInstance();
        mEtEmail=(EditText)findViewById(R.id.et_email);
        mEtEmail.setOnFocusChangeListener(this);
        mCbCreateAccount=(CheckBox)findViewById(R.id.cb_account);
        mCbCreateAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if (isChecked) {

                } else {

                }
            }
        });
        mEtPhone=(EditText)findViewById(R.id.et_phone);
        mEtFirstName=(EditText)findViewById(R.id.et_firstname);
        mEtLastName=(EditText)findViewById(R.id.et_lastName);
        mEtDateOfBirth=(EditText)findViewById(R.id.et_date_birth);
        mSpTitle=(Spinner)findViewById(R.id.et_title);
        mSpTitle.setAdapter(new TitleAdapter(this,mRealm.where(TitleNames.class).findAll()));
        mSpTitle.setOnItemSelectedListener(this);
        mEtAddress=(EditText)findViewById(R.id.et_address);
        mEtPassword=(EditText)findViewById(R.id.et_password);
        mEtConfirmPassword=(EditText)findViewById(R.id.et_conf_pass);
        mBtSubmit=(Button)findViewById(R.id.bt_submit);
        mRGGender=(RadioGroup)findViewById(R.id.radioSex);

        mBtSubmit.setOnClickListener(this);
    }


    private RadioButton radioSexButton;
    @Override
    public void onClick(View view) {
        mStEmail=mEtEmail.getText().toString().trim();
        mStPhone=mEtPhone.getText().toString().trim();
        mStFirstName=mEtFirstName.getText().toString().trim();
        mStLastName=mEtLastName.getText().toString().trim();
        mStAddress=mEtAddress.getText().toString().trim();
        mStDateOfBirth=mEtDateOfBirth.getText().toString().trim();
        mStPassword=mEtPassword.getText().toString().trim();
        mStConfirmPassword=mEtConfirmPassword.getText().toString().trim();
        int selectedId = mRGGender.getCheckedRadioButtonId();
        radioSexButton=(RadioButton) findViewById(selectedId);
        mStGender= (String) radioSexButton.getText();


        User user=new User();
        user.setUserID("0");
        user.setTitle(mTitleNames.getValue());
        user.setEmail(mStEmail);
        user.setPhone(mStPhone);
        user.setDisplayUserName(null);
        user.setFirstName(mStFirstName);
        user.setLastName(mStLastName);
        user.setGender(mStGender);
        user.setDateOfBirth(mStDateOfBirth);
        user.setIsTempPassword("0");
        user.setPreferredLocID(null);
        user.setPrimaryLocID(null);
        user.setIsActive("1");
        user.setAuditID("0");
        user.setIsTempPassword("0");
        user.setOrgID("1");
        user.setIsEndUser("1");
        UserAddress userAddress=new UserAddress();
        userAddress.setAddressLine1("");
        userAddress.setAddressLine2("");
        userAddress.setAddressLine3("");
        userAddress.setCity("");
        userAddress.setCountry("");
        userAddress.setState("");
        userAddress.setZip("");
        user.setAddress(userAddress);


        BookingAppointmentUserDetails bookingAppointmentUserDetails=new BookingAppointmentUserDetails();
        bookingAppointmentUserDetails.setAppointment(appointmentBookingModel);
        bookingAppointmentUserDetails.setUser(user);

        if (mCommonProgressDialog == null) {
            mCommonProgressDialog = ResvUtils.createProgressDialog(this);
            mCommonProgressDialog.show();
            mCommonProgressDialog.setMessage("Please wait...");
            mCommonProgressDialog.setCancelable(false);
        } else {
            mCommonProgressDialog.show();
        }
        Call<Response> response = apiService.createAcountAndAppointemnt(getString(R.string.token), bookingAppointmentUserDetails);
        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<com.effone.mobile.model.Response> call, retrofit2.Response<Response> rawResponse) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                try {

                    if (rawResponse.body().getResult().getID() != null) {
// Toast.makeText(AppointementBookingActivity.this, "done" + rawResponse.body().getResult().getID(), Toast.LENGTH_SHORT).show();
                        //get your response....
                        //Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + rawResponse.body());
                        Intent intent = new Intent(RegisterActivity.this, AppointmentAcknowledgementActivity.class);
                        intent.putExtra(getString(R.string.confirmation_no), rawResponse.body().getResult().getID());
                        startActivity(intent);
                        finish();
                    } else {
                        ResvUtils.createErrorAlert(RegisterActivity.this, getString(R.string.error), "" + rawResponse.message());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 5000);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<com.effone.mobile.model.Response> call, Throwable throwable) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + throwable.toString());
            }
        });

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
           if(view.getId() == R.id.et_email){
               checkingEmail(mEtEmail.getText().toString().trim());

           }

            // If view having focus.
        }
    }

    private void checkingEmail(String text) {
        Call<Response> response = apiService.getEmailExists(getString(R.string.token),text );
        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> rawResponse) {
                try {
                    if (rawResponse.body().getResult().getID() != null) {
                        Toast.makeText(RegisterActivity.this,"UserExit",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this,"NoUserExit",Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable throwable) {
                // other stuff...
                Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + throwable.toString());
            }
        });

    }
    TitleNames  mTitleNames;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mTitleNames = (TitleNames) mSpTitle.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
