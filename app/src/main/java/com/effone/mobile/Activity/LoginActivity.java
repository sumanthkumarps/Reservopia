package com.effone.mobile.Activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.adapter.AppointmentListAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.common.Validation;
import com.effone.mobile.model.LoginResult;
import com.effone.mobile.model.Response;
import com.effone.mobile.model.Result;
import com.effone.mobile.model.UpCommingAppointmentModel;
import com.effone.mobile.model.UserDetailGet;
import com.effone.mobile.model.UserDetails;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;

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
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
        setContentView(R.layout.login_alert);

        mEtEmail=(EditText)findViewById(R.id.et_email);
        String email=getIntent().getStringExtra("email");
        if(email!=null) {
            if (!email.equals("")) {
                mEtEmail.setText(email);
            }
        }
        mEtPassword=(EditText)findViewById(R.id.et_password);
        mTvCancelNew=(TextView)findViewById(R.id.tv_cancel_new);
        mTvLogin=(TextView)findViewById(R.id.tv_login);
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
                    ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount),  getResources().getString(R.string.Emailmsg) );


                break;
            case R.id.tv_cancel_new:
                finish();
                break;
            default:
                break;
        }
    }
    String user_id;
    private void userLogin(final String email, String pass) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResult> call = apiService.getLogin(getString(R.string.token),email,pass);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, retrofit2.Response<LoginResult> response) {
                response.raw().request().url();
                if (response.body() != null) {
                    if (response.body().getResult()) {
                        AppPreferene.with(LoginActivity.this).setEmail(email);
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    } else {
                        ResvUtils.createOKAlert(LoginActivity.this, "Error", response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                ResvUtils.createOKAlert(LoginActivity.this,getString(R.string.error),getString(R.string.something_went_wrong));
            }
        });

    }


}
