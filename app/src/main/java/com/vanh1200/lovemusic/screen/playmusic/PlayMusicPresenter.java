package com.vanh1200.lovemusic.screen.playmusic;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;

public class PlayMusicPresenter implements PlayMusicContract.Presenter {
    private PlayMusicContract.View mView;
    private TrackRepository mTrackRepository;

    public PlayMusicPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    public void addToFavorite(Track track){
        mTrackRepository.addTrackToFavorite(track);
    }

    public void removeFromFavorite(Track track){
        mTrackRepository.removeTrackFromFavorite(track);
    }

    @Override
    public boolean isFavoriteTrack(Track track) {
        return mTrackRepository.isFavoriteTrack(track);
    }

    @Override
    public void setView(PlayMusicContract.View view) {
        mView = view;
    }
}
