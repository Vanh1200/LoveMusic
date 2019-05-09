package com.vanh1200.lovemusic.data.source.local;

import android.content.Context;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.source.TrackDataSource;
import com.vanh1200.lovemusic.data.source.local.database.DatabaseHelper;
import com.vanh1200.lovemusic.data.source.local.sdcard.SdCardHelper;
import com.vanh1200.lovemusic.utils.Constants;
import com.vanh1200.lovemusic.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    private Context mContext;
    private static TrackLocalDataSource sInstance;
    private SdCardHelper mSdCardHelper;
    private DatabaseHelper mDatabaseHelper;

    private TrackLocalDataSource(Context context) {
        mContext = MyApplication.getInstance().getApplicationContext();
        mSdCardHelper = new SdCardHelper();
        mDatabaseHelper = new DatabaseHelper(mContext);
    }

    public static TrackLocalDataSource getInstance(Context context) {
        if(sInstance == null){
            sInstance = new TrackLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public List<Track> getLocalTracks() {
        return mSdCardHelper.getTrackFromLocal();
    }

    @Override
    public List<Track> getFavoriteTracks() {
        return mDatabaseHelper.getFavorites();
    }

    @Override
    public List<Track> getDownloadTracks() {
        List<Track> tracks = getLocalTracks();
        List<Track> resultTracks = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            if(tracks.get(i).getStreamUrl().contains("Love Music")){
                resultTracks.add(tracks.get(i));
            }
        }
        return resultTracks;
    }

    @Override
    public List<Track> getPlaylists() {
        return null;
    }

    @Override
    public boolean isFavoriteTrack(Track track) {
        return mDatabaseHelper.checkFavorite(track);
    }

    @Override
    public void addTrackToFavorite(Track track) {
        mDatabaseHelper.addFavorite(track);
    }

    @Override
    public void removeTrackFromFavorite(Track track) {
        mDatabaseHelper.removeFavorite(track);
    }
}
