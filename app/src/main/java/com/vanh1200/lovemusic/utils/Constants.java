package com.vanh1200.lovemusic.utils;

import com.vanh1200.lovemusic.BuildConfig;

public final class Constants {
    public static final String CLIENT_ID = BuildConfig.CLIENT_ID;

    public static final String BASE_GENRE_URL
            = "https://api-v2.soundcloud.com/charts?kind=%s&genre=%s&client_id=%s&limit=%d&offset=%d";
    public static final String BASE_SEARCH_URL
            = "http://api.soundcloud.com/tracks?q=%s&client_id=%s&limit=%d&offset=%d";
    public static final String BASE_DOWNLOAD_URL
            = "https://api.soundcloud.com/tracks/%d/download?client_id=%s";
    public static final String BASE_STREAM_URL
            = "https://api.soundcloud.com/tracks/%d/stream?client_id=%s";

    public static final String KIND_TOP = "top";
    public static final String KIND_TREND = "trending";
    public static final String GENRES_ALL_MUSIC = "soundcloud:genres:all-music";
    public static final String GENRES_ALL_AUDIO = "soundcloud:genres:all-audio";
    public static final String GENRES_ROCK = "soundcloud:genres:alternativerock";
    public static final String GENRES_AMBIENT = "soundcloud:genres:ambient";
    public static final String GENRES_CLASSICAL = "soundcloud:genres:classical";
    public static final String GENRES_COUNTRY = "soundcloud:genres:country";
    public static final String GENRES_LOCAL = "Local";
    public static final String GENRES_FAVORITE = "Favorite";
    public static final String GENRES_DOWNLOAD = "Download";

    public static final int NO_IMAGE = 0;

    public static final int OFFSET = 0;
    public static final int LIMIT = 50;
    public static final int LIMIT_SLIDER = 5;

    public static final String KEY_BUNDLE_TRACK = "key_bundle_track";

    public static final String IMAGE_LARGE = "large";
    public static final String IMAGE_FULL = "t500x500";
    public static final String UNKNOWN = "unknown";

    public static final String KEY_INTENT_GENRE = "key_intent_genre";

    public static final String ACTION_PLAY_AND_PAUSE = "com.vanh1200.lovemusic.ACTION_PLAY_AND_PAUSE";
    public static final String ACTION_NEXT = "com.vanh1200.lovemusic.ACTION_NEXT";
    public static final String ACTION_PREVIOUS = "com.vanh1200.lovemusic.ACTION_PREVIOUS";
    public static final String ACTION_CLOSE = "com.vanh1200.lovemusic.ACTION_CLOSE";
    public static final int DEFAULT_NOTIFY_SIZE = 100;
    public static final String KEY_TIMER = "key_timer";
    public static final String KEY_INTENT_GENRE_LOCAL = "key_intent_genre_local";
    public static final String KEY_INTENT_GENRE_DOWNLOAD = "key_intent_genre_download";
    public static final String KEY_INTENT_GENRE_FAVORITE = "key_intent_genre_favorite";
}
