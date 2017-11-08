package com.effone.mobile.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.common.ResvUtils;

public class SearchAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mEtLastName;
    private TextView mEtDob;
    private TextView mEtConfirmNo;
    private Button mBtSubmit;
    private TextView mTvTitle;
    private LinearLayout mTvEmptyView;
    private ListView mLvAppointmentList;
    private TextView mTvCountAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        ResvUtils.enableBackButton(this);
        ResvUtils.enableHomeButton(this);
        declare();
    }

    private void declare() {
        mEtLastName=(TextView)findViewById(R.id.et_last_name);
        mEtDob=(TextView)findViewById(R.id.et_date_birth);
        mEtConfirmNo=(TextView)findViewById(R.id.et_confirmation_no);
        mBtSubmit=(Button)findViewById(R.id.bt_submit);
        mTvEmptyView=(LinearLayout) findViewById(R.id.tv_empty_view);
        mBtSubmit.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvCountAppointment=(TextView)findViewById(R.id.tv_count_appointments);
        mTvTitle.setText(getString(R.string.search));
        mLvAppointmentList=(ListView)findViewById(R.id.lv_upcomingAppointent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_submit){

        }

    }
    private void removingCompleteData() {
        mTvCountAppointment.setText(""+0);
        mLvAppointmentList.setAdapter(null);
        mLvAppointmentList.setVisibility(View.GONE);
        mTvEmptyView.setVisibility(View.VISIBLE);
    }
}
