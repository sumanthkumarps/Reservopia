package com.effone.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.model.ProviderTable;
import com.effone.mobile.realmdb.ServiceTable;

import io.realm.RealmList;

/**
 * Created by sarith.vasu on 30-10-2017.
 */

public class ProviderTypeAdapter  extends BaseAdapter {
    private Context mContext;
    private RealmList<ProviderTable> mAppointmentDateTime;
    private LayoutInflater mLayoutInflater ;

    public ProviderTypeAdapter(Context context, RealmList<ProviderTable> list) {
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
        final ProviderTypeAdapter.ProviderTypeItems holder;

        if(vi==null) {
            vi = mLayoutInflater.inflate(R.layout.date_time_slot_grid, null);
            holder = new ProviderTypeAdapter.ProviderTypeItems();
            holder.mTvServiceType = (TextView)    vi.findViewById(R.id.ad_tv_date_time);

            vi.setTag( holder );
        }else
            holder = (ProviderTypeAdapter.ProviderTypeItems) vi.getTag();

        holder.mTvServiceType.setText(mAppointmentDateTime.get(position).getFirstName()+" "+mAppointmentDateTime.get(position).getLastName());

        return vi;
    }
    public static class ProviderTypeItems{
        public TextView mTvServiceType;
        public TextView mTvSerDescription;
        public ImageView mIvServiceTypeIcon;
    }
}
