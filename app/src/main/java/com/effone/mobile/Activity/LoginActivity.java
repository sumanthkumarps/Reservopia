package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.adapter.AppointmentListAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.common.Validation;
import com.effone.mobile.model.ForgotPasswordResponse;
import com.effone.mobile.model.LoginResult;
import com.effone.mobile.model.Response;
import com.effone.mobile.model.Result;
import com.effone.mobile.model.UpCommingAppointmentModel;
import com.effone.mobile.model.UserDetailGet;
import com.effone.mobile.model.UserDetails;
import com.effone.mobile.realmdb.UserTable;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.util.Arrays;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sumanth.peddinti on 10/11/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="login" ;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private TextView mTvLogin,mTvCancelNew;
    private ImageButton mCancelBtn;
    private ApiInterface apiService;
    private ProgressDialog mCommonProgressDialog;
    private TextView mForgotPassword;
    private boolean isFormHomeScreen;
    private TextView mResetPassword;
    private boolean isShow;
    private Realm mRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm=Realm.getDefaultInstance();
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
        setContentView(R.layout.login_alert);
        this.setFinishOnTouchOutside(false);
        mEtEmail=(EditText)findViewById(R.id.et_email);
        String email=getIntent().getStringExtra("email");
        isFormHomeScreen=getIntent().getBooleanExtra(getString(R.string.isFromHomeScreen),false);
        mEtPassword=(EditText)findViewById(R.id.et_password);

        mEtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getX() >= (mEtPassword.getWidth() - mEtPassword
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(!isShow) {
                            mEtPassword.setTransformationMethod(null);
                            isShow=true;
                            mEtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.ic_visibility_off_black_24dp, 0);
                        }
                        else{
                            mEtPassword.setTransformationMethod(new PasswordTransformationMethod());
                            mEtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.ic_visibility_black_24dp, 0);
                            isShow=false;
                        }
                        mEtPassword.setSelection(mEtPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
        if(email!=null) {
            if (!email.equals("")) {
                mEtEmail.setText(email);
                mEtEmail.setEnabled(false);
                mEtEmail.clearFocus();
                mEtPassword.requestFocus();
            }
        }


        mTvCancelNew=(TextView)findViewById(R.id.tv_sign_up);
        mTvLogin=(TextView)findViewById(R.id.tv_login);
        mCancelBtn=(ImageButton)findViewById( R.id.btnCancel) ;
        mResetPassword=(TextView)findViewById(R.id.tv_reset_password);
        mForgotPassword=(TextView)findViewById(R.id.forgotPassword);
        mForgotPassword.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mTvCancelNew.setOnClickListener(this);
        mResetPassword.setOnClickListener(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);


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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                if(ResvUtils.Operations.isOnline(this)) {
                    Validation validation = new Validation();
                    if (validation.isValidEmail(mEtEmail.getText().toString()) && validation.isValidPassword(mEtPassword.getText().toString()))
                        userLogin(mEtEmail.getText().toString().trim(), mEtPassword.getText().toString().trim());
                    else
                        ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.login_validation));
                }
                else{
                    ResvUtils.Operations.showNoNetworkActivity(this);
                }

                break;
            case R.id.tv_sign_up:
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra(getString(R.string.fromLogin),true);
                intent.putExtra(getString(R.string.edit_profile),false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnCancel:

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                finish();
                break;
            case R.id.forgotPassword:
                Intent intent2= new Intent(LoginActivity.this, ResetNForgotActivity.class);
                intent2.putExtra(getString(R.string.reset),false);
                startActivity(intent2);
                finish();
                break;
            case R.id.tv_reset_password:
                Intent intent1= new Intent(LoginActivity.this, ResetNForgotActivity.class);
                intent1.putExtra(getString(R.string.reset),true);
                startActivity(intent1);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!ResvUtils.Operations.isOnline(this)) {
            ResvUtils.Operations.showNoNetworkActivity(this);
        }
    }

    String user_id;
    private void userLogin(final String email, String pass) {
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

        Call<UserDetails> call = apiService.getLogin(getString(R.string.token),email,pass);

        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, retrofit2.Response<UserDetails> response) {
                response.raw().request().url();
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                if (response.body() != null) {
                    UserDetailGet userDetailGet = response.body().getResult();
                    if (userDetailGet != null) {
                        mEtEmail.setText(userDetailGet.getEmail());
                        AppPreferene.with(LoginActivity.this).setEmail(email);
                        AppPreferene.with(LoginActivity.this).setUserId(userDetailGet.getUserID());
                        //insertingUserDeatilsIntoDataBase(userDetailGet.getUserID(),email);
                        if(isFormHomeScreen) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else {

                            finish();
                        }

                        finish();
                    }
                    else if(response.body().getMessage()!=null){
                        ResvUtils.createOKAlert(LoginActivity.this, "Message",response.body().getMessage());
                    }
                    else {
                        ResvUtils.createOKAlert(LoginActivity.this, "Error", "Something went wrong");
                    }
                }
                else{
                    if (mCommonProgressDialog != null)
                        mCommonProgressDialog.cancel();
                    if (!response.isSuccessful() ) {
                        UserDetails registerResponse=null;
                        Log.d(TAG, "onResponse - Status : " + response.code());
                        Gson gson = new Gson();
                        TypeAdapter<UserDetails> adapter = gson.getAdapter(UserDetails.class);
                        try {
                            if (response.errorBody() != null)
                                registerResponse =
                                        adapter.fromJson(response.errorBody().string());
                            ResvUtils.createOKAlert(LoginActivity.this, "Error", registerResponse.getMessage());
                        } catch (IOException e) {

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                ResvUtils.createOKAlert(LoginActivity.this,getString(R.string.error),getString(R.string.something_went_wrong));
            }
        });

    }

    private void insertingUserDeatilsIntoDataBase(String userID,String email) {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetails> call = apiService.getUserDetails(getString(R.string.token),userID,email);

            call.enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, retrofit2.Response<UserDetails> response) {
                    response.raw().request().url();
                    if(response.body() != null) {

                        UserDetailGet userDetailGet = response.body().getResult();
                        if (userDetailGet != null) {
                            mRealm.beginTransaction();
                            UserTable userTable=new UserTable();
                            userTable.setFirstName(userDetailGet.getFirstName());
                            userTable.setPhone(userDetailGet.getPhone());
                            userTable.setLastName(userDetailGet.getLastName());
                            userTable.setDateOfBirth(ResvUtils.parseDateToddMMyyyy(userDetailGet.getDateOfBirth().split("T")[0],"yyyy-MM-dd","MM/dd/yyyy"));
                            userTable.setGender(userDetailGet.getGender());
                            userTable.setTitle(userDetailGet.getTitle());
                            mRealm.insert(userTable);
                            mRealm.commitTransaction();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {
                    ResvUtils.createOKAlert(LoginActivity.this,getString(R.string.error),getString(R.string.something_went_wrong));
                }
            });




    }


}
