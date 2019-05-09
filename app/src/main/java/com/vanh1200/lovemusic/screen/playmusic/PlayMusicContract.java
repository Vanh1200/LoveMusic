package com.vanh1200.lovemusic.screen.playmusic;

import com.vanh1200.lovemusic.base.BasePresenter;
import com.vanh1200.lovemusic.data.model.Track;

public class PlayMusicContract {
    interface View {
        void updateTrackInformation(Track track);
    }

    interface Presenter extends BasePresenter<View> {
        void addToFavorite(Track track);

        void removeFromFavorite(Track track);

        boolean isFavoriteTrack(Track track);
    }
}
