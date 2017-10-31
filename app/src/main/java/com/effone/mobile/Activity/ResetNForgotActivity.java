package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.mobile.R;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.common.Validation;
import com.effone.mobile.model.ChangePassword;
import com.effone.mobile.model.ForgotPasswordResponse;
import com.effone.mobile.model.UserDetails;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetNForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtEmail;
    private EditText mEtPassword;
    private EditText mEtConfirmPassword;
    private EditText mEtMailedPassword;
    private Button mBtSubmit;
    private boolean isFromReset;
    private LinearLayout mLinearLayoutFromMail;
    private TextView mTvTitle;
    private ProgressDialog mCommonProgressDialog;
    private String TAG;
    private LinearLayout mLinearLayoutResetPass;
    private TextView mTvResetPassword;
    private LinearLayout mLinearLayoutEmailReset;
    private LinearLayout mLinearLayoutProvisional;
    private EditText mEtEmailForReset;
    private Button mBtEmailSubmit;
    private TextView mTvforgotPassMsg;
    private       ApiInterface apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_nforgot);
        this.setFinishOnTouchOutside(false);
        isFromReset = getIntent().getBooleanExtra(getString(R.string.reset), false);
        ResvUtils.enableBackButton(this);

         apiService =
                ApiClient.getClient().create(ApiInterface.class);
        declaration();
    }

    private void declaration() {
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtEmailForReset = (EditText) findViewById(R.id.et_reset_email);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtConfirmPassword = (EditText) findViewById(R.id.et_conf_pass);
        mLinearLayoutFromMail = (LinearLayout) findViewById(R.id.login_info);
        mLinearLayoutProvisional = (LinearLayout) findViewById(R.id.lay_mailed_password);
        mLinearLayoutResetPass = (LinearLayout) findViewById(R.id.lay_reset_password);
        mEtMailedPassword = (EditText) findViewById(R.id.et_from_email_pass);
        mTvResetPassword = (TextView) findViewById(R.id.tv_reset_password);
        mTvforgotPassMsg = (TextView) findViewById(R.id.tv_forgot_msg);
        mLinearLayoutEmailReset = (LinearLayout) findViewById(R.id.lay_emai);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.reset_cap));
        if (isFromReset) {
            mLinearLayoutFromMail.setVisibility(View.GONE);
            mLinearLayoutResetPass.setVisibility(View.VISIBLE);
            mTvResetPassword.setVisibility(View.VISIBLE);
            mLinearLayoutEmailReset.setVisibility(View.VISIBLE);
            mLinearLayoutProvisional.setVisibility(View.GONE);
            mTvTitle.setText(getString(R.string.reset_pass));
        } else {
            mLinearLayoutFromMail.setVisibility(View.VISIBLE);
            mLinearLayoutResetPass.setVisibility(View.GONE);
            mTvResetPassword.setVisibility(View.GONE);
            mLinearLayoutEmailReset.setVisibility(View.GONE);
            mLinearLayoutProvisional.setVisibility(View.VISIBLE);
            mTvTitle.setText(getString(R.string.forgot_title));

        }
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mBtEmailSubmit = (Button) findViewById(R.id.bt_submit_email);
        mBtEmailSubmit.setTransformationMethod(null);
        mBtSubmit.setOnClickListener(this);
        mBtEmailSubmit.setOnClickListener(this);
        mBtSubmit.setTransformationMethod(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_submit_email:
                if (ResvUtils.Operations.isOnline(this)) {
                    forgotPasswordApi(mEtEmail.getText().toString().trim());
                } else {
                    ResvUtils.Operations.showNoNetworkActivity(this);
                }
                break;
            case R.id.bt_submit:
                if (ResvUtils.Operations.isOnline(this)) {
                    Validation validation = new Validation();
                    if (!validation.isValidEmail(mEtEmailForReset.getText().toString())) {
                        ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.Emailmsg));
                    }else
                    if (!validation.isValidPassword(mEtPassword.getText().toString())) {
                        ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.passwordmsg));
                    } else if(mEtPassword.equals(mEtConfirmPassword)){
                        changePassword(mEtEmailForReset.getText().toString().trim(),mEtPassword.getText().toString().trim());
                    }
                    else if(!mEtPassword.getText().toString().equals(mEtConfirmPassword.getText().toString()))
                        ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.passworddoednotmatch));
                    else{

                    }
                } else {
                    ResvUtils.Operations.showNoNetworkActivity(this);
                }
            default:
                break;
        }
    }

    private void changePassword(String email, String password) {
        if (mCommonProgressDialog == null) {
            mCommonProgressDialog = ResvUtils.createProgressDialog(this);
            mCommonProgressDialog.show();
            mCommonProgressDialog.setMessage("Please wait...");
            mCommonProgressDialog.setCancelable(false);
        } else {
            mCommonProgressDialog.show();
        }


        Call<ChangePassword> call = apiService.getChangedPassword(getString(R.string.token), email,password,Boolean.TRUE);


        call.enqueue(new Callback<ChangePassword>() {

            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                if (response.body() != null) {
                    if (response.body().getMessage() != null) {
                        if (response.body().getResult()) {

                            ResvUtils.createOKAlert(ResetNForgotActivity.this, "", response.body().getMessage(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                        }

                    }


                }

            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
            }

      });
    }


    private void forgotPasswordApi(String email) {
        Validation validation = new Validation();
        if (validation.isValidEmail(mEtEmail.getText().toString())) {
            if (mCommonProgressDialog == null) {
                mCommonProgressDialog = ResvUtils.createProgressDialog(this);
                mCommonProgressDialog.show();
                mCommonProgressDialog.setMessage("Please wait...");
                mCommonProgressDialog.setCancelable(false);
            } else {
                mCommonProgressDialog.show();
            }


            Call<ForgotPasswordResponse> call = apiService.getForgotPassword(getString(R.string.token), getString(R.string.org_id), email, "True");

            call.enqueue(new Callback<ForgotPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgotPasswordResponse> call, final retrofit2.Response<ForgotPasswordResponse> response) {
                    if (mCommonProgressDialog != null)
                        mCommonProgressDialog.cancel();
                    if (response.body() != null) {
                        if (response.body().getMessage() != null) {
                            ResvUtils.createOKAlert(ResetNForgotActivity.this, "Message", response.body().getMessage(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mTvforgotPassMsg.setVisibility(View.VISIBLE);
                                    mTvforgotPassMsg.setText(response.body().getMessage());
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0, 10, 0, 0);
                                    mTvforgotPassMsg.setLayoutParams(params);
                                    mLinearLayoutFromMail.setVisibility(View.GONE);
                                    mLinearLayoutResetPass.setVisibility(View.VISIBLE);
                                    mTvResetPassword.setVisibility(View.VISIBLE);
                                    mLinearLayoutEmailReset.setVisibility(View.GONE);
                                    mLinearLayoutProvisional.setVisibility(View.VISIBLE);
                                }
                            });
                        } else {
                            ResvUtils.createOKAlert(ResetNForgotActivity.this, "Error", "Something went wrong");
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
                                ResvUtils.createOKAlert(ResetNForgotActivity.this, "Error", registerResponse.getMessage());
                            } catch (IOException e) {

                            }
                        }
                    }
                }


                @Override
                public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                    ResvUtils.createOKAlert(ResetNForgotActivity.this, getString(R.string.error), getString(R.string.something_went_wrong));
                }
            });
        } else {
            ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.Emailmsg));
        }

    }
}
