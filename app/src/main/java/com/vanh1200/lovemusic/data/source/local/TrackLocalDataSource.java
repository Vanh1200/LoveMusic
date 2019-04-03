package com.vanh1200.lovemusic.data.source.local;

import android.content.Context;

import com.vanh1200.lovemusic.data.source.TrackDataSource;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    private Context mContext;
    private static TrackLocalDataSource sInstance;

    private TrackLocalDataSource(Context context) {
        mContext = context;
    }

    public static TrackLocalDataSource getInstance(Context context) {
        if(sInstance == null){
            sInstance = new TrackLocalDataSource(context);
        }
        return sInstance;
    }
}
