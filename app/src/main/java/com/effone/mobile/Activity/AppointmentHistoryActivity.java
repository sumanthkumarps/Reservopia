package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.adapter.AppointmentHistoryAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.model.History;
import com.effone.mobile.model.HistoryAppointment;
import com.effone.mobile.model.Result;
import com.effone.mobile.model.UpCommingAppointmentModel;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AppointmentHistory";
    private TextView mTvTitle;
    private ListView mLvAppointmentHistoryList;
    ArrayList<HistoryAppointment> mAppointments;
    private  ImageView mIvBackBtn;
    private ProgressDialog mCommonProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);
        ResvUtils.enableBackButton(this);
        ResvUtils.enableHomeButton(this);
        init();
    }

    private void init() {
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.appointment_history));
        mIvBackBtn=(ImageView)findViewById(R.id.iv_back_btn);
        mIvBackBtn.setOnClickListener(this);
        mLvAppointmentHistoryList=(ListView)findViewById(R.id.lv_appointment_history);
       if(AppPreferene.with(this).getEmail().equals("")){
           ResvUtils.createOKAlert(this,"History","No History details found", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                    finish();
               }
           });

       }else
        getAppointmentHistoryList();
    }
    private void getAppointmentHistoryList() {
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

        Call<UpCommingAppointmentModel> call = apiService.getUpCommingAppointmentDetails(getString(R.string.token),"application/json",  getString(R.string.org_id), AppPreferene.with(this).getEmail());
        call.enqueue(new Callback<UpCommingAppointmentModel>() {
            @Override
            public void onResponse(Call<UpCommingAppointmentModel> call, Response<UpCommingAppointmentModel> response) {
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Result results = response.body().getResult();
                fillListView(Arrays.asList(results.getHistory()));

            }

            @Override
            public void onFailure(Call<UpCommingAppointmentModel> call, Throwable t) {
                // Log error here since request failed
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                Log.e(TAG, t.toString());
            }
        });



    }

    private void fillListView(List<History> histories) {
        mAppointments=new ArrayList<>();
        for (int i = 0; i <histories.size(); i++) {
            HistoryAppointment historyAppointment=new HistoryAppointment();
            historyAppointment.setLocation(histories.get(i).getLocName());
            historyAppointment.setName(histories.get(i).getUserName());
            historyAppointment.setConfirmationNo(histories.get(i).getConfirmationNo());
            historyAppointment.setDate(histories.get(i).getAppointmentDateTime());
            historyAppointment.setSerivice(histories.get(i).getServiceName());
            historyAppointment.setAppointment_id(histories.get(i).getAppointmentID());
            mAppointments.add(historyAppointment);
        }
        AppointmentHistoryAdapter adapter=new AppointmentHistoryAdapter(this,R.layout.appointment_list_item,mAppointments);
        mLvAppointmentHistoryList.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back_btn){
            finish();
        }
    }
}
