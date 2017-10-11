package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.effone.mobile.R;
import com.effone.mobile.adapter.TitleAdapter;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.common.Validation;
import com.effone.mobile.model.AppointmentBookingModel;
import com.effone.mobile.model.BookingAppointmentUserDetails;
import com.effone.mobile.model.Response;
import com.effone.mobile.model.TitleNames;
import com.effone.mobile.model.User;
import com.effone.mobile.model.UserAddress;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sarith.vasu on 26-09-2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
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
    private RelativeLayout mLinearLayout;
    ToggleButton male;
    ToggleButton female;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ResvUtils.enableBackButton(this);
        ResvUtils.enableHomeButton(this);

        appointmentBookingModel = (AppointmentBookingModel) getIntent().getSerializableExtra("appointment_details");
        apiService = ApiClient.getClient().create(ApiInterface.class);

            mLinearLayout=(RelativeLayout)findViewById(R.id.lv_password);

        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.register));
        declarations();
    }
    private Realm mRealm;
    private void declarations() {
        mRealm= Realm.getDefaultInstance();
        mEtEmail=(EditText)findViewById(R.id.et_email);
      //  mEtEmail.setOnFocusChangeListener(this);
        mCbCreateAccount=(CheckBox)findViewById(R.id.cb_account);
        mCbCreateAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if (isChecked) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayout.setVisibility(View.GONE);
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

        mEtPassword=(EditText)findViewById(R.id.et_password);
        mEtConfirmPassword=(EditText)findViewById(R.id.et_conf_pass);
        mBtSubmit=(Button)findViewById(R.id.bt_submit);
      /*  mRGGender=(RadioGroup)findViewById(R.id.radioSex);*/
        male = (ToggleButton) findViewById(R.id.tb_male);
        female = (ToggleButton) findViewById(R.id.tb_female);
        male.setOnCheckedChangeListener(changeChecker);
        female.setOnCheckedChangeListener(changeChecker);
        mBtSubmit.setOnClickListener(this);
        mEtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    EditText editText = (EditText) v;
                    String text = editText.getText().toString();
                    Validation validation=new Validation();
                    if(validation.isValidEmail(text))
                    checkingEmail(text);
                    else
                        ResvUtils.createOKAlert(RegisterActivity.this, getResources().getString(R.string.headercreateaccount),  getResources().getString(R.string.Emailmsg) );
                }else{
                    mCbCreateAccount.setVisibility(View.GONE);
                    mLinearLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == male) {
                    male.setChecked(true);
                    female.setChecked(false);
                    mStGender="male";
                }
                if (buttonView == female) {
                    male.setChecked(false);
                    female.setChecked(true);
                    mStGender="female";
                }
            }
        }
    };

    private RadioButton radioSexButton;
    @Override
    public void onClick(View view) {
        mStEmail=mEtEmail.getText().toString().trim();
        mStPhone=mEtPhone.getText().toString().trim();
        mStFirstName=mEtFirstName.getText().toString().trim();
        mStLastName=mEtLastName.getText().toString().trim();

        mStDateOfBirth=mEtDateOfBirth.getText().toString().trim();



      /*  int selectedId = mRGGender.getCheckedRadioButtonId();
        radioSexButton=(RadioButton) findViewById(selectedId);*/

        int count = 0;
        String mMsg = "";

        Validation validate = new Validation();
        if (!validate.isValidFirstName(mStFirstName)) {
            mMsg = mMsg + "" + getResources().getString(R.string.firstnamemsg) + "\n";
            count++;
        }
        if (!validate.isValidFirstName(mStLastName)) {
            mMsg = mMsg + "" + getResources().getString(R.string.lastnamemsg) + "\n";
            count++;
        }
        if (!validate.isValidDate(mStDateOfBirth)) {
            mMsg = mMsg + "" + getResources().getString(R.string.DateofBirthmsg) + "\n";
            count++;
        }
        if (!validate.isValidPhone(mStPhone)) {
            mMsg = mMsg + "" + getResources().getString(R.string.Phonemsg) + "\n";
            count++;
        }

        if (!validate.isValidEmail(mStEmail)) {
            mMsg = mMsg + "" + getResources().getString(R.string.Emailmsg) + "\n";
            count++;
        }
        if(mLinearLayout.getVisibility() == View.VISIBLE) {
            mStPassword=mEtPassword.getText().toString().trim();
            mStConfirmPassword=mEtConfirmPassword.getText().toString().trim();
            if ((mStPassword.length() < 7) || (mStPassword.length() > 16)) {
                mMsg = mMsg + "" + getResources().getString(R.string.passwordmsg) + "\n";
                count++;
            } else if (!validate.isValidPassword(mStPassword)) {
                mMsg = mMsg + "" + getResources().getString(R.string.password) + "" + getResources().getString(R.string.passwordmymsg) + "\n";
                count++;
            }
            if ((mStConfirmPassword.length() < 7) || (mStConfirmPassword.length() > 16)) {
                mMsg = mMsg + "" + getResources().getString(R.string.passwordmsg2) + "\n";
                count++;
            } else if (!validate.isValidPassword(mStConfirmPassword)) {
                mMsg = mMsg + "Confirm password" + getResources().getString(R.string.passwordmymsg) + "\n";
                count++;
            } else if (!mStPassword.equals(mStConfirmPassword)) {
                mMsg = mMsg + "" + getResources().getString(R.string.passworddoednotmatch) + "\n";
                count++;
            }
        }else{
            mStPassword="";
        }
        if (count == 0) {
            mMsg = "success";

                sendInformation();

        }
        if (!mMsg.equals("success")) {
            ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), mMsg);

        }



    }

    private void sendInformation() {
        User user=new User();
        user.setUserID("0");
        user.setTitle(mTitleNames.getValue());
        user.setEmail(mStEmail);
        user.setPhone(mStPhone);
        user.setDisplayUserName(null);
        user.setFirstName(mStFirstName);
        user.setLastName(mStLastName);
        user.setPassword(mStPassword);
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

/*    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
           if(view.getId() == R.id.et_email){
               checkingEmail(mEtEmail.getText().toString().trim());

           }

            // If view having focus.
        }
    }*/

    private void checkingEmail(String text) {
        Call<Response> response = apiService.getEmailExists(getString(R.string.token),text,getString(R.string.org_id) );
        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> rawResponse) {
                try{

                    if (rawResponse.body().getResult().getID() != null)  {
                        if(rawResponse.body().getResult().getID().equals("0")) {
                            mCbCreateAccount.setVisibility(View.VISIBLE);
                        }else{
                                mCbCreateAccount.setVisibility(View.GONE);
                               ResvUtils.createOKAlert(RegisterActivity.this, "Trying to sign in? ", "Someone's already using that email.If that’s you, please login.", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                             /*       Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    startActivity(i);*/
                                }
                            });
                        }
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
