package com.vanh1200.lovemusic.utils;

import android.support.annotation.StringDef;

@StringDef({
        ApiEntity.GENRE,
        ApiEntity.KIND,
        ApiEntity.COLLECTION
})
public @interface ApiEntity{
    String GENRE = "genre";
    String KIND = "kind";
    String COLLECTION = "collection";
    String TRACK = "track";
}
