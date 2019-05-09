package com.vanh1200.lovemusic.data.source;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;

import java.util.List;

public class TrackDataSource {
    public interface LocalDataSource {
        List<Track> getLocalTracks();

        List<Track> getFavoriteTracks();

        List<Track> getDownloadTracks();

        List<Track> getPlaylists();

        boolean isFavoriteTrack(Track track);

        void addTrackToFavorite(Track track);

        void removeTrackFromFavorite(Track track);

    }

    public interface RemoteDataSource {
        List<Track> getTracksByGenre(String genre, int limit, int offset,
                                     TrackRemoteDataSource.OnGetTracksByGenre callback);

        List<Track> getSuggestedTracks(TrackRemoteDataSource.OnGetSuggestedTracks callback);

        List<Track> getTracksByQuery(String query, int limit, int offset,
                                     TrackRemoteDataSource.OnGetTracksByQuery callback);
    }
}
