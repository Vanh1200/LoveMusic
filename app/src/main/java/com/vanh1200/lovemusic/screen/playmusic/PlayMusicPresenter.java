package com.vanh1200.lovemusic.screen.playmusic;

public class PlayMusicPresenter implements PlayMusicContract.Presenter {
    private PlayMusicContract.View mView;

    @Override
    public void setView(PlayMusicContract.View view) {
        mView = view;
    }
}
