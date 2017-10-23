package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.util.Arrays;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
        setContentView(R.layout.login_alert);
        this.setFinishOnTouchOutside(false);
        mEtEmail=(EditText)findViewById(R.id.et_email);
        String email=getIntent().getStringExtra("email");
        isFormHomeScreen=getIntent().getBooleanExtra(getString(R.string.isFromHomeScreen),false);
        mEtPassword=(EditText)findViewById(R.id.et_password);
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
        mForgotPassword=(TextView)findViewById(R.id.forgotPassword);
        mForgotPassword.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mTvCancelNew.setOnClickListener(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:

                Validation validation=new Validation();
                if(validation.isValidEmail(mEtEmail.getText().toString())&&validation.isValidPassword(mEtPassword.getText().toString()))
                    userLogin(mEtEmail.getText().toString().trim(),mEtPassword.getText().toString().trim());
                else
                    ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount),  getResources().getString(R.string.login_validation) );


                break;
            case R.id.tv_sign_up:
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra(getString(R.string.fromLogin),true);
                startActivity(intent);

                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.forgotPassword:
                forgotPasswordApi(mEtEmail.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void forgotPasswordApi(String email) {
        Validation validation=new Validation();
        if(validation.isValidEmail(mEtEmail.getText().toString())) {
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

            Call<ForgotPasswordResponse> call = apiService.getForgotPassword(getString(R.string.token),getString(R.string.org_id), email,"True");

            call.enqueue(new Callback<ForgotPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgotPasswordResponse> call, retrofit2.Response<ForgotPasswordResponse> response) {
                    response.raw().request().url();
                    if (mCommonProgressDialog != null)
                        mCommonProgressDialog.cancel();
                    if (response.body() != null) {
                     if (response.body().getMessage() != null) {
                            ResvUtils.createOKAlert(LoginActivity.this, "Message", response.body().getMessage());
                        } else {
                            ResvUtils.createOKAlert(LoginActivity.this, "Error", "Something went wrong");
                        }
                    } else {
                        if (mCommonProgressDialog != null)
                            mCommonProgressDialog.cancel();
                        if (!response.isSuccessful()) {
                            UserDetails registerResponse = null;
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
                public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                    ResvUtils.createOKAlert(LoginActivity.this, getString(R.string.error), getString(R.string.something_went_wrong));
                }
            });
        }else{
            ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount),  getResources().getString(R.string.Emailmsg) );
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
                        if(isFormHomeScreen)
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        else
                            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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
                ResvUtils.createOKAlert(LoginActivity.this,getString(R.string.error),getString(R.string.something_went_wrong));
            }
        });

    }


}
