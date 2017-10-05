package com.effone.mobile.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class AppPreferene {
    private static final String PRE_LOAD = "preLoad";
    private static final String PREFS_NAME = "prefs";
    private static final String SERVICE_COUNT = "count" ;
    private static final String USER_ID = "user_id";
    private static final String EMAIL_ID = "email_id" ;
    private static AppPreferene instance;
    private final SharedPreferences sharedPreferences;

    public AppPreferene(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreferene with(Context context) {

        if (instance == null) {
            instance = new AppPreferene(context);
        }
        return instance;
    }

    public void setPreLoad(boolean isLoaded) {

        sharedPreferences
                .edit()
                .putBoolean(PRE_LOAD, isLoaded)
                .apply();
    }

    public boolean getPreLoad(){
        return sharedPreferences.getBoolean(PRE_LOAD, false);
    }


    public void setMulitpleService(boolean count) {

        sharedPreferences
                .edit()
                .putBoolean(SERVICE_COUNT, count)
                .apply();
    }

    public boolean getMulitpleService(){
        return sharedPreferences.getBoolean(SERVICE_COUNT, false);
    }


    public void setUserId(String  userId) {

        sharedPreferences
                .edit()
                .putString(USER_ID, userId)
                .apply();
    }

    public String getUserId(){
        return sharedPreferences.getString(USER_ID, "");
    }
    public void setEmail(String   email) {

        sharedPreferences
                .edit()
                .putString(EMAIL_ID, email)
                .apply();
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL_ID, "");
    }



}
