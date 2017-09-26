package com.effone.reservopia.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.adapter.ServiceTypeAdapter;
import com.effone.reservopia.model.Locations;

import java.util.ArrayList;

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
        mLvServiceType=(ListView)findViewById(R.id.lv_service_type);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.service_type));
        mSpinner=(AppCompatSpinner)findViewById(R.id.sp_location);
        setAdapters();
    }

    private void setAdapters() {



}

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
