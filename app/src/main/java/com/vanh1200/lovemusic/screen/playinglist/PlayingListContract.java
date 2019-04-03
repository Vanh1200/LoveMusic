package com.vanh1200.lovemusic.screen.playinglist;

import com.vanh1200.lovemusic.base.BasePresenter;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.service.PlayMusicService;

import java.util.List;

public class PlayingListContract {
    interface View {
        void showPlayingList(List<Track> tracks);
    }

    interface Presenter extends BasePresenter<View> {
        void loadPlayingList(PlayMusicService musicService);
    }
}
