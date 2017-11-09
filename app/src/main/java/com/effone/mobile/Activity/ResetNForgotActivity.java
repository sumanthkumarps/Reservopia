package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.common.Validation;
import com.effone.mobile.model.ChangePassword;
import com.effone.mobile.model.ForgotPasswordResponse;
import com.effone.mobile.model.UserDetailGet;
import com.effone.mobile.model.UserDetails;
import com.effone.mobile.realmdb.ServiceTable;
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
    private RelativeLayout mLinearLayoutFromMail ;
    private TextView mTvTitle;
    private ProgressDialog mCommonProgressDialog;
    private String TAG;
    private RelativeLayout mLinearLayoutResetPass;
    private TextView mTvResetPassword;
    private LinearLayout mLinearLayoutEmailReset;
    private LinearLayout mLinearLayoutProvisional;
    private EditText mEtEmailForReset;
    private Button mBtEmailSubmit;
    private TextView mTvFromEmail;
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
        mTvFromEmail=(TextView)findViewById(R.id.tv_from_email);

        mEtEmailForReset = (EditText) findViewById(R.id.et_reset_email);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtConfirmPassword = (EditText) findViewById(R.id.et_conf_pass);
        mLinearLayoutFromMail = (RelativeLayout) findViewById(R.id.login_info);
        mLinearLayoutProvisional = (LinearLayout) findViewById(R.id.lay_mailed_password);
        mLinearLayoutResetPass = (RelativeLayout) findViewById(R.id.lay_reset_password);
        mEtMailedPassword = (EditText) findViewById(R.id.et_from_email_pass);
      /*  drawerRightEye(mEtConfirmPassword);
        drawerRightEye(mEtMailedPassword);
        drawerRightEye(mEtPassword);*/

        mEtMailedPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (mEtMailedPassword.getWidth() - mEtMailedPassword
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!isShow) {
                            mEtMailedPassword.setTransformationMethod(null);
                            isShow = true;
                            mEtMailedPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_black_24dp, 0);
                        } else {
                            mEtMailedPassword.setTransformationMethod(new PasswordTransformationMethod());
                            mEtMailedPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_black_24dp, 0);
                            isShow = false;
                        }
                        mEtMailedPassword.setSelection(mEtMailedPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
        mEtPassword.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View v, MotionEvent event) {
                                               final int DRAWABLE_LEFT = 0;
                                               final int DRAWABLE_TOP = 1;
                                               final int DRAWABLE_RIGHT = 2;
                                               final int DRAWABLE_BOTTOM = 3;

                                               if (event.getAction() == MotionEvent.ACTION_UP) {
                                                   if (event.getX() >= (mEtPassword.getWidth() - mEtPassword
                                                           .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                                       if (!isShow) {
                                                           mEtPassword.setTransformationMethod(null);
                                                           isShow = true;
                                                           mEtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_black_24dp, 0);
                                                       } else {
                                                           mEtPassword.setTransformationMethod(new PasswordTransformationMethod());
                                                           mEtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_black_24dp, 0);
                                                           isShow = false;
                                                       }
                                                       mEtPassword.setSelection(mEtPassword.getText().length());
                                                       return true;
                                                   }
                                               }
                                               return false;
                                           }
                                       });

        methodPassword();
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
            mLinearLayoutProvisional.setVisibility(View.VISIBLE);
            mTvTitle.setText(getString(R.string.reset_pass));
            mEtEmailForReset.setText(AppPreferene.with(this).getEmail());
            mEtEmailForReset.setEnabled(false);
        } else {
            mLinearLayoutFromMail.setVisibility(View.VISIBLE);
            mLinearLayoutResetPass.setVisibility(View.GONE);
            mTvResetPassword.setVisibility(View.GONE);
            mLinearLayoutEmailReset.setVisibility(View.GONE);
            mLinearLayoutProvisional.setVisibility(View.VISIBLE);
            mTvTitle.setText(getString(R.string.forgot_title));
            mTvFromEmail.setText(getString(R.string.reset_code));
            mEtMailedPassword.setHint(getString(R.string.enter_reset_code));

        }
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mBtEmailSubmit = (Button) findViewById(R.id.bt_submit_email);
        mBtEmailSubmit.setTransformationMethod(null);
        mBtSubmit.setOnClickListener(this);
        mBtEmailSubmit.setOnClickListener(this);
        mBtSubmit.setTransformationMethod(null);
    }

    private void methodPassword() {
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
                    if(mEtMailedPassword.getText().toString().equals("")||mEtEmailForReset.getText().toString().equals("")||mEtPassword.getText().toString().equals("")||mEtConfirmPassword.getText().toString().equals("")){
                        ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.all_field));
                    }else
                    if (!validation.isValidEmail(mEtEmailForReset.getText().toString().trim())) {
                        ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.email_error_msg));
                    }else
                    if (!validation.isValidPassword(mEtPassword.getText().toString().trim())) {
                        ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.passwordmsg));
                    } else if(mEtPassword.getText().toString().trim().equals(mEtConfirmPassword.getText().toString().trim())){
                       // changePassword(mEtEmailForReset.getText().toString().trim(),mEtMailedPassword.getText().toString().trim(),mEtPassword.getText().toString().trim());
                        passowrdChanged(mEtEmailForReset.getText().toString().trim(),mEtMailedPassword.getText().toString().trim(),mEtPassword.getText().toString().trim());

                    }
                    else
                        ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.passworddoednotmatch));

                } else {
                    ResvUtils.Operations.showNoNetworkActivity(this);
                }
            default:
                break;
        }
    }

    private void passowrdChanged(String trim, String trim1, String trim2) {
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

            Call<ChangePassword> call = apiService.getChangedPassword(getString(R.string.token),trim,trim1,trim2);

            call.enqueue(new Callback<ChangePassword>() {
                @Override
                public void onResponse(Call<ChangePassword> call, retrofit2.Response<ChangePassword> responses) {
                    responses.raw().request().url();
                    if (mCommonProgressDialog != null)
                        mCommonProgressDialog.cancel();
                    if (responses.body() != null) {
                        ChangePassword userDetailGet = responses.body();
                        if(userDetailGet!= null){
                            ResvUtils.createOKAlert(ResetNForgotActivity.this, "Message", responses.body().getMessage(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(ResetNForgotActivity.this, MainActivity.class));
                                }
                            });
                        }
                        else if(responses.body().getMessage()!=null){
                            ResvUtils.createOKAlert(ResetNForgotActivity.this, "Message",responses.body().getMessage());
                        }

                    }else  {
                        if (mCommonProgressDialog != null)
                            mCommonProgressDialog.cancel();
                        if (!responses.isSuccessful() ) {
                            ChangePassword registerResponse=null;
                            Log.d(TAG, "onResponse - Status : " + responses.code());
                            Gson gson = new Gson();
                            TypeAdapter<ChangePassword> adapter = gson.getAdapter(ChangePassword.class);
                            try {
                                if (responses.errorBody() != null)
                                    registerResponse =
                                            adapter.fromJson(responses.errorBody().string());
                                if(!registerResponse.getResult()){
                                    ResvUtils.createOKAlert(ResetNForgotActivity.this, "Error", registerResponse.getMessage());
                                }else {
                                    ResvUtils.createOKAlert(ResetNForgotActivity.this, "", "Your password has been changed successfully.", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent=new Intent(ResetNForgotActivity.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            } catch (IOException e) {

                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<ChangePassword> call, Throwable t) {
                    if (mCommonProgressDialog != null)
                        mCommonProgressDialog.cancel();
                    ResvUtils.createOKAlert(ResetNForgotActivity.this,getString(R.string.error),getString(R.string.something_went_wrong));
                }
            });



    }
    boolean isShow;
    public void drawerRightEye(final EditText editText){
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getX() >= (editText.getWidth() - editText
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if(!isShow) {
                            editText.setTransformationMethod(null);
                            isShow=true;
                            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.ic_visibility_off_black_24dp, 0);
                        }
                        else{
                            editText.setTransformationMethod(new PasswordTransformationMethod());
                            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.ic_visibility_black_24dp, 0);
                            isShow=false;
                        }
                        editText.setSelection(editText.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
    }




    private void changePassword(String email,String oldpassword, String password) {
        if (mCommonProgressDialog == null) {
            mCommonProgressDialog = ResvUtils.createProgressDialog(this);
            mCommonProgressDialog.show();
            mCommonProgressDialog.setMessage("Please wait...");
            mCommonProgressDialog.setCancelable(false);
        } else {
            mCommonProgressDialog.show();
        }


        Call<ChangePassword> call = apiService.getChangedPassword(getString(R.string.token),email,oldpassword,password);


        call.enqueue(new Callback<ChangePassword>() {

            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                response.raw().request().url();

                if (response.body() != null) {
                    if (response.body().getMessage() != null) {
                            ResvUtils.createOKAlert(ResetNForgotActivity.this, "", "Your password has been changed successfully.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });


                    }


                }else{
                    ResvUtils.createErrorAlert(ResetNForgotActivity.this,"Error","Something went wrong");
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

            mEtEmailForReset.setText(email);
            call.enqueue(new Callback<ForgotPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgotPasswordResponse> call, final retrofit2.Response<ForgotPasswordResponse> response) {
                    if (mCommonProgressDialog != null)
                        mCommonProgressDialog.cancel();
                    if (response.body() != null) {
                        if (response.body().getMessage() != null) {
                            ResvUtils.createOKAlert(ResetNForgotActivity.this, "Message", getString(R.string.resetPassword_msg), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                  mTvforgotPassMsg.setVisibility(View.VISIBLE);
                                    mTvforgotPassMsg.setText(getString(R.string.resetPassword_msg));
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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
            ResvUtils.createOKAlert(this, getResources().getString(R.string.headercreateaccount), getResources().getString(R.string.email_error_msg));
        }

    }
}
