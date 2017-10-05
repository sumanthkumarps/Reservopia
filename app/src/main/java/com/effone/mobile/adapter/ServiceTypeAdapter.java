package com.effone.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.realmdb.ServiceTable;

import io.realm.RealmList;

/**
 * Created by sumanth.peddinti on 8/24/2017.
 */

public class ServiceTypeAdapter extends BaseAdapter {
    private Context mContext;
    private RealmList<ServiceTable> mAppointmentDateTime;
    private LayoutInflater mLayoutInflater ;

    public ServiceTypeAdapter(Context context, RealmList<ServiceTable> list) {
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

        if(vi==null) {
            vi = mLayoutInflater.inflate(R.layout.service_type_list, null);
            holder = new  ServiceTypeItems();
            holder.mTvServiceType = (TextView)    vi.findViewById(R.id.tv_serviceType);
            holder.mTvSerDescription=(TextView)    vi.findViewById(R.id.tv_ser_desc);
            holder.mIvServiceTypeIcon=(ImageView) vi.findViewById(R.id.iv_appointment);
            vi.setTag( holder );
        }else
            holder = (ServiceTypeItems) vi.getTag();

        holder.mTvServiceType.setText(mAppointmentDateTime.get(position).getServiceName());
        holder.mTvSerDescription.setText(mAppointmentDateTime.get(position).getDescription());
        if(mAppointmentDateTime.size() != 1 || mAppointmentDateTime.size() >1){
            holder.mIvServiceTypeIcon.setVisibility(View.VISIBLE);
        }else
            holder.mIvServiceTypeIcon.setVisibility(View.INVISIBLE);

        return vi;
    }
    public static class ServiceTypeItems{
        public TextView mTvServiceType;
        public TextView mTvSerDescription;
        public ImageView mIvServiceTypeIcon;
    }
}
