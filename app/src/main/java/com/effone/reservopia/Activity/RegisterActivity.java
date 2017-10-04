package com.effone.reservopia.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.effone.reservopia.R;

/**
 * Created by sarith.vasu on 26-09-2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtEmail, mEtPhone, mEtFirstName, mEtLastName, mEtDateOfBirth,
            mEttitle, mEtAddress, mEtZip, mEtState, mEtPassword, mEtConfirmPassword, mEtCity;
    private RadioGroup mRGGender;
    private Button mBtSubmit;
    private String mStEmail, mStPhone, mStFirstName, mStLastName, mStDateOfBirth,
            mSttitle, mStGender, mStAddress, mStZip, mStState, mStPassword, mStConfirmPassword, mStCity;
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("Registration");
        init();
    }

    private void init() {
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus

                }
            }
        });
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtFirstName = (EditText) findViewById(R.id.et_firstname);
        mEtLastName = (EditText) findViewById(R.id.et_lastName);
        mEtDateOfBirth = (EditText) findViewById(R.id.et_date_birth);
        mEttitle = (EditText) findViewById(R.id.et_title);
        mEtAddress = (EditText) findViewById(R.id.et_address);

        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtConfirmPassword = (EditText) findViewById(R.id.et_conf_pass);

        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mRGGender = (RadioGroup) findViewById(R.id.radioSex);

        mBtSubmit.setOnClickListener(this);
    }

    private RadioButton radioSexButton;

    @Override
    public void onClick(View view) {
        mStEmail = mEtEmail.getText().toString().trim();


        mStPhone = mEtPhone.getText().toString().trim();
        mStFirstName = mEtFirstName.getText().toString().trim();
        mStLastName = mEtLastName.getText().toString().trim();
        mStAddress = mEtAddress.getText().toString().trim();
        mSttitle = mEttitle.getText().toString().trim();
        mStDateOfBirth = mEtDateOfBirth.getText().toString().trim();
        mStPassword = mEtPassword.getText().toString().trim();
        mStConfirmPassword = mEtConfirmPassword.getText().toString().trim();
        int selectedId = mRGGender.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        mStGender = (String) radioSexButton.getText();

    }

}
