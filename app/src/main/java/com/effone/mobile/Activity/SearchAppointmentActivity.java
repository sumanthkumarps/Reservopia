package com.effone.mobile.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.adapter.AppointmentListAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.model.History;
import com.effone.mobile.model.Result;
import com.effone.mobile.model.UpCommingAppointmentModel;
import com.effone.mobile.rest.ApiClient;
import com.effone.mobile.rest.ApiInterface;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAppointmentActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private EditText mEtLastName;
    private EditText mEtDob;
    private EditText mEtConfirmNo;
    private Button mBtSubmit;
    private TextView mTvTitle;
    private LinearLayout mTvEmptyView;
    private ListView mLvAppointmentList;
    private TextView mTvCountAppointment;
    private LinearLayout mLlSearch;
    private ProgressDialog mCommonProgressDialog;
    private AppointmentListAdapter mAppointmentListAdapter;
    private List<History> histories;
    private String TAG="SearchActivity";

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
        mEtLastName=(EditText)findViewById(R.id.et_last_name);
        mEtDob=(EditText)findViewById(R.id.et_date_birth);
        mEtDob.addTextChangedListener(mDateEntryWatcher);
        mEtConfirmNo=(EditText)findViewById(R.id.et_confirmation_no);
        mLlSearch=(LinearLayout)findViewById(R.id.search_result_lay);
        mLlSearch.setVisibility(View.GONE);
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
            SearchApiCall();
        }

    }

    private void SearchApiCall() {
        String strLastName=mEtLastName.getText().toString();
        String strDob=mEtDob.getText().toString();
        String strConfirmationNo=mEtConfirmNo.getText().toString();
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

        Call<UpCommingAppointmentModel> call = apiService.getSearchAppointment(getString(R.string.token), getString(R.string.org_id), strLastName,strDob,strConfirmationNo);

        call.enqueue(new Callback<UpCommingAppointmentModel>() {
            @Override
            public void onResponse(Call<UpCommingAppointmentModel> call, Response<UpCommingAppointmentModel> response) {
                response.raw().request().url();
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                try {
                    Result results = response.body().getResult();
                    histories = Arrays.asList(results.getUpcoming());
                    if(histories.size()>0) {
                        mAppointmentListAdapter = new AppointmentListAdapter(SearchAppointmentActivity.this, histories);
                        mTvEmptyView.setVisibility(View.GONE);
                        mLvAppointmentList.setVisibility(View.VISIBLE);
                        mLvAppointmentList.setAdapter(mAppointmentListAdapter);
                        mLvAppointmentList.setOnItemClickListener(SearchAppointmentActivity.this);
                    }else
                        removingCompleteData();
                    mTvCountAppointment.setText("" + histories.size());
                }catch (Exception e){
                    mLvAppointmentList.setEmptyView(findViewById(R.id.tv_history));
                }
            }

            @Override
            public void onFailure(Call<UpCommingAppointmentModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                mLvAppointmentList.setEmptyView(findViewById(R.id.tv_history));

            }
        });


    }

    private void removingCompleteData() {
        mTvCountAppointment.setText(""+0);
        mLvAppointmentList.setAdapter(null);
        mLvAppointmentList.setVisibility(View.GONE);
        mTvEmptyView.setVisibility(View.VISIBLE);
    }
    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            boolean isValidYear = true;
            if (working.length() == 2 && before == 0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 12) {
                    isValid = false;
                } else {
                    working += "/";
                    mEtDob.setText(working);
                    mEtDob.setSelection(working.length());
                }
            } else if (working.length() == 5 && before == 0) {
                String month = working.substring(3);
                if (Integer.parseInt(month) > 31) {
                    isValid = false;
                } else {
                    working += "/";
                    mEtDob.setText(working);
                    mEtDob.setSelection(working.length());
                }
            } else if (working.length() == 10 && before == 0) {
                String enteredYear = working.substring(6);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int minYear = currentYear - 120;
                int intEnterYear = Integer.parseInt(enteredYear);
                if (intEnterYear > currentYear || intEnterYear < minYear) {
                    isValidYear = false;
                }
            } else if (working.length() != 10) {
                isValid = false;
            }

            if (!isValid || !isValidYear) {
                if (!isValidYear)
                    mEtDob.setError("Invalid Year");
                else
                    mEtDob.setError(getString(R.string.dobMsg));
            } else {
                mEtDob.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
