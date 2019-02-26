package com.vanh1200.lovemusic.screen.playinglist;

import com.vanh1200.lovemusic.service.PlayMusicService;

public class PlayingListPresenter implements PlayingListContract.Presenter{
    PlayingListContract.View mView;

    @Override
    public void loadPlayingList(PlayMusicService musicService) {
        mView.showPlayingList(musicService.getTracks());
    }

    @Override
    public void setView(PlayingListContract.View view) {
        mView = view;
    }
}
