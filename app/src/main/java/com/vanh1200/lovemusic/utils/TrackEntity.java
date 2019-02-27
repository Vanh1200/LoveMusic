package com.vanh1200.lovemusic.utils;

import android.support.annotation.StringDef;

@StringDef({
        TrackEntity.ARTWORK_URL,
        TrackEntity.DESCRIPTION,
        TrackEntity.DOWNLOADABLE,
        TrackEntity.STREAMABLE,
        TrackEntity.DURATION,
        TrackEntity.GENRE,
        TrackEntity.ID,
        TrackEntity.PUBLISHER_METADATA,
        TrackEntity.PLAYBACK_COUNT,
        TrackEntity.LIKES_COUNT,
        TrackEntity.DOWNLOAD_COUNT
})

public @interface TrackEntity {
    String ARTWORK_URL = "artwork_url";
    String DESCRIPTION = "description";
    String DOWNLOADABLE = "downloadable";
    String DURATION = "duration";
    String GENRE = "genre";
    String ID = "id";
    String TITLE = "title";
    String PUBLISHER_METADATA = "publisher_metadata";
    String STREAMABLE = "streamable";
    String PLAYBACK_COUNT = "playback_count";
    String LIKES_COUNT = "likes_count";
    String DOWNLOAD_COUNT = "download_count";
}
