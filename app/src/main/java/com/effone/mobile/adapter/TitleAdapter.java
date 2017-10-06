package com.effone.mobile.adapter;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.model.TitleNames;

import io.realm.RealmResults;

/**
 * Created by sumanth.peddinti on 10/4/2017.
 */

public class TitleAdapter extends BaseAdapter {
    Context context;
    RealmResults<TitleNames> mLocatin;
    LayoutInflater inflter;
    public TitleAdapter(Context context, RealmResults<TitleNames> mLocation) {
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
        view = inflter.inflate(R.layout.date_time_slot_grid, null);
        TextView names = (TextView) view.findViewById(R.id.ad_tv_date_time);
        names.setText(mLocatin.get(i).getText());
        return view;
    }
}

