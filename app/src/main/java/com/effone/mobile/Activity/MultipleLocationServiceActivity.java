package com.effone.mobile.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.adapter.ServiceTypeAdapter;
import com.effone.mobile.common.ResvUtils;

/**
 * Created by sarith.vasu on 21-09-2017.
 */

public class MultipleLocationServiceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{

    private ListView mLvServiceType;
    private TextView mTvTitle;
    private AppCompatSpinner mSpinner;
    private ServiceTypeAdapter mServiceTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_service);
        ResvUtils.enableBackButton(this);
        mLvServiceType=(ListView)findViewById(R.id.lv_service_type);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.service_type));
        mSpinner=(AppCompatSpinner)findViewById(R.id.sp_location);
        setAdapters();
    }

    private void setAdapters() {



}
    @Override
    protected void onStart() {
        super.onStart();
        if (!ResvUtils.Operations.isOnline(this)) {
            ResvUtils.Operations.showNoNetworkActivity(this);
        }
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
