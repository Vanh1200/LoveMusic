package com.vanh1200.lovemusic.mediaplayer;

import android.support.annotation.IntDef;

@IntDef({MediaPlayerStateType.PLAY,
        MediaPlayerStateType.PAUSE,
        MediaPlayerStateType.PREPARE})
public @interface MediaPlayerStateType {
    int PLAY = 0;
    int PAUSE = 1;
    int PREPARE = 2;
}
