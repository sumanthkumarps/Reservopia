package com.effone.mobile.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.effone.mobile.R;
import com.effone.mobile.model.HistoryAppointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarith.vasu on 29-08-2017.
 */

public class AppointmentHistoryAdapter extends ArrayAdapter<HistoryAppointment> {
    private Context mContext;
    private List<HistoryAppointment> mhistoryAppointments;
    private LayoutInflater mLayoutInflater ;
    public AppointmentHistoryAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<HistoryAppointment> historyAppointments) {
        super(context, resource, historyAppointments);
        this.mContext=context;
        this.mhistoryAppointments=historyAppointments;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final AppointmentListItems holder;
        if(vi==null) {
            vi = mLayoutInflater.inflate(R.layout.appointment_list_item, null);
            holder=new AppointmentListItems();
            holder.mTvDate= (TextView) vi.findViewById(R.id.tv_Date_of_selection_details);
            holder.mTvAppointmentId= (TextView) vi.findViewById(R.id.tv_appointment_id);
            holder.mTvLocation= (TextView) vi.findViewById(R.id.tv_lab_address_details);
            holder.mTvName= (TextView) vi.findViewById(R.id.tv_nameofperson);
            holder.mTvService= (TextView) vi.findViewById(R.id.tv_type_service_details);
            holder.mTvProvider= (TextView) vi.findViewById(R.id.tv_provider_history);
            holder.mTvStatus= (TextView) vi.findViewById(R.id.tv_status_history);
            vi.setTag( holder );
        }else
            holder = (AppointmentListItems) vi.getTag();


        holder.mTvDate.setText(Html.fromHtml(mhistoryAppointments.get(position).getDate()));
        holder.mTvAppointmentId.setText(mhistoryAppointments.get(position).getConfirmationNo());
        holder.mTvLocation.setText(mhistoryAppointments.get(position).getLocation());
        holder.mTvName.setText(mhistoryAppointments.get(position).getName());
        holder.mTvService.setText(mhistoryAppointments.get(position).getSerivice());

        holder.mTvProvider.setText(mhistoryAppointments.get(position).getProvider());
        holder.mTvStatus.setText(mhistoryAppointments.get(position).getStatus());
        return vi;
    }

    public static class AppointmentListItems{
        public TextView mTvDate;
        public TextView mTvAppointmentId;
        public TextView mTvService;
        public TextView mTvName;
        public TextView mTvLocation;
        public TextView mTvProvider;
        public TextView mTvStatus;


    }
}
