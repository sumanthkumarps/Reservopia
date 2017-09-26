package com.effone.reservopia.app;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by sarith.vasu on 20-09-2017.
 */

public class ResApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
