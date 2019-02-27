package com.vanh1200.lovemusic.mediaplayer;

import android.support.annotation.IntDef;

@IntDef({MediaPlayerShuffleType.OFF,
        MediaPlayerShuffleType.ON})
public @interface MediaPlayerShuffleType {
    int OFF = 0;
    int ON = 1;
}
