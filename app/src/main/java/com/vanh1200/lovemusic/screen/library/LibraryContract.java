package com.vanh1200.lovemusic.screen.library;

import com.vanh1200.lovemusic.base.BasePresenter;
import com.vanh1200.lovemusic.data.model.Track;

import java.util.List;

public interface LibraryContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showLocalTracks(List<Track> tracks);

        void showFavoriteTracks(List<Track> tracks);

        void showDownloadedTracks(List<Track> tracks);

        void showPlayLists();
    }

    interface Presenter extends BasePresenter<View> {
        void loadPlayLists();

        void loadDownloadedTracks();

        void loadFavoriteTracks();

        void loadLocalTracks();
    }
}
