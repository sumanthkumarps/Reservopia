package com.effone.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.model.TimeZoneDetails;

import io.realm.RealmResults;

/**
 * Created by sumanth.peddinti on 9/27/2017.
 */


public class TimeZoneAdapter extends BaseAdapter {
    Context context;
    RealmResults<TimeZoneDetails> mTimeZone;
    LayoutInflater inflter;
    public TimeZoneAdapter(Context context, RealmResults<TimeZoneDetails> mTimeZone) {
        this.context=context;
        this.mTimeZone = mTimeZone;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return mTimeZone.size();
    }

    @Override
    public Object getItem(int i) {
        return mTimeZone.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.date_time_slot_grid, null);
        TextView names = (TextView) view.findViewById(R.id.ad_tv_date_time);
        names.setText(mTimeZone.get(i).getDisplayShortName());
        return view;
    }
}
