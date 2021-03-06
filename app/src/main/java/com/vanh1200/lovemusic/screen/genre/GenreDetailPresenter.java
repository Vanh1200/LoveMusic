package com.vanh1200.lovemusic.screen.genre;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.List;

public class GenreDetailPresenter implements GenreDetailContract.Presenter,
        TrackRemoteDataSource.OnGetTracksByGenre {
    private GenreDetailContract.View mView;
    private TrackRepository mTrackRepository;

    public GenreDetailPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void fetchTracksByGenre(String genre) {
        mTrackRepository.getTracksByGenre(genre,
                Constants.LIMIT,
                Constants.OFFSET,
                this);
    }

    @Override
    public void loadLocalTracks() {
        mView.showLocalTracks(mTrackRepository.getLocalTracks());
    }

    @Override
    public void loadFavoriteTracks() {
        mView.showFavoriteTracks(mTrackRepository.getFavoriteTracks());
    }

    @Override
    public void loadDownloadedTracks() {
        mView.showDownloadedTracks(mTrackRepository.getDownloadTracks());
    }

    @Override
    public void addToFavorite(Track track) {
        mTrackRepository.addTrackToFavorite(track);
    }

    public void setView(GenreDetailContract.View view) {
        mView = view;
    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {
        mView.onGetTracksSuccess(tracks);
    }

    @Override
    public void onGetTracksFailed(String error) {
        mView.onGetTracksFailed(error);
    }
}
