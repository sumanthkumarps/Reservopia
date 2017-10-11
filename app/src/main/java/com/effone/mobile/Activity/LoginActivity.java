package com.effone.mobile.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.mobile.R;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.common.Validation;

/**
 * Created by sumanth.peddinti on 10/11/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtEmail;
    private EditText mEtPassword;
    private TextView mTvLogin,mTvCancelNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_alert);
        mEtEmail=(EditText)findViewById(R.id.et_email);
        mEtPassword=(EditText)findViewById(R.id.et_password);
        mTvCancelNew=(TextView)findViewById(R.id.tv_cancel_new);
        mTvLogin=(TextView)findViewById(R.id.tv_login);
        mTvLogin.setOnClickListener(this);
        mTvCancelNew.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:

                Validation validation=new Validation();
                if(validation.isValidEmail(mEtEmail.getText().toString())&&validation.isValidPassword(mEtPassword.getText().toString()))
                    Toast.makeText(this,"Api need to be implemented",Toast.LENGTH_SHORT).show();
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
}
