package com.vanh1200.lovemusic.service;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerStateType;

public interface PlayMusicListener {
    void onPlayingStateListener(@MediaPlayerStateType int state);

    void onTrackChangedListener(Track track);
}
