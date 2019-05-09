package com.vanh1200.lovemusic.screen.search;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter, TrackRemoteDataSource.OnGetTracksByQuery {
    private SearchContract.View mView;
    private TrackRepository mRepository;
    private boolean isLoadMore = false;

    public SearchPresenter(TrackRepository trackRepository) {
        mRepository = trackRepository;
    }

    @Override
    public void loadHistory() {
    }

    @Override
    public void loadResult(String query, int limit, int offset) {
        isLoadMore = false;
        mRepository.getTracksByQuery(query, limit, offset, this);
    }

    @Override
    public void loadMore(String query, int limit, int offset) {
        isLoadMore = true;
        mRepository.getTracksByQuery(query, limit, offset, this);
    }

    @Override
    public void addToFavorite(Track track) {
        mRepository.addTrackToFavorite(track);
    }

    @Override
    public void setView(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {
        if (isLoadMore){
            mView.showMore(tracks);
        } else {
            mView.showResultSuccess(tracks);
        }
    }

    @Override
    public void onGetTracksFailed(String error) {
        mView.showResultFailed(error);
    }
}
