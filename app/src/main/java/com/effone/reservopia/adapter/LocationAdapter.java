package com.effone.reservopia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.model.Locations;
import com.effone.reservopia.realmdb.LocationTable;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by sumanth.peddinti on 9/13/2017.
 */

public class LocationAdapter extends BaseAdapter {
    Context context;
    RealmResults<LocationTable> mLocatin;
    LayoutInflater inflter;
    public LocationAdapter(Context context, RealmResults<LocationTable> mLocation) {
        this.context=context;
        this.mLocatin = mLocation;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return mLocatin.size();
    }

    @Override
    public Object getItem(int i) {
        return mLocatin.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.date_time_slot, null);
        TextView names = (TextView) view.findViewById(R.id.ad_tv_date_time);
        names.setText(mLocatin.get(i).getLocName());
        return view;
    }
}
