package com.vanh1200.lovemusic.screen.home;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter,
        TrackRemoteDataSource.OnGetTracksByGenre,
        TrackRemoteDataSource.OnGetSuggestedTracks {
    private HomeContract.View mView;
    private TrackRepository mTrackRepository;
    private boolean isSlidesLoaded;
    private boolean isSuggestionLoaded;

    public HomePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
        isSlidesLoaded = false;
        isSuggestionLoaded = false;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {
        isSuggestionLoaded = true;
        mView.onFetchDataForSliderSuccess(tracks);
        mView.onFetchDataComplete(isSlidesLoaded && isSuggestionLoaded);
    }

    @Override
    public void onGetTracksFailed(String error) {
        mView.onFetchDataForSliderFailed(error);
    }

    @Override
    public void initDataForSlider(String genre) {
        mTrackRepository.getTracksByGenre(genre,
                        8,
                        Constants.OFFSET,
                        this);
        isSlidesLoaded = true;
        mView.onFetchDataComplete(isSlidesLoaded && isSuggestionLoaded);
    }

    @Override
    public void initDataForSuggestedTracks() {
        mTrackRepository.getSuggestedTracks(this);
    }

    @Override
    public void onGetSuggestedTracksSuccess(List<Track> tracks) {
        mView.onFetchDataForSuggestedSuccess(tracks);
    }

    @Override
    public void onGetSuggestedTracksFailed(String error) {
        mView.onFetchDataForSuggestedFailed(error);
    }

}
