package com.vanh1200.lovemusic.utils;

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

    public static String generateSearchUrl(String query) {
        return String.format(
                Constants.BASE_SEARCH_URL,
                query,
                Constants.CLIENT_ID);
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
}
