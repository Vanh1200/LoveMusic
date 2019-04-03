package com.vanh1200.lovemusic.mediaplayer;

import android.support.annotation.IntDef;

@IntDef({MediaPlayerStateType.PLAY,
        MediaPlayerStateType.PAUSE})
public @interface MediaPlayerStateType {
    int PLAY = 0;
    int PAUSE = 1;
}
