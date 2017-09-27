package com.effone.reservopia.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.common.ResvUtils;

/**
 * Created by sarith.vasu on 26-09-2017.
 */

public class RegisterActivity extends AppCompatActivity {

    TextView mTvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText("Registration");
        ResvUtils.enableBackButton(this);
    }
}
