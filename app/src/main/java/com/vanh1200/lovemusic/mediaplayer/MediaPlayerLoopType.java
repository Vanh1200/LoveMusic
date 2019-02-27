package com.vanh1200.lovemusic.mediaplayer;

import android.support.annotation.IntDef;


@IntDef({MediaPlayerLoopType.NONE,
        MediaPlayerLoopType.ONE,
        MediaPlayerLoopType.ALL})
public @interface MediaPlayerLoopType {
    int NONE = 0;
    int ONE = 1;
    int ALL = 2;
}
