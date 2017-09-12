package com.effone.reservopia.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.adapter.AppointmentHistoryAdapter;
import com.effone.reservopia.model.HistoryAppointment;

import java.util.ArrayList;

public class AppointmentHistoryActivity extends AppCompatActivity {
    private TextView mTvTitle;
    private ListView mLvAppointmentHistoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);
        init();
    }

    private void init() {
        mTvTitle=(TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(getString(R.string.appointment_history));
        ArrayList<HistoryAppointment> appointments=new ArrayList<>();
        HistoryAppointment h1=new HistoryAppointment();
        h1.setLocation("Bangalore");
        h1.setName("Sumanth");
        h1.setDate("08/29/2017 : 01:00 PM");
        h1.setSerivice("Hair cut");
        h1.setAppointment_id("33333");
        appointments.add(h1);
        HistoryAppointment h2=new HistoryAppointment();
        h2.setLocation("Manglore");
        h2.setName("Sumanth");
        h2.setDate("08/26/2017 : 01:00 PM");
        h2.setSerivice("Sahaving");
        h2.setAppointment_id("33373");
        appointments.add(h2);
        HistoryAppointment h3=new HistoryAppointment();
        h3.setLocation("Hindupur");
        h3.setName("Sumanth");
        h3.setDate("08/21/2017 : 01:00 PM");
        h3.setSerivice("Facial");
        h3.setAppointment_id("33353");
        appointments.add(h3);
        mLvAppointmentHistoryList=(ListView)findViewById(R.id.lv_appointment_history);
        AppointmentHistoryAdapter adapter=new AppointmentHistoryAdapter(this,R.layout.appointment_list_item,appointments);
        mLvAppointmentHistoryList.setAdapter(adapter);

    }
}
