package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;

import java.io.IOException;
import java.util.Calendar;

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
    private EditText mEtEmail, mEtPhone, mEtFirstName, mEtLastName, mEtDateOfBirth,
            mEtAddress, mEtZip, mEtState, mEtPassword, mEtConfirmPassword, mEtCity;
    private RadioGroup mRGGender;
    private Button mBtSubmit;
    private String mStEmail, mStPhone, mStFirstName, mStLastName, mStDateOfBirth,
            mSttitle, mStGender = "male", mStAddress, mStZip, mStState, mStPassword, mStConfirmPassword, mStCity;
    TextView mTvTitle;
    ApiInterface apiService;
    private CheckBox mCbCreateAccount;
    private Spinner mSpTitle;
    private AppointmentBookingModel appointmentBookingModel;
    private ProgressDialog mCommonProgressDialog;
    private RelativeLayout mLinearLayout;
    ToggleButton male;
    ToggleButton female;
    private String mUserId = "0";
    boolean isFromHomeScreen,isEditProfile;
    private boolean loginned;
    private boolean isEditAppointment;
    private TextInputLayout mTvFirstName, mTvLastName, mTvDob, mTvEmail, mTvPhone, mTvPassword, mTvConfirmPassword;
    private TitleAdapter tileAdapter;
    private boolean checked;
    private String temEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ResvUtils.enableBackButton(this);
        ResvUtils.enableHomeButton(this);

        appointmentBookingModel = (AppointmentBookingModel) getIntent().getSerializableExtra("appointment_details");
        isEditAppointment = getIntent().getBooleanExtra("editAppointment", false);
        loginned = getIntent().getBooleanExtra("login", false);
        isFromHomeScreen = getIntent().getBooleanExtra(getString(R.string.fromLogin), false);
        isEditProfile=getIntent().getBooleanExtra(getString(R.string.edit_profile),false);
        mTvFirstName = (TextInputLayout) findViewById(R.id.tv_error_name);
        mTvLastName = (TextInputLayout) findViewById(R.id.tv_error_lastname);
        mTvDob = (TextInputLayout) findViewById(R.id.tv_error_date);
        mTvEmail = (TextInputLayout) findViewById(R.id.tv_error_email);
        mTvPhone = (TextInputLayout) findViewById(R.id.tv_error_phone);
        mTvPassword = (TextInputLayout) findViewById(R.id.tv_error_password);
        mTvConfirmPassword = (TextInputLayout) findViewById(R.id.tv_error_conform);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        mLinearLayout = (RelativeLayout) findViewById(R.id.lv_password);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.register));
        declarations();
        if(isFromHomeScreen){
            mLinearLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBtSubmit.setEnabled(true);
        try {
            mEtEmail = (EditText) findViewById(R.id.et_email);
            mCbCreateAccount = (CheckBox) findViewById(R.id.cb_account);
            if (isEditAppointment)
                settingValuesForEdit();
            else
                gettingUserDeatils();
            if (mEtEmail.getText().toString().trim().equals(""))
                mCbCreateAccount.setVisibility(View.GONE);
            if(isEditProfile){
                mTvTitle.setText("Update details ");
                mBtSubmit.setText("Update");
            }
        }
        catch (Exception e){
            Log.e("",""+e);
        }

       /* if(!mEtEmail.getText().toString().trim().equals(""))
        checkingEmail(mEtEmail.getText().toString().trim());*/
    }

    private void settingValuesForEdit() {
        mCbCreateAccount.setVisibility(View.GONE);
        mTvTitle.setText(getString(R.string.edit_personal_info));
        mBtSubmit.setText(getString(R.string.booking_app));
        mEtEmail.setText(appointmentBookingModel.getEmail());
        mEtEmail.setEnabled(false);

        try {
            mEtPhone.setText(appointmentBookingModel.getPhone());
            mEtFirstName.setText(appointmentBookingModel.getFirstName());
            mEtLastName.setText(appointmentBookingModel.getLastName());

            mEtDateOfBirth.setText(appointmentBookingModel.getDateOfBirth().split(" ")[0]);

            RealmResults<TitleNames> titleNames = mRealm.where(TitleNames.class).findAll();
            TitleNames titleName = mRealm.where(TitleNames.class).equalTo("Value", appointmentBookingModel.getTitle()).findFirst();
            int positio=titleNames.indexOf(titleName);
        mSpTitle.setSelection(positio,true);
           // selectSpinnerValue(mSpTitle, titleName.getText());
            if (appointmentBookingModel.getGender().equals("male"))
                male.setChecked(true);
            else
                female.setChecked(true);
        } catch (Exception e) {
            Log.e("Sumanth", e.getMessage());
        }

    }

    private void selectSpinnerValue(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            String titleNames = spinner.getItemAtPosition(i).toString();
            if (titleNames.equals(myString)) {
                spinner.setSelection(i,true);
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        View views = this.getCurrentFocus();
        if (views != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(views.getWindowToken(), 0);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void gettingUserDeatils() {

        if (!AppPreferene.with(this).getUserId().equals("")) {
            gettingDetails(AppPreferene.with(RegisterActivity.this).getUserId(), AppPreferene.with(RegisterActivity.this).getEmail());
            if(!isFromHomeScreen){
                mBtSubmit.setText(getString(R.string.booking_app));
            }else
            mBtSubmit.setText(getString(R.string.signUp));
        }
        else if(AppPreferene.with(this).getUserId().equals("")&&!AppPreferene.with(this).getEmail().equals("")){
            if(!isFromHomeScreen){
                mBtSubmit.setText(getString(R.string.booking_app));
            }else
            mBtSubmit.setText(getString(R.string.signUp));
        }
        else {
            mBtSubmit.setEnabled(true);
            if (appointmentBookingModel != null)
                mBtSubmit.setText(getString(R.string.sign_up_and_book_appoint));
            else
                mBtSubmit.setText(getString(R.string.signUp));
        }
    }

    private void gettingDetails(String user_id, String email) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<UserDetails> call = apiService.getUserDetails(getString(R.string.token), user_id, email);

        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, retrofit2.Response<UserDetails> response) {
                response.raw().request().url();
                mBtSubmit.setEnabled(true);
                if (response.body() != null) {

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
                        selectSpinnerValue(mSpTitle, titleName.getText());
                        if (userDetailGet.getGender().equals("male"))
                            male.setChecked(true);
                        else
                            female.setChecked(true);



                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                mBtSubmit.setEnabled(true);
                ResvUtils.createOKAlert(RegisterActivity.this, getString(R.string.error), getString(R.string.something_went_wrong));
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
        mRealm = Realm.getDefaultInstance();
        mEtEmail = (EditText) findViewById(R.id.et_email);
        //  mEtEmail.setOnFocusChangeListener(this);
        mCbCreateAccount = (CheckBox) findViewById(R.id.cb_account);
        mCbCreateAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                    mEtPassword.requestFocus();

                } else {
                    mLinearLayout.setVisibility(View.GONE);
                    mEtPhone.requestFocus();
                }
            }
        });
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtFirstName = (EditText) findViewById(R.id.et_firstname);
        mEtLastName = (EditText) findViewById(R.id.et_lastname);
        mEtDateOfBirth = (EditText) findViewById(R.id.et_date_birth);
        mSpTitle = (Spinner) findViewById(R.id.et_title);
        tileAdapter = new TitleAdapter(this, mRealm.where(TitleNames.class).findAll());
        mSpTitle.setAdapter(tileAdapter);
        mSpTitle.setOnItemSelectedListener(this);
        TitleNames mTitleName = (TitleNames) mSpTitle.getItemAtPosition(0);
        mSttitle = mTitleName.getValue();
        mEtDateOfBirth.addTextChangedListener(mDateEntryWatcher);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtConfirmPassword = (EditText) findViewById(R.id.et_conf_pass);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
      /*  mRGGender=(RadioGroup)findViewById(R.id.radioSex);*/
        male = (ToggleButton) findViewById(R.id.tb_male);
        male.setTransformationMethod(null);
        female = (ToggleButton) findViewById(R.id.tb_female);
        female.setTransformationMethod(null);
        male.setOnCheckedChangeListener(changeChecker);
        female.setOnCheckedChangeListener(changeChecker);
        mBtSubmit.setOnClickListener(this);
        mBtSubmit.setTransformationMethod(null);
        touchOnEdit(mEtPassword);


        mEtConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (mEtConfirmPassword.getWidth() - mEtConfirmPassword
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!isShow) {
                            mEtConfirmPassword.setTransformationMethod(null);
                            isShow = true;
                            mEtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_black_24dp, 0);
                        } else {
                            mEtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                            mEtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_black_24dp, 0);
                            isShow = false;
                        }
                        mEtConfirmPassword.setSelection(mEtConfirmPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
        mEtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if (AppPreferene.with(RegisterActivity.this).getUserId().equals("") || !text.equals(AppPreferene.with(RegisterActivity.this).getEmail())) {
                    if (!hasFocus) {
                        if(!text.equals(temEmail))
                            checked=false;
                        Validation validation = new Validation();
                        if(text.length()!=0)
                        if (validation.isValidEmail(text)) {
                            if(!checked)
                            checkingEmail(text);
                            mTvEmail.setErrorEnabled(false);
                        } else
                            mTvEmail.setError(getString(R.string.email_error_msg_valid));
                        else
                            mTvEmail.setError(getString(R.string.email_error_msg));

                    } else {
                        if(!checked) {
                            mCbCreateAccount.setVisibility(View.GONE);
                            mCbCreateAccount.setChecked(false);
                            if (!isFromHomeScreen)
                                mLinearLayout.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        final Validation validate = new Validation();
        mEtFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if (!validate.isValidFirstName(text))
                    mTvFirstName.setError(getString(R.string.first_error_msg));
                else
                    mTvFirstName.setErrorEnabled(false);

            }
        });

        mEtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if (!validate.isValidFirstName(text)) {
                    mTvLastName.setError(getString(R.string.last_error_msg));
                } else {
                    mTvLastName.setErrorEnabled(false);
                }
            }
        });

        mEtDateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if (!validate.isValidDate(text)) {
                    mTvDob.setError(getString(R.string.dobMsg));
                } else {
                    mTvDob.setErrorEnabled(false);
                }
            }
        });
        mEtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if (!validate.isValidPhone(text)) {
                    mTvPhone.setError(getString(R.string.phone_error_msg));
                } else {
                    mTvPhone.setErrorEnabled(false);
                }
            }
        });
        mEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if (!validate.isValidPassword(text)) {
                    mTvPassword.setError(getString(R.string.password_error_msg));
                } else {
                    mTvPassword.setErrorEnabled(false);
                }
            }
        });
        mEtConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if (!validate.isValidPassword(text)) {
                    mTvConfirmPassword.setError(getString(R.string.re_password_mssg));
                } else {
                    mTvConfirmPassword.setErrorEnabled(false);
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
                    mStGender = "female";

                }
            } else {
                if (buttonView == male) {
                    male.setChecked(false);
                    female.setChecked(true);
                    male.setBackground(getDrawable(R.drawable.selector));
                    mStGender = "female";
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
    private boolean isShow;

    public void touchOnEdit(final EditText passwordField) {
        passwordField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (passwordField.getWidth() - passwordField
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!isShow) {
                            passwordField.setTransformationMethod(null);
                            isShow = true;
                            passwordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_black_24dp, 0);
                        } else {
                            passwordField.setTransformationMethod(new PasswordTransformationMethod());
                            passwordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_black_24dp, 0);
                            isShow = false;
                        }
                        passwordField.setSelection(passwordField.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private RadioButton radioSexButton;
    Boolean misMatch=false;
    @Override
    public void onClick(View view) {

        mStEmail = mEtEmail.getText().toString().trim();
        mStPhone = mEtPhone.getText().toString().trim();
        mStFirstName = mEtFirstName.getText().toString().trim();
        mStLastName = mEtLastName.getText().toString().trim();
        mStDateOfBirth = mEtDateOfBirth.getText().toString().trim();



      /*  int selectedId = mRGGender.getCheckedRadioButtonId();
        radioSexButton=(RadioButton) findViewById(selectedId);*/

        int count = 0;
        String mMsg = "";
     /*   if(AppPreferene.with(RegisterActivity.this).getUserId().equals("")||!mStEmail.equals(AppPreferene.with(RegisterActivity.this).getEmail()))
        checkingEmail(mStEmail);*/
        Validation validate = new Validation();
        if (!validate.isValidFirstName(mStFirstName)) {
           mTvFirstName.setError(getResources().getString(R.string.firstnamemsg));
            count++;
        }
        if (!validate.isValidFirstName(mStLastName)) {
            mTvLastName.setError(getString(R.string.last_error_msg));
            count++;
        }
        if (!validate.isValidDate(mStDateOfBirth)||mTvDob.getError()!=null) {
        mTvDob.setError(getString(R.string.dobMsg));
            count++;
        }
        if (!validate.isValidPhone(mStPhone)) {
            mTvPhone.setError(getString(R.string.phone_error_msg));
            count++;
        }
        if(mStEmail.length()!=0) {
            if (!validate.isValidEmail(mStEmail)) {
                mTvEmail.setError(getString(R.string.email_error_msg_valid));
                count++;
            }
        }else{
            mTvEmail.setError(getString(R.string.email_error_msg));
            count++;
        }

        if (mLinearLayout.getVisibility() == View.VISIBLE) {
            mStPassword = mEtPassword.getText().toString().trim();
            mStConfirmPassword = mEtConfirmPassword.getText().toString().trim();
            if ((mStPassword.length() < 5) || (mStPassword.length() > 16)) {
                mTvPassword.setError(getString(R.string.password_error_msg));
                count++;
            } else if (!validate.isValidPassword(mStPassword)) {
                mTvPassword.setError(getString(R.string.password_error_msg));
                count++;
            }
            if ((mStConfirmPassword.length() < 5) || (mStConfirmPassword.length() > 16)) {
                mTvConfirmPassword.setError(getString(R.string.re_password_mssg));
                count++;
            } else if (!validate.isValidPassword(mStConfirmPassword)) {
                mTvConfirmPassword.setError(getString(R.string.re_password_mssg));
                count++;
            } else if (!mStPassword.equals(mStConfirmPassword)) {
                mMsg = mMsg + "" + getResources().getString(R.string.didnotmatch_error_msg) + "\n";
                misMatch=true;
                count++;
            }
        } else {
            if (mStPassword != null || mStPassword != "") {

            } else
                mStPassword = "";
        }
        if (count == 0) {
            mMsg = "success";

            if (isFromHomeScreen)
                registerUser();
            else if(isEditProfile)
                registerUser();
                else
            if (AppPreferene.with(this).getUserId().equals("")) {
                mBtSubmit.setEnabled(false);
                if(isEditAppointment)
                    mUserId=appointmentBookingModel.getUserID();
                sendInformation();

            }
            else {
                mBtSubmit.setEnabled(false);
                addingUserDetailsToAppointmentBody();
                bookingAppointmentWithOutUserDetails();
            }

        }
        if (!mMsg.equals("success")) {
            if(misMatch)
            ResvUtils.createOKAlert(this, getResources().getString(R.string.user_validation), mMsg);
            misMatch=false;

        }


    }

    private void bookingAppointmentWithOutUserDetails() {
        mCommonProgressDialog = ResvUtils.createProgressDialog(this);
        mCommonProgressDialog.show();
        mCommonProgressDialog.setMessage("Please wait...");
        mCommonProgressDialog.setCancelable(false);
        Gson gson = new Gson();
        String msi = gson.toJson(appointmentBookingModel);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<com.effone.mobile.model.Response> response = apiService.createAppointment(getString(R.string.token), appointmentBookingModel);
        response.enqueue(new Callback<com.effone.mobile.model.Response>() {
            @Override
            public void onResponse(Call<com.effone.mobile.model.Response> call, retrofit2.Response<Response> rawResponse) {
                mBtSubmit.setEnabled(true);
                onResponseFromServer(rawResponse);
            }

            @Override
            public void onFailure(Call<com.effone.mobile.model.Response> call, Throwable throwable) {
                // other stuff...
                Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + throwable.toString());
                mBtSubmit.setEnabled(true);
                errorShowingDialog(throwable.getMessage());

            }
        });
    }

    private void registerUser() {
        final User user = new User();
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
        if(isEditProfile)
           user.setUserID(AppPreferene.with(this).getUserId());
        else
            user.setUserID(mUserId);

        user.setIsEndUser(1);
        UserAddress userAddress = new UserAddress();
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

        Gson gson = new Gson();
        String json = gson.toJson(user);

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
                mBtSubmit.setEnabled(true);
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                if (!rawResponse.isSuccessful()) {
                    UserDetails registerResponse = null;
                    Log.d(TAG, "onResponse - Status : " + rawResponse.code());
                    Gson gson = new Gson();
                    TypeAdapter<UserDetails> adapter = gson.getAdapter(UserDetails.class);
                    try {
                        if (rawResponse.errorBody() != null)
                            registerResponse =adapter.fromJson(rawResponse.errorBody().string());
                        ResvUtils.createOKAlert(RegisterActivity.this, "Error", registerResponse.getMessage());
                    } catch (IOException e) {

                    }
                } else {

                    try {

                        if (rawResponse.body().getResult() != null && rawResponse.body().getResult().getID() != null) {
                            AppPreferene.with(RegisterActivity.this).setUserId(rawResponse.body().getResult().getID());
                            AppPreferene.with(RegisterActivity.this).setEmail(user.getEmail());
                            if(isEditProfile)
                                alertMsgForCreateUserSuccess(MainActivity.class,getString(R.string.details_updated));
                                else
                            alertMsgForCreateUserSuccess(MainActivity.class,getString(R.string.account_create_msg));

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
                ResvUtils.createErrorAlert(RegisterActivity.this, getString(R.string.error), throwable.getMessage());
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

    private void alertMsgForCreateUserSuccess(final Class<?> calledClass, String msg) {
        ResvUtils.createOKAlert(RegisterActivity.this, "",msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RegisterActivity.this, calledClass);
                startActivity(intent);
                finish();
            }
        });
    }

    private void sendInformation() {
        addingUserDetailsToAppointmentBody();
        User users = new User();
        UserAddress userAddre = new UserAddress();
        userAddre.setAddressLine1("");
        userAddre.setAddressLine2("");
        userAddre.setAddressLine3("");
        userAddre.setCity("");
        userAddre.setCountry("");
        userAddre.setState("");
        userAddre.setZip("");
        users.setAddress(userAddre);
        if (AppPreferene.with(this).getUserId().equals("")) {
            users.setUserID(mUserId);
        } else
            users.setUserID(AppPreferene.with(this).getUserId());
        users.setTitle(mTitleNames.getValue());
        users.setEmail(mStEmail);
        users.setPhone(mStPhone);
        users.setDisplayUserName(null);
        users.setFirstName(mStFirstName);
        users.setLastName(mStLastName);
        if (mStPassword == null) {
            users.setPassword(null);
        } else
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


        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        String jsons = gson.toJson(users);
        BookingAppointmentUserDetails bookingAppointmentUserDetails = new BookingAppointmentUserDetails();
        appointmentBookingModel.setUserID(users.getUserID());
        bookingAppointmentUserDetails.setAppointment(appointmentBookingModel);
        bookingAppointmentUserDetails.setUser(users);

        String json = gson.toJson(bookingAppointmentUserDetails);

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
                mBtSubmit.setEnabled(true);
                errorShowingDialog(throwable.getMessage());
            }
        });

    }

    private void onResponseFromServer(final retrofit2.Response<Response> rawResponse) {
        mBtSubmit.setEnabled(true);
        if (mCommonProgressDialog != null)
            mCommonProgressDialog.cancel();
        if (!rawResponse.isSuccessful()) {

            UserDetails registerResponse = null;
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
                errorShowingDialog(e.getMessage());
            }
        } else {
            try {

                if (rawResponse.body().getResult() != null && rawResponse.body().getResult().getID() != null) {
// Toast.makeText(AppointementBookingActivity.this, "done" + rawResponse.body().getResult().getID(), Toast.LENGTH_SHORT).show();
                    //get your response....
                    //Log.e(TAG, "RetroFit2.0 :RetroGetLogin: " + rawResponse.body());
                    final String id=rawResponse.body().getResult().getID();
                    if(AppPreferene.with(this).getUserId().equals("")&&!isEditAppointment && mStPassword!=null)
                    {
                        alertMSgForConfirmation(rawResponse.body().getResult().getID(),getString(R.string.account_create_msg));
                    }else {
                        Intent intent = new Intent(RegisterActivity.this, AppointmentAcknowledgementActivity.class);
                        intent.putExtra(getString(R.string.confirmation_no), id);
                        intent.putExtra(getString(R.string.password), !mEtPassword.getText().toString().equals(""));
                        startActivity(intent);
                        finish();
                    }


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

    private void alertMSgForConfirmation(final String id, String  msg) {
        ResvUtils.createOKAlert(RegisterActivity.this, "", msg ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RegisterActivity.this, AppointmentAcknowledgementActivity.class);
                intent.putExtra(getString(R.string.confirmation_no), id);
                intent.putExtra(getString(R.string.password), !mEtPassword.getText().toString().equals(""));
                startActivity(intent);
                finish();
            }
        });
    }

    private void errorShowingDialog(String message) {
        ResvUtils.createErrorAlert(RegisterActivity.this, getString(R.string.error), message);
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
        if (mSttitle == null)
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

        if(!temEmail.equals(text)) {
            temEmail = text;
            checked = true;
        }
        else
            checked=false;
        Call<Response> response = apiService.getEmailExists(getString(R.string.token), text, getString(R.string.org_id));
        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> rawResponse) {
                try {

                    if (rawResponse.body().getResult().getID() != null) {

                        if (rawResponse.body().getResult().getID().equals("0")) {
                            if(!isFromHomeScreen) {
                                mCbCreateAccount.setVisibility(View.VISIBLE);
                                ResvUtils.createRegisterContinueDialog(RegisterActivity.this, "Email doesn’t exist.\nWould you like to register or continue as a guest user.", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                mCbCreateAccount.setChecked(true);
                                                mLinearLayout.setVisibility(View.VISIBLE);
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                dialogInterface.cancel();
                                                break;
                                        }

                                    }
                                });
                            }
                        } else {
                            if (rawResponse.body().getResult().getOperation().equals("1") && rawResponse.body().getResult().getUserType().equals("3")) {
                                mCbCreateAccount.setVisibility(View.GONE);
                                ResvUtils.createYesOrNoDialog(RegisterActivity.this, "Email already  exists.\nDo you want to login?\n ", new DialogInterface.OnClickListener() {
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
                            } else {
                                ResvUtils.createOKAlert(RegisterActivity.this, "", "Email already exists. \n Please register. ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mCbCreateAccount.setChecked(true);
                                        mLinearLayout.setVisibility(View.VISIBLE);
                                    }
                                });
                               if(!isFromHomeScreen)
                                mCbCreateAccount.setVisibility(View.VISIBLE);
                                mUserId = rawResponse.body().getResult().getID();
                            }

                        }

                    } else {
                        ResvUtils.createErrorAlert(RegisterActivity.this, getString(R.string.error), "No User Exist");
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

    TitleNames mTitleNames;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mTitleNames = (TitleNames) mSpTitle.getSelectedItem();
        mSttitle = mTitleNames.getValue();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            boolean isValidYear = true;
            if (working.length() == 2 && before == 0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 12) {
                    isValid = false;
                } else {
                    working += "/";
                    mEtDateOfBirth.setText(working);
                    mEtDateOfBirth.setSelection(working.length());
                }
            } else if (working.length() == 5 && before == 0) {
                String month = working.substring(3);
                if (Integer.parseInt(month) > 31) {
                    isValid = false;
                } else {
                    working += "/";
                    mEtDateOfBirth.setText(working);
                    mEtDateOfBirth.setSelection(working.length());
                }
            } else if (working.length() == 10 && before == 0) {
                String enteredYear = working.substring(6);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int minYear = currentYear - 120;
                int intEnterYear = Integer.parseInt(enteredYear);
                if (intEnterYear > currentYear || intEnterYear < minYear) {
                    isValidYear = false;
                }
            } else if (working.length() != 10) {
                isValid = false;

            }

            if (!isValid || !isValidYear) {
                if (!isValidYear) {
                    mEtDateOfBirth.setError("Invalid Year");
                    mTvDob.setError(getString(R.string.dobMsg));
                }
                else {
                    mEtDateOfBirth.setError(getString(R.string.dobMsg));
                    mTvDob.setError(getString(R.string.dobMsg));
                }
            } else {
                mEtDateOfBirth.setError(null);
                mTvDob.setErrorEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

    };


}
