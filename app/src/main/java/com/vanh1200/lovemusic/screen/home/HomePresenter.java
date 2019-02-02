package com.vanh1200.lovemusic.screen.home;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter, TrackRemoteDataSource.OnGetTracksByGenre {
    private HomeContract.View mView;
    private TrackRepository mTrackRepository;

    public HomePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {
        mView.onFetchDataForSliderSuccess(tracks);
    }

    @Override
    public void onGetTracksFailed(String error) {
        mView.onFetchDataForSliderFailed(error);
    }

    @Override
    public void initDataForSlider(String genre) {
        mTrackRepository.getTracksByGenre(genre, this);
    }
}
