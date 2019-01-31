package com.vanh1200.lovemusic.utils;

import android.support.annotation.StringDef;

@StringDef({
        PublisherEntity.ID,
        PublisherEntity.ARTIST
})
public @interface PublisherEntity {
    String ID = "id";
    String ARTIST = "artist";
}
