package com.effone.reservopia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.effone.reservopia.R;
import com.effone.reservopia.model.AppointmentDataTime;

import java.util.List;

/**
 * Created by sumanth.peddinti on 8/24/2017.
 */

public class ServiceTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<AppointmentDataTime> mAppointmentDateTime;
    private LayoutInflater mLayoutInflater ;
    public ServiceTypeAdapter(Context context, List<AppointmentDataTime> list) {
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
        final ServiceTypeItems holder;

        if(view==null) {
            view = mLayoutInflater.inflate(R.layout.service_type_list, null);
            holder = new  ServiceTypeItems();
            holder.mIvServiceTypeIcon=(ImageView)view.findViewById(R.id.img_service_type_icon);
            holder.mTvServiceType = (TextView)    view.findViewById(R.id.tv_serviceType);
        }else
            holder = (ServiceTypeItems) vi.getTag();

        holder.mTvServiceType.setText(mAppointmentDateTime.get(position).getServiceType());

        return view;
    }
    public static class ServiceTypeItems{
        public TextView mTvServiceType;
        public ImageView mIvServiceTypeIcon;
    }
}
