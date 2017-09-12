package com.effone.reservopia.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import java.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.effone.reservopia.R;

import java.util.Locale;

public class AppointementBookingActivity extends AppCompatActivity {
    private String[] arraySpinner;

    private Calendar myCalendar;
    private EditText mEtDate;
    private GridView mGvTimeSlots;
    private TextView mTvSubmit;
    private TextView mTvTitle;
    private static final String[] numbers = new String[]{
            "10:15AM", "10:35AM", "11:15AM", "11:35AM", "12:15AM",
            "12:35AM", "01:15PM", "01:35PM", "02:15AM", "02:35AM",
            "03:15PM", "03:35PM", "03:15PM", "11:15AM", "04:15AM",
            "10:15AM", "10:35AM", "11:15AM", "11:35AM", "12:15AM"
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement_booking);
        myCalendar = Calendar.getInstance();
        this.arraySpinner = new String[]{
                " ", "Mr", "Miss", "Ms", "Mrs", "Dr"
        };
        mGvTimeSlots = (GridView) findViewById(R.id.gv_timeSlots);

        settingAdapter();
        declarations();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void declarations() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(myCalendar.getTime());
        mEtDate = (EditText) findViewById(R.id.et_date);
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.booking_app));
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(AppointementBookingActivity.this, AppointmentAcknowledgementActivity.class);
            }
        });
        mEtDate.setText(formattedDate);
        mEtDate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(AppointementBookingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEtDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void settingAdapter() {

        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this,
                R.layout.date_time_slot_grid, numbers);

        mGvTimeSlots.setAdapter(adapters);


    }

    // Method for converting DP value to pixels
    public static int getPixelsFromDPs(Activity activity, int dps) {
        Resources r = activity.getResources();
        int px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }
    public void openActivity(Context context, Class<?> calledActivity){
        Intent intent = new Intent(context,calledActivity);
        startActivity(intent);
    }
}
