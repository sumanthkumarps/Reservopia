package com.effone.mobile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.effone.mobile.R;
import com.effone.mobile.model.TimeSlotStrings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */

public class TimeSlotAdapter  extends BaseAdapter {
    private Context mContext;
    private ArrayList<TimeSlotStrings> web;
    private String formattedDate;


    public TimeSlotAdapter(Context c, ArrayList<TimeSlotStrings> web ) {
        mContext = c;
        this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return web.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid=convertView;
        final  TimeSlotsFiel mTimeSlotsFiel;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            mTimeSlotsFiel = new TimeSlotsFiel();
         convertView = inflater.inflate(R.layout.gird_single, null);
            mTimeSlotsFiel.mTvDateTime = (TextView) convertView.findViewById(R.id.grid_text);
            convertView.setTag(mTimeSlotsFiel);
        }else {
            mTimeSlotsFiel = (TimeSlotsFiel) convertView.getTag();
        }
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
            DateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);

            try {
                formattedDate = timeFormat.format(dateFormat.parse(web.get(position).getStartTime()));
                mTimeSlotsFiel.mTvDateTime.setText(formattedDate);

              //  textView.setLayoutParams(new GridView.LayoutParams(144, 144));
            } catch (Exception e) {
                Log.e("DATE", "" + e);
            }







        return convertView;
    }
    public static class TimeSlotsFiel {
        public TextView mTvDateTime;

    }
}
