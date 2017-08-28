package com.effone.reservopia.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.effone.reservopia.R;
import com.effone.reservopia.adapter.AppointmentListAdapter;
import com.effone.reservopia.adapter.ServiceTypeAdapter;
import com.effone.reservopia.model.AppointmentDataTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocationServiceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
   private String response="[{\n" +
            "  \"date\":\"24/8/2017\",\n" +
            "  \"time\":\"1:19pm\",\n" +
            "  \"location\":\"Bangalore\",\n" +
            "  \"locationId\":\"1\",\n" +
            "  \"serviceType\":\"HairCut\",\n" +
            "  \"serviceTypeId\":\"1\"\n" +
            "},\n" +
            "{\n" +
            "  \"date\":\"24/8/2017\",\n" +
            "  \"time\":\"1:30pm\",\n" +
            "  \"location\":\"Bangalore\",\n" +
            "  \"locationId\":\"1\",\n" +
            "  \"serviceType\":\"HairCut\",\n" +
            "  \"serviceTypeId\":\"1\"\n" +
            "},\n" +
            "{\n" +
            "  \"date\":\"24/8/2017\",\n" +
            "  \"time\":\"2:19pm\",\n" +
            "  \"location\":\"Bangalore\",\n" +
            "  \"locationId\":\"1\",\n" +
            "  \"serviceType\":\"HairCut\",\n" +
            "  \"serviceTypeId\":\"1\"\n" +
            "}\n" +
            "\n" +
            "]";
    private ListView mLvServiceType;
    private ServiceTypeAdapter mServiceTypeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_service);
        mLvServiceType=(ListView)findViewById(R.id.lv_service_type);

        mServiceTypeAdapter=new ServiceTypeAdapter(this,  jsonObject(response));
        mLvServiceType.setAdapter(mServiceTypeAdapter);
        mLvServiceType.setOnItemClickListener(this);

    }

    public List<AppointmentDataTime> jsonObject(String response) {
        List<AppointmentDataTime> listItems=new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<AppointmentDataTime>>(){}.getType();
        listItems = gson.fromJson(response, type);
        return listItems;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent inte=new Intent(this,AppointementBookingActivity.class);
        startActivity(inte);
    }
}
