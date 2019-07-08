package com.example.sqlitetest;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
