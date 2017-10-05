package com.effone.mobile.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.effone.mobile.Activity.NetworkErrorActivity;

/**
 * Created by sumanth.peddinti on 9/26/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver
{
    public  static  boolean connectionValue=false;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (!isOnline(context)) {
                //dialog(true);
                connectionValue=true;
                openActivity(context,NetworkErrorActivity.class);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void openActivity(Context context,Class<?> calledActivity){
        Intent intent = new Intent(context,calledActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public  boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

}
