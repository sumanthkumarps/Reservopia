package com.effone.reservopia.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class AppPreferene {
    private static final String PRE_LOAD = "preLoad";
    private static final String PREFS_NAME = "prefs";
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

}
