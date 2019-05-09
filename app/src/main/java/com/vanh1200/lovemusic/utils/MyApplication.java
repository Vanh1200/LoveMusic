package com.vanh1200.lovemusic.utils;

import android.app.Application;

public class MyApplication extends Application{
    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        if(sInstance == null){
            sInstance = new MyApplication();
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
