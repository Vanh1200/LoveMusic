package com.vanh1200.lovemusic.data.source.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vanh1200.lovemusic.data.model.Publisher;
import com.vanh1200.lovemusic.data.model.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "love_music";
    public static final String TABLE_DROP = "DROP TABLE IF EXISTS";
    private static final String SELECTION_SUFFIX = " LIKE ?";
    private static final String SQL_CREATE_FAVORITE =
            String.format(Locale.US, "CREATE TABLE %s (%s INTEGER PRIMARY KEY," +
                            "%s INTEGER, %s TEXT, %s TEXT, %s TEXT,%s TEXT, %s INTEGER)",
                    FavoriteEntry.TABLE_FAVORITE,
                    FavoriteEntry.COLUMN_NAME_ID,
                    FavoriteEntry.COLUMN_NAME_DURATION,
                    FavoriteEntry.COLUMN_NAME_TITLE,
                    FavoriteEntry.COLUMN_NAME_ARTIST,
                    FavoriteEntry.COLUMN_NAME_STREAM_URL,
                    FavoriteEntry.COLUMN_NAME_ARTWORD_URL,
                    FavoriteEntry.COLUMN_NAME_DOWNLOADABLE);
//    private static final String SQL_CREATE_SEARCH =
//            String.format(Locale.US, "CREATE TABLE %s (%s TEXT PRIMARY KEY )",
//                    SearchHistoryEntry.TABLE_SEARCH_HISTORY,
//                    SearchHistoryEntry.SEARCH_KEY);
    private static final String SQL_DELETE_FAVORITE =
            TABLE_DROP + FavoriteEntry.TABLE_FAVORITE;
//    private static final String SQL_DELETE_SEARCH =
//            TABLE_DROP + FavoriteEntry.TABLE_FAVORITE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITE);
//        db.execSQL(SQL_CREATE_SEARCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FAVORITE);
//        db.execSQL(SQL_DELETE_SEARCH);
        onCreate(db);
    }

    public int addFavorite(Track track) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry.COLUMN_NAME_ID, track.getId());
        values.put(FavoriteEntry.COLUMN_NAME_DURATION, track.getDuration());
        values.put(FavoriteEntry.COLUMN_NAME_TITLE, track.getTitle());
        values.put(FavoriteEntry.COLUMN_NAME_ARTIST, track.getPublisher().getArtist());
        values.put(FavoriteEntry.COLUMN_NAME_STREAM_URL, track.getStreamUrl());
        values.put(FavoriteEntry.COLUMN_NAME_ARTWORD_URL, track.getArtworkUrl());
        values.put(FavoriteEntry.COLUMN_NAME_DOWNLOADABLE, track.isDownloadable()? 1 : 0);

        return (int) db.insert(FavoriteEntry.TABLE_FAVORITE, null, values);
    }

    public List<Track> getFavorites() {
        SQLiteDatabase db = getReadableDatabase();
        List<Track> tracks = new ArrayList<>();
        String[] projection = {
                FavoriteEntry.COLUMN_NAME_ID,
                FavoriteEntry.COLUMN_NAME_DURATION,
                FavoriteEntry.COLUMN_NAME_TITLE,
                FavoriteEntry.COLUMN_NAME_ARTIST,
                FavoriteEntry.COLUMN_NAME_STREAM_URL,
                FavoriteEntry.COLUMN_NAME_ARTWORD_URL,
                FavoriteEntry.COLUMN_NAME_DOWNLOADABLE
        };
        Cursor cursor = db.query(FavoriteEntry.TABLE_FAVORITE,
                projection,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null) cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.
                    getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_ID));
            int duration = cursor.getInt(cursor.
                    getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_DURATION));
            String title = cursor.getString(cursor.
                    getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_TITLE));
            String artist = cursor.getString(cursor.
                    getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_ARTIST));
            String streamUrl = cursor.getString(cursor.
                    getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_STREAM_URL));
            String artworkUrl = cursor.getString(cursor.
                    getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_ARTWORD_URL));
            boolean downloadable = cursor.getInt(cursor.
                    getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_DOWNLOADABLE)) == 1 ? true : false;

            Track track = new Track();
            track.setId(id);
            track.setDuration(duration);
            track.setTitle(title);
            Publisher publisher = new Publisher();
            publisher.setArtist(artist);
            track.setPublisher(publisher);
            track.setStreamUrl(streamUrl);
            track.setArtworkUrl(artworkUrl);
            track.setDownloadable(downloadable);
            tracks.add(track);
            cursor.moveToNext();
        }
        return tracks;
    }

    public boolean checkFavorite(Track track) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {FavoriteEntry.COLUMN_NAME_ID};
        String selection = FavoriteEntry.COLUMN_NAME_ID + SELECTION_SUFFIX;
        String[] selectionArgs = {String.valueOf(track.getId())};
        Cursor cursor = db.query(FavoriteEntry.TABLE_FAVORITE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (cursor != null && !cursor.isAfterLast()) return true;
        return false;
    }

    public int removeFavorite(Track track) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = FavoriteEntry.COLUMN_NAME_ID + SELECTION_SUFFIX;
        String[] selectionArgs = {String.valueOf(track.getId())};
        return db.delete(FavoriteEntry.TABLE_FAVORITE, selection, selectionArgs);
    }

//    public List<String> getSearchHistory() {
//        List<String> strings = new ArrayList<>();
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.query(SearchHistoryEntry.TABLE_SEARCH_HISTORY,
//                null, null, null, null, null, null);
//        if (cursor != null) cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            String tmp = cursor.getString(cursor.getColumnIndex(SearchHistoryEntry.SEARCH_KEY));
//            strings.add(tmp);
//            cursor.moveToNext();
//        }
//        return strings;
//    }
//
//    public int addSearchKey(String searchKey) {
//        if (checkSearchKey(searchKey)) return 0;
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(SearchHistoryEntry.SEARCH_KEY, searchKey);
//        return (int) db.insert(SearchHistoryEntry.TABLE_SEARCH_HISTORY, null, values);
//    }
//
//    private boolean checkSearchKey(String searchKey) {
//        SQLiteDatabase db = getReadableDatabase();
//        String[] projection = {SearchHistoryEntry.SEARCH_KEY};
//        String selection = SearchHistoryEntry.SEARCH_KEY + SELECTION_SUFFIX;
//        String[] selectionArgs = {searchKey};
//        Cursor cursor = db.query(SearchHistoryEntry.TABLE_SEARCH_HISTORY,
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null);
//        if (cursor != null && !cursor.isAfterLast()) return true;
//        return false;
//    }
}
