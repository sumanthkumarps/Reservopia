package com.effone.mobile.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.receivers.NetworkChangeReceiver;

import static com.effone.mobile.receivers.NetworkChangeReceiver.connectionValue;

public class NetworkErrorActivity extends AppCompatActivity implements View.OnClickListener {
    private static ConstraintLayout mCoordinatorLayout;
    private NetworkChangeReceiver broadcastReceiver;
    private  ImageView mIvBackBtn;
    private TextView mTvTitle,mTvRetry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);
        ResvUtils.enableBackButton(this);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.noConn));
        mIvBackBtn=(ImageView)findViewById(R.id.iv_back_btn);
        mIvBackBtn.setVisibility(View.GONE);
        mCoordinatorLayout=(ConstraintLayout)findViewById(R.id.condintra);
        broadcastReceiver=new NetworkChangeReceiver();
        mTvRetry=(TextView)findViewById(R.id.tv_retry);
        mTvRetry.setOnClickListener(this);
    }

    public  void dialog(boolean value){


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    public void onClick(View view) {
       if(connectionValue)
           finish();
    }
}
