package com.vanh1200.lovemusic.data.source.remote;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.source.TrackDataSource;
import com.vanh1200.lovemusic.utils.Constants;
import com.vanh1200.lovemusic.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {
    private static TrackRemoteDataSource sInstance;

    private TrackRemoteDataSource() {
    }

    public static TrackRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TrackRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public List<Track> getTracksByGenre(String genre, OnGetTracksByGenre callback) {
        List<Track> tracks = new ArrayList<>();
        if (callback != null) {
            new FetchTracksAsync(callback, null).execute(StringUtils.
                    generateGenreUrl(Constants.KIND_TOP,
                            genre, Constants.LIMIT_SLIDER,
                            Constants.OFFSET));
        }
        return tracks;
    }

    @Override
    public List<Track> getSuggestedTracks(OnGetSuggestedTracks callback) {
        List<Track> tracks = new ArrayList<>();
        if (callback != null) {
            new FetchTracksAsync(null, callback).execute(StringUtils.
                    generateGenreUrl(Constants.KIND_TREND,
                            Constants.GENRES_ALL_AUDIO,
                            Constants.LIMIT_SUGGESTED,
                            Constants.OFFSET_SUGGESTED));
        }
        return tracks;
    }

    public interface OnGetTracksByGenre {
        void onGetTracksSuccess(List<Track> tracks);

        void onGetTracksFailed(String error);
    }

    public interface OnGetSuggestedTracks {
        void onGetSuggestedTracksSuccess(List<Track> tracks);

        void onGetSuggestedTracksFailed(String error);
    }
}
