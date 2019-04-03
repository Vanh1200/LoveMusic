package com.vanh1200.lovemusic.screen.genre;

import com.vanh1200.lovemusic.data.model.Track;

import java.util.List;

public interface GenreDetailContract {
    interface View {
        void initRecycler(List<Track> tracks);

        void onGetTracksFailed(String error);

        void onGetTracksSuccess(List<Track> tracks);
    }

    interface Presenter {
        void fetchTracksByGenre(String genre);
    }
}
