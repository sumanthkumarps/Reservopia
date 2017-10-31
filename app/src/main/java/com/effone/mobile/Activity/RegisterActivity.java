package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;


import java.io.IOException;
import java.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
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

import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.adapter.TitleAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.common.Validation;
import com.effone.mobile.model.AppointmentBookingModel;
import com.effone.mobile.model.BookingAppointmentUserDetails;
import com.effone.mobile.model.Response;
import com.effone.mobile.model.TitleNames;
import com.effone.mobile.model.User;
import com.effone.mobile.model.UserAddress;
import com.effone.mobile.model.UserDetailGet;
import com.effone.mobile.model.UserDetails;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.TypeAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmResults;
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
            mSttitle,mStGender="male",mStAddress,mStZip,mStState,mStPassword,mStConfirmPassword,mStCity;
    TextView mTvTitle;
    ApiInterface apiService;
    private CheckBox mCbCreateAccount;
    private Spinner mSpTitle;
   private AppointmentBookingModel appointmentBookingModel;
    private ProgressDialog mCommonProgressDialog;
    private RelativeLayout mLinearLayout;
    ToggleButton male;
    ToggleButton female;
    private String mUserId="0";
    boolean isFromHomeScreen;
    private  boolean loginned;
    private  boolean isEditAppointment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ResvUtils.enableBackButton(this);
        ResvUtils.enableHomeButton(this);

        appointmentBookingModel = (AppointmentBookingModel) getIntent().getSerializableExtra("appointment_details");
        isEditAppointment=getIntent().getBooleanExtra("editAppointment",false);
        loginned=getIntent().getBooleanExtra("login",false);
        isFromHomeScreen=getIntent().getBooleanExtra(getString(R.string.fromLogin),false);

        apiService = ApiClient.getClient().create(ApiInterface.class);

            mLinearLayout=(RelativeLayout)findViewById(R.id.lv_password);

        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.register));
        declarations();



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isEditAppointment)
            settingValuesForEdit();

        else
            gettingUserDeatils();
       /* if(!mEtEmail.getText().toString().trim().equals(""))
        checkingEmail(mEtEmail.getText().toString().trim());*/
    }

    private void settingValuesForEdit() {
        mCbCreateAccount.setVisibility(View.GONE);
        mBtSubmit.setText(getString(R.string.edit_appointment));
        mEtEmail.setText(AppPreferene.with(this).getEmail().trim());
        mEtEmail.setEnabled(false);

        try {
        mEtPhone.setText(appointmentBookingModel.getPhone());
        mEtFirstName.setText(appointmentBookingModel.getFirstName());
        mEtLastName.setText(appointmentBookingModel.getLastName());

            mEtDateOfBirth.setText(appointmentBookingModel.getDateOfBirth().split(" ")[0]);

        RealmResults<TitleNames> titleNames = mRealm.where(TitleNames.class).findAll();
        TitleNames titleName = mRealm.where(TitleNames.class).equalTo("Value", appointmentBookingModel.getTitle()).findFirst();
        mSpTitle.setSelection(titleNames.indexOf(titleName));
        if (appointmentBookingModel.getGender().equals("male"))
            male.setChecked(true);
        else
            female.setChecked(true);
        }catch (Exception e){
            Log.e("Sumanth",e.getMessage());
        }

    }

    private void gettingUserDeatils() {
        if(!AppPreferene.with(this).getUserId().equals("")) {
            gettingDetails(AppPreferene.with(RegisterActivity.this).getUserId(), AppPreferene.with(RegisterActivity.this).getEmail());
            mBtSubmit.setText(getString(R.string.booking_app));
        }
        else{
            mBtSubmit.setText(getString(R.string.signUp));
        }
    }

    private void gettingDetails(String user_id, String email) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<UserDetails> call = apiService.getUserDetails(getString(R.string.token),user_id,email);

        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, retrofit2.Response<UserDetails> response) {
                response.raw().request().url();
                if(response.body() != null) {

                    UserDetailGet userDetailGet = response.body().getResult();
                    if (userDetailGet != null) {
                        mEtEmail.setText(userDetailGet.getEmail());
                        mEtEmail.setEnabled(false);
                        mEtPhone.setText(userDetailGet.getPhone());
                        mEtFirstName.setText(userDetailGet.getFirstName());
                        mEtLastName.setText(userDetailGet.getLastName());
                        mEtDateOfBirth.setText(ResvUtils.parseDateToddMMyyyy(userDetailGet.getDateOfBirth().split("T")[0], "yyyy-MM-dd", "MM/dd/yyyy"));
                        mStPassword = userDetailGet.getPassword();
                        RealmResults<TitleNames> titleNames = mRealm.where(TitleNames.class).findAll();
                        TitleNames titleName = mRealm.where(TitleNames.class).equalTo("Value", userDetailGet.getTitle()).findFirst();
                        mSpTitle.setSelection(titleNames.indexOf(titleName));
                        if (userDetailGet.getGender().equals("male"))
                            male.setChecked(true);
                        else
                            female.setChecked(true);


              /*          appointmentBookingModel.setFirstName(userDetailGet.getFirstName());
                        appointmentBookingModel.setLastName(userDetailGet.getLastName());
                        appointmentBookingModel.setTitle(userDetailGet.getTitle());
                        appointmentBookingModel.setGender(userDetailGet.getGender());
                        appointmentBookingModel.setDateOfBirth(userDetailGet.getGender());*/

                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                ResvUtils.createOKAlert(RegisterActivity.this,getString(R.string.error),getString(R.string.something_went_wrong));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!ResvUtils.Operations.isOnline(this)) {
            ResvUtils.Operations.showNoNetworkActivity(this);
        }
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
                    mEtPassword.requestFocus();

                } else {
                    mLinearLayout.setVisibility(View.GONE);
                    mEtPhone.requestFocus();
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
      TitleNames  mTitleName=(TitleNames)mSpTitle.getItemAtPosition(0);
        mSttitle=mTitleName.getValue();
        mEtDateOfBirth.addTextChangedListener(mDateEntryWatcher);
        mEtPassword=(EditText)findViewById(R.id.et_password);
        mEtConfirmPassword=(EditText)findViewById(R.id.et_conf_pass);
        mBtSubmit=(Button)findViewById(R.id.bt_submit);
      /*  mRGGender=(RadioGroup)findViewById(R.id.radioSex);*/
        male = (ToggleButton) findViewById(R.id.tb_male);
        male.setTransformationMethod(null);
        female = (ToggleButton) findViewById(R.id.tb_female);
        female.setTransformationMethod(null);
        male.setOnCheckedChangeListener(changeChecker);
        female.setOnCheckedChangeListener(changeChecker);
        mBtSubmit.setOnClickListener(this);
        mBtSubmit.setTransformationMethod(null);
        mEtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if(AppPreferene.with(RegisterActivity.this).getUserId().equals("")||!text.equals(AppPreferene.with(RegisterActivity.this).getEmail())) {
                    if (!hasFocus) {
                        Validation validation = new Validation();
                        if (validation.isValidEmail(text))
                            checkingEmail(text);
                        else
                            ResvUtils.createOKAlert(RegisterActivity.this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.Emailmsg));
                    } else {
                        mCbCreateAccount.setVisibility(View.GONE);
                        mCbCreateAccount.setChecked(false);
                        mLinearLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == male) {

                    male.setChecked(true);
                    female.setChecked(false);
                    female.setBackground(getDrawable(R.drawable.selector));
                    mStGender = "male";
                }
                if (buttonView == female) {
                    male.setChecked(false);
                    female.setChecked(true);
                    male.setBackground(getDrawable(R.drawable.selector));
                    mStGender="female";

                }
            }else{
                if (buttonView == male) {
                    male.setChecked(false);
                    female.setChecked(true);
                    male.setBackground(getDrawable(R.drawable.selector));
                    mStGender="female";
                }
                if (buttonView == female) {
                    male.setChecked(true);
                    female.setChecked(false);
                    female.setBackground(getDrawable(R.drawable.selector));
                    mStGender = "male";


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
        if(AppPreferene.with(RegisterActivity.this).getUserId().equals("")||!mStEmail.equals(AppPreferene.with(RegisterActivity.this).getEmail()))
        checkingEmail(mStEmail);
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
            if ((mStPassword.length() < 5) || (mStPassword.length() > 16)) {
                mMsg = mMsg + "" + getResources().getString(R.string.passwordmsg) + "\n";
                count++;
            } else if (!validate.isValidPassword(mStPassword)) {
                mMsg = mMsg + "" + getResources().getString(R.string.password) + "" + getResources().getString(R.string.passwordmymsg) + "\n";
                count++;
            }
            if ((mStConfirmPassword.length() < 5) || (mStConfirmPassword.length() > 16)) {
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
            if(mStPassword != null || mStPassword != ""){

            }else
                mStPassword=" ";
        }
        if (count == 0) {
            mMsg = "success";
            mBtSubmit.setEnabled(false);
            if(isFromHomeScreen)
                registerUser();
                else

                    if(AppPreferene.with(this).getUserId().equals(""))
                    sendInformation();
            else {
                        addingUserDetailsToAppointmentBody();
                        bookingAppointmentWithOutUserDetails();
                    }

        }
        if (!mMsg.equals("success")) {
            ResvUtils.createOKAlert(this, getResources().getString(R.string.user_validation), mMsg);

        }



    }

    private void bookingAppointmentWithOutUserDetails() {
        Gson gson =new Gson();
        String msi=gson.toJson(appointmentBookingModel);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<com.effone.mobile.model.Response> response = apiService.createAppointment(getString(R.string.token), appointmentBookingModel);
        response.enqueue(new Callback<com.effone.mobile.model.Response>() {
            @Override
            public void onResponse(Call<com.effone.mobile.model.Response> call, retrofit2.Response<Response> rawResponse) {
                onResponseFromServer(rawResponse);
            }

            @Override
            public void onFailure(Call<com.effone.mobile.model.Response> call, Throwable throwable) {
                // other stuff...
                Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + throwable.toString());
                errorShowingDialog( throwable.getMessage());

            }
        });
    }

    private void registerUser() {
        final User user=new User();
        user.setTitle(mTitleNames.getValue());
        user.setEmail(mStEmail);
        user.setPhone(mStPhone);
        user.setDisplayUserName(null);
        user.setFirstName(mStFirstName);
        user.setLastName(mStLastName);
        user.setPassword(mStPassword);
        user.setGender(mStGender);
        user.setDateOfBirth(mStDateOfBirth);
        user.setIsTempPassword(0);
        user.setPreferredLocID(null);
        user.setPrimaryLocID(null);
        user.setIsActive(1);
        user.setAuditID(0);
        user.setIsTempPassword(0);
        user.setOrgID(1);
        user.setIsEndUser(1);
        UserAddress userAddress=new UserAddress();
        userAddress.setAddressLine1("");
        userAddress.setAddressLine2("");
        userAddress.setAddressLine3("");
        userAddress.setCity("");
        userAddress.setCountry("");
        userAddress.setState("");
        userAddress.setZip("");
        user.setAddress(userAddress);
        user.setIsEndUser(1);
        user.setDisplayUserName("");
        user.setPreferredLocID("");
        user.setPrimaryLocID("");

        Gson gson=new Gson();
        String json =gson.toJson(user);

        if (mCommonProgressDialog == null) {
            mCommonProgressDialog = ResvUtils.createProgressDialog(this);
            mCommonProgressDialog.show();
            mCommonProgressDialog.setMessage("Please wait...");
            mCommonProgressDialog.setCancelable(false);
        } else {
            mCommonProgressDialog.show();
        }
        Call<Response> response = apiService.createAcount(getString(R.string.token), user);
        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<com.effone.mobile.model.Response> call, retrofit2.Response<Response> rawResponse) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                if (!rawResponse.isSuccessful() ) {
                    UserDetails registerResponse=null;
                    Log.d(TAG, "onResponse - Status : " + rawResponse.code());
                    Gson gson = new Gson();
                    TypeAdapter<UserDetails> adapter = gson.getAdapter(UserDetails.class);
                    try {
                        if (rawResponse.errorBody() != null)
                            registerResponse =
                                    adapter.fromJson(rawResponse.errorBody().string());
                        ResvUtils.createOKAlert(RegisterActivity.this, "Error", registerResponse.getMessage());
                    } catch (IOException e) {

                    }
                }else {

                    try {

                        if (rawResponse.body().getResult() != null && rawResponse.body().getResult().getID() != null) {
                            AppPreferene.with(RegisterActivity.this).setUserId(user.getUserID());
                            AppPreferene.with(RegisterActivity.this).setEmail(user.getEmail());
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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
                            mBtSubmit.setEnabled(true);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        mBtSubmit.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<com.effone.mobile.model.Response> call, Throwable throwable) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + throwable.getMessage());
                ResvUtils.createErrorAlert(RegisterActivity.this, getString(R.string.error),  throwable.getMessage() );
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 5000);
                mBtSubmit.setEnabled(true);

            }
        });
    }

    private void sendInformation() {
        addingUserDetailsToAppointmentBody();
        User users=new User();
       UserAddress userAddre=new UserAddress();
        userAddre.setAddressLine1("");
        userAddre.setAddressLine2("");
        userAddre.setAddressLine3("");
        userAddre.setCity("");
        userAddre.setCountry("");
        userAddre.setState("");
        userAddre.setZip("");
        users.setAddress(userAddre);
        if(AppPreferene.with(this).getUserId().equals("")){
            users.setUserID(mUserId);
        }else
            users.setUserID(AppPreferene.with(this).getUserId());
        users.setTitle(mTitleNames.getValue());
        users.setEmail(mStEmail);
        users.setPhone(mStPhone);
        users.setDisplayUserName(null);
        users.setFirstName(mStFirstName);
        users.setLastName(mStLastName);
        if(mStPassword == null){
            users.setPassword(null);
        }else
        users.setPassword(mStPassword);
        users.setGender(mStGender);
        users.setDateOfBirth(mStDateOfBirth);
        users.setIsTempPassword(0);
        users.setPreferredLocID(null);
        users.setPrimaryLocID(null);
        users.setIsActive(1);
        users.setAuditID(0);
        users.setIsTempPassword(0);
        users.setOrgID(Integer.parseInt(getString(R.string.org_id)));
        users.setIsEndUser(1);
        users.setDisplayUserName("");
        users.setPreferredLocID("");
        users.setPrimaryLocID("");


        Gson gson=new GsonBuilder()
                .serializeNulls()
                .create();
        String jsons =gson.toJson(users);
        BookingAppointmentUserDetails bookingAppointmentUserDetails=new BookingAppointmentUserDetails();
        appointmentBookingModel.setUserID(users.getUserID());
        bookingAppointmentUserDetails.setAppointment(appointmentBookingModel);
        bookingAppointmentUserDetails.setUser(users);

        String json =gson.toJson(bookingAppointmentUserDetails);

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
                onResponseFromServer(rawResponse);
            }

            @Override
            public void onFailure(Call<com.effone.mobile.model.Response> call, Throwable throwable) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                errorShowingDialog( throwable.getMessage());
            }
        });

    }

    private void onResponseFromServer(retrofit2.Response<Response> rawResponse) {
        if (mCommonProgressDialog != null)
            mCommonProgressDialog.cancel();
        if (!rawResponse.isSuccessful() ) {
            UserDetails registerResponse=null;
            Log.d(TAG, "onResponse - Status : " + rawResponse.code());
            Gson gson = new Gson();
            TypeAdapter<UserDetails> adapter = gson.getAdapter(UserDetails.class);
            try {
                if (rawResponse.errorBody() != null)
                    registerResponse =
                            adapter.fromJson(rawResponse.errorBody().string());
                ResvUtils.createOKAlert(RegisterActivity.this, "Error", registerResponse.getMessage(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

            } catch (IOException e) {
                errorShowingDialog( e.getMessage());
            }
        }
        else {
            try {

                if (rawResponse.body().getResult() != null && rawResponse.body().getResult().getID() != null) {
// Toast.makeText(AppointementBookingActivity.this, "done" + rawResponse.body().getResult().getID(), Toast.LENGTH_SHORT).show();
                    //get your response....
                    //Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + rawResponse.body());

                    Intent intent = new Intent(RegisterActivity.this, AppointmentAcknowledgementActivity.class);
                    intent.putExtra(getString(R.string.confirmation_no), rawResponse.body().getResult().getID());
                    intent.putExtra(getString(R.string.password), !mEtPassword.getText().toString().equals(""));
                    startActivity(intent);
                    finish();
                } else if (rawResponse.body().getMessage() != null) {
                    ResvUtils.createErrorAlert(RegisterActivity.this, getString(R.string.error), "" + rawResponse.body().getMessage());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 5000);
                    mBtSubmit.setEnabled(true);
                }


            } catch (Exception e) {
                e.printStackTrace();
                mBtSubmit.setEnabled(true);
            }
        }
    }

    private void errorShowingDialog(String message) {
        ResvUtils.createErrorAlert(RegisterActivity.this, getString(R.string.error), message );
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);
        Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + message);
    }

    private void addingUserDetailsToAppointmentBody() {
            appointmentBookingModel.setFirstName(mStFirstName);
            appointmentBookingModel.setLastName(mStLastName);
            if(mSttitle == null)
                appointmentBookingModel.setTitle("1");
            else
                appointmentBookingModel.setTitle(mSttitle);
            appointmentBookingModel.setGender(mStGender);
            appointmentBookingModel.setPhone(mStPhone);
            appointmentBookingModel.setDateOfBirth(mStDateOfBirth);
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

    private void checkingEmail(final String text) {
        Call<Response> response = apiService.getEmailExists(getString(R.string.token),text,getString(R.string.org_id) );
        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> rawResponse) {
                try{

                    if (rawResponse.body().getResult().getID() != null)  {

                            if (rawResponse.body().getResult().getID().equals("0")) {

                                mCbCreateAccount.setVisibility(View.VISIBLE);

                            } else {
                                if (rawResponse.body().getResult().getOperation().equals("1")&&rawResponse.body().getResult().getUserType().equals("3")) {
                                    mCbCreateAccount.setVisibility(View.GONE);
                                    ResvUtils.createYesOrNoDialog(RegisterActivity.this, "Email id exists.\nDo you want to login?\n ", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            switch (id) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class).putExtra("email", text));
                                                    finish();
                                                    break;

                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    dialog.cancel();
                                                    break;
                                            }
                                        }
                                    });
                                }
                                else{
                                    mCbCreateAccount.setVisibility(View.VISIBLE);
                                    mUserId=rawResponse.body().getResult().getID();
                            }

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
    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            boolean isValidYear=true;
            if (working.length()==2 && before ==0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
                    isValid = false;
                } else {
                    working+="/";
                    mEtDateOfBirth.setText(working);
                    mEtDateOfBirth.setSelection(working.length());
                }
            }
            else if(working.length()==5 && before ==0){
                String month=working.substring(3);
                if ( Integer.parseInt(month)>31) {
                    isValid = false;
                } else {
                    working+="/";
                    mEtDateOfBirth.setText(working);
                    mEtDateOfBirth.setSelection(working.length());
                }
            }
            else if (working.length()==10 && before ==0) {
                String enteredYear = working.substring(6);
                int currentYear=Calendar.getInstance().get(Calendar.YEAR);
                int minYear=currentYear-120;
                int intEnterYear=Integer.parseInt(enteredYear);
                if (intEnterYear > currentYear || intEnterYear<minYear) {
                    isValidYear=false;
                }
            } else if (working.length()!=10) {
                isValid = false;
            }

            if (!isValid||!isValidYear) {
                if(!isValidYear)
                    mEtDateOfBirth.setError("Invalid Year");
                else
                    mEtDateOfBirth.setError("Invalid Date.Format is mm/dd/yyyy. Eg.04/09/1965");
            } else {
                mEtDateOfBirth.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };









}
