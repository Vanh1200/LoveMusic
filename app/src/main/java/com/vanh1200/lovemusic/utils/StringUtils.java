package com.vanh1200.lovemusic.utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StringUtils {
    public static String generateGenreUrl(String kind, String genre, int limit, int offset) {
        return String.format(
                Constants.BASE_GENRE_URL,
                kind,
                genre,
                Constants.CLIENT_ID,
                limit,
                offset);
    }

    public static String generateSearchUrl(String query, int limit, int offset) {
        return String.format(
                Constants.BASE_SEARCH_URL,
                query,
                Constants.CLIENT_ID,
                limit,
                offset);
    }

    public static String generateDownloadUrl(long trackId) {
        return String.format(
                Constants.BASE_DOWNLOAD_URL,
                trackId,
                Constants.CLIENT_ID);
    }

    public static String generateStreamUrl(long trackId) {
        return String.format(
                Constants.BASE_STREAM_URL,
                trackId,
                Constants.CLIENT_ID);
    }

    public static String merge(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static String reformatImageUrl(String url) {
        return url.replace(Constants.IMAGE_LARGE, Constants.IMAGE_FULL);
    }

    public static String formatTrackFileName(String name){
        return String.format(Locale.US,"%s.mp3", name);
    }

    public static String convertTimeInMilisToString(long duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
