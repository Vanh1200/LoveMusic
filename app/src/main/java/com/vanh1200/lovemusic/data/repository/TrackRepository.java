package com.vanh1200.lovemusic.data.repository;

import android.util.Log;
import android.widget.Toast;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.source.TrackDataSource;
import com.vanh1200.lovemusic.data.source.local.TrackLocalDataSource;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;

import java.util.List;

public class TrackRepository implements TrackDataSource.LocalDataSource,
        TrackDataSource.RemoteDataSource {
    private static TrackRepository sInstance;
    private TrackLocalDataSource mTrackLocalDataSource;
    private TrackRemoteDataSource mRemoteDataSource;

    private TrackRepository(TrackLocalDataSource trackLocalDataSource,
                            TrackRemoteDataSource remoteDataSource) {
        mTrackLocalDataSource = trackLocalDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static TrackRepository getInstance(TrackLocalDataSource trackLocalDataSource,
                                              TrackRemoteDataSource trackRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new TrackRepository(trackLocalDataSource, trackRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public List<Track> getTracksByGenre(String genre, int limit, int offset,
                                        TrackRemoteDataSource.OnGetTracksByGenre callback) {
        return mRemoteDataSource.getTracksByGenre(genre, limit, offset, callback);
    }

    @Override
    public List<Track> getSuggestedTracks(TrackRemoteDataSource.OnGetSuggestedTracks callback) {
        return mRemoteDataSource.getSuggestedTracks(callback);
    }

    @Override
    public List<Track> getTracksByQuery(String query, int limit, int offset, TrackRemoteDataSource.OnGetTracksByQuery callback) {
        return mRemoteDataSource.getTracksByQuery(query, limit, offset, callback);
    }

    @Override
    public List<Track> getLocalTracks() {
        return mTrackLocalDataSource.getLocalTracks();
    }

    @Override
    public List<Track> getFavoriteTracks() {
        return mTrackLocalDataSource.getFavoriteTracks();
    }

    @Override
    public List<Track> getDownloadTracks() {
        return mTrackLocalDataSource.getDownloadTracks();
    }

    @Override
    public List<Track> getPlaylists() {
        return null;
    }

    @Override
    public boolean isFavoriteTrack(Track track) {
        return mTrackLocalDataSource.isFavoriteTrack(track);
    }

    @Override
    public void addTrackToFavorite(Track track) {
        mTrackLocalDataSource.addTrackToFavorite(track);
    }

    @Override
    public void removeTrackFromFavorite(Track track) {
        mTrackLocalDataSource.removeTrackFromFavorite(track);
    }


}
