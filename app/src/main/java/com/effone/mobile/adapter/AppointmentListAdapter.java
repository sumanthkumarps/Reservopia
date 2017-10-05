package com.effone.mobile.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.model.History;

import java.util.List;

/**
 * Created by sumanth.peddinti on 8/24/2017.
 */

@SuppressWarnings("deprecation")
public class AppointmentListAdapter extends BaseAdapter {
    private Context mContext;
    private List<History> mAppointmentDateTime;
    private LayoutInflater mLayoutInflater ;
    public AppointmentListAdapter(Context context, List<History> list) {
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
        View vi=view ;
        final AppointmentListItems holder;
        if(vi==null) {
            vi = mLayoutInflater.inflate(R.layout.date_time_slot, null);
            holder=new AppointmentListItems();
            holder.mTvDateTime= (TextView) vi.findViewById(R.id.ad_tv_date_time);
            vi.setTag(holder);

        }else
            holder = (AppointmentListItems) vi.getTag();

      String[] timedate= mAppointmentDateTime.get(position).getAppointmentDateTime().split(",");
        if (Build.VERSION.SDK_INT >= 24) {
            holder.mTvDateTime.setText(Html.fromHtml("<font color='#f1c40f'>"+timedate[1]+","+timedate[2]+"</font>"+" : "+"<font color='#F4F6F7'>"+timedate[0]+"</font>",100));
        } else {
            holder.mTvDateTime.setText(Html.fromHtml("<font color='#f1c40f'>"+timedate[1]+","+timedate[2]+"</font>"+" : "+"<font color='#F4F6F7'>"+timedate[0]+"</font>"));
        }
        return vi;
    }

    public static class AppointmentListItems{
        public TextView mTvDateTime;
    }
}
