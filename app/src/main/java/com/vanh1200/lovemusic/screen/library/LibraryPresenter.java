package com.vanh1200.lovemusic.screen.library;

import com.vanh1200.lovemusic.data.repository.TrackRepository;

public class LibraryPresenter implements LibraryContract.Presenter{
    private TrackRepository mTrackRepository;
    private LibraryContract.View mView;

    public LibraryPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void loadPlayLists() {

    }

    @Override
    public void loadDownloadedTracks() {

    }

    @Override
    public void loadFavoriteTracks() {

    }

    @Override
    public void loadLocalTracks() {
        mView.showLocalTracks(mTrackRepository.getLocalTracks());
    }

    @Override
    public void setView(LibraryContract.View view) {
        mView = view;
    }
}
