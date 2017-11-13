package com.effone.mobile.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.mobile.MainActivity;
import com.effone.mobile.R;
import com.effone.mobile.adapter.AppointmentListAdapter;
import com.effone.mobile.common.AppPreferene;
import com.effone.mobile.common.ResvUtils;
import com.effone.mobile.common.Validation;
import com.effone.mobile.model.History;
import com.effone.mobile.model.Result;
import com.effone.mobile.model.SearchAppointment;
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
    private TextView mTvSearch;
    private String TAG="SearchActivity";

    private TextInputLayout mTvLastName,mTvDateOfBirth,mTvConfirmation;

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
    @Override
    protected void onPause() {
        super.onPause();
       hidingKeyBoard();
    }

    private void hidingKeyBoard() {
        View views = this.getCurrentFocus();
        if (views != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(views.getWindowToken(), 0);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void declare() {
        mEtLastName=(EditText)findViewById(R.id.et_last_name);
        mEtDob=(EditText)findViewById(R.id.et_date_birth);
        mTvSearch=(TextView)findViewById(R.id.tv_search_and_title);
        mEtDob.addTextChangedListener(mDateEntryWatcher);
        mEtConfirmNo=(EditText)findViewById(R.id.et_confirmation_no);
        mLlSearch=(LinearLayout)findViewById(R.id.search_result_lay);
        mLlSearch.setVisibility(View.GONE);
        mBtSubmit=(Button)findViewById(R.id.bt_submit);
        mBtSubmit.setTransformationMethod(null);
        mTvEmptyView=(LinearLayout) findViewById(R.id.tv_empty_view);
        mBtSubmit.setOnClickListener(this);
        mTvLastName=(TextInputLayout)findViewById(R.id.input_layout_last);
        mTvDateOfBirth=(TextInputLayout)findViewById(R.id.input_layout_date_birth);
        mTvConfirmation=(TextInputLayout)findViewById(R.id.input_layout_coniframtion_no);
        mBtSubmit.setTransformationMethod(null);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvCountAppointment=(TextView)findViewById(R.id.tv_count_appointments);
        mTvTitle.setText(getString(R.string.search));
        mLvAppointmentList=(ListView)findViewById(R.id.lv_upcomingAppointent);
        mLvAppointmentList.setOnItemClickListener(this);
            final Validation validation=new Validation();

        mEtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                if (!validation.isValidFirstName(text)) {
                    mTvLastName.setError(getString(R.string.last_error_msg));
                } else {
                    mTvLastName.setErrorEnabled(false);
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_submit){
            SearchApiCall();
        }

    }

    private void SearchApiCall() {
        Validation validation =new Validation();
        String strLastName=mEtLastName.getText().toString();
        String strDob=mEtDob.getText().toString();
        String strConfirmationNo=mEtConfirmNo.getText().toString();
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        }
        if(!validation.isValidFirstName(strLastName)){
            mTvLastName.setError(getString(R.string.last_error_msg));
        }else{
            int count=0;
            if(strConfirmationNo.equals("")) {
                strConfirmationNo = "0";
                count=0;
            }
            if(!strDob.equals("")||!strConfirmationNo.equals("0")) {
                searchingData(strLastName,strDob,strConfirmationNo);
                mTvDateOfBirth.setError(null);
            }else{
                mTvDateOfBirth.setError(getString(R.string.dobMsg));

            }

        }


    }

    private void searchingData(String strLastName, String strDob, String strConfirmationNo) {

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

        Call<SearchAppointment> call = apiService.getSearchAppointment(getString(R.string.token), getString(R.string.org_id), strLastName,strDob,strConfirmationNo);

        call.enqueue(new Callback<SearchAppointment>() {
            @Override
            public void onResponse(Call<SearchAppointment> call, Response<SearchAppointment> response) {
                response.raw().request().url();
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                try {
                    if (!response.isSuccessful()) {
                        ResvUtils.createOKAlert(SearchAppointmentActivity.this, "Error", "No Appointment Found.");
                    } else {
                        histories = response.body().getResult();

                        if (histories.size() > 0) {
                            mLlSearch.setVisibility(View.VISIBLE);
                            if(histories.size()>1)
                                mTvSearch.setText(getString(R.string.upcomingAppointments));
                            else
                                mTvSearch.setText(getString(R.string.upcomingAppointment));
                            mAppointmentListAdapter = new AppointmentListAdapter(SearchAppointmentActivity.this, histories);
                            mTvEmptyView.setVisibility(View.GONE);
                            mLvAppointmentList.setVisibility(View.VISIBLE);
                            mLvAppointmentList.setAdapter(mAppointmentListAdapter);
                            mLvAppointmentList.setOnItemClickListener(SearchAppointmentActivity.this);
                        } else {
                            mLlSearch.setVisibility(View.GONE);
                            mTvSearch.setText(getString(R.string.upcomingAppointment));
                            removingCompleteData();
                            ResvUtils.createOKAlert(SearchAppointmentActivity.this, "Error", "No Search Found.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                        }

                        mTvCountAppointment.setText("" + histories.size());
                        hidingKeyBoard();
                    }
                }catch(Exception e){
                        mLvAppointmentList.setEmptyView(findViewById(R.id.tv_history));
                    }
                }

            @Override
            public void onFailure(Call<SearchAppointment> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                if (mCommonProgressDialog != null)
                    mCommonProgressDialog.cancel();
                ResvUtils.createOKAlert(SearchAppointmentActivity.this, "Error", "No Search Found.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
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
        History appointmentDataTime=(History) mLvAppointmentList.getItemAtPosition(i);
        Intent intent=new Intent(this,AppointmentDetailsActivity.class);
        intent.putExtra("selectedItem",appointmentDataTime);
        startActivity(intent);

    }
}
