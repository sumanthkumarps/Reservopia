package com.effone.reservopia.Activity;

import android.content.BroadcastReceiver;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.common.ResvUtils;
import com.effone.reservopia.receivers.NetworkChangeReceiver;

import static com.effone.reservopia.R.id.linearLayout;

public class NetworkErrorActivity extends AppCompatActivity {
    private static ConstraintLayout mCoordinatorLayout;
    private BroadcastReceiver broadcastReceiver;
    private  ImageView mIvBackBtn;
    private TextView mTvTitle;
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
        final  Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, "No Connection", Snackbar.LENGTH_LONG);
        snackbar.setAction("RETRY", null);
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
