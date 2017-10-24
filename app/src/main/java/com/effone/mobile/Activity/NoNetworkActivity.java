package com.effone.mobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.common.ResvUtils;

public class NoNetworkActivity extends AppCompatActivity {
    private Button mBtSubit;
    private ImageView mTvImgBackButton;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ResvUtils.Operations.isOnline(this)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            setContentView(R.layout.activity_no_network);
            mTvImgBackButton=(ImageView) findViewById(R.id.iv_back_btn);
            mTvImgBackButton.setVisibility(View.GONE);
            mTvTitle=(TextView)findViewById(R.id.tv_title);
            mTvTitle.setText(R.string.app_name);
            mBtSubit=(Button)findViewById(R.id.bt_subit);
            mBtSubit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResvUtils.Operations.isOnline(NoNetworkActivity.this)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
    }
}
