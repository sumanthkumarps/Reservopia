package com.effone.reservopia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.effone.reservopia.MainActivity;
import com.effone.reservopia.R;
import com.effone.reservopia.model.AppointmentDataTime;

import java.util.List;

/**
 * Created by sumanth.peddinti on 8/24/2017.
 */

public class AppointmentListAdapter extends BaseAdapter {
    private Context mContext;
    private List<AppointmentDataTime> mAppointmentDateTime;
    private LayoutInflater mLayoutInflater ;
    public AppointmentListAdapter(Context context, List<AppointmentDataTime> list) {
        this.mContext=context;
        this.mAppointmentDateTime=list;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mAppointmentDateTime.size();
    }

    @Override
    public Object getItem(int i) {
        return mAppointmentDateTime.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vi = view;
        final AppointmentListItems holder;
        if(view==null) {
            view = mLayoutInflater.inflate(R.layout.date_time_slot, null);
            holder=new AppointmentListItems();
         holder.mTvDateTime= (TextView) view.findViewById(R.id.ad_tv_date_time);
        }else
            holder = (AppointmentListItems) vi.getTag();


        holder.mTvDateTime.setText(mAppointmentDateTime.get(position).getDate()+" "+mAppointmentDateTime.get(position).getTime());

        return view;
    }

    public static class AppointmentListItems{
        public TextView mTvDateTime;

    }
}
