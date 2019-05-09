package com.vanh1200.lovemusic.screen.genre;

import com.vanh1200.lovemusic.data.model.Track;

import java.util.List;

public interface GenreDetailContract {
    interface View {
        void initRecycler(List<Track> tracks);

        void onGetTracksFailed(String error);

        void onGetTracksSuccess(List<Track> tracks);

        void showLocalTracks(List<Track> tracks);

        void showFavoriteTracks(List<Track> tracks);

        void showDownloadedTracks(List<Track> tracks);
    }

    interface Presenter {
        void fetchTracksByGenre(String genre);

        void loadLocalTracks();

        void loadFavoriteTracks();

        void loadDownloadedTracks();

        void addToFavorite(Track track);

    }
}
