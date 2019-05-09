package com.vanh1200.lovemusic.data.source.local.sdcard;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.vanh1200.lovemusic.data.model.Publisher;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.MyApplication;
import com.vanh1200.lovemusic.utils.TrackEntity;

import java.util.ArrayList;
import java.util.List;

public class SdCardHelper {

    public List<Track> getTrackFromLocal() {
        ContentResolver resolver = MyApplication.getInstance().getContentResolver();

        String[] projections = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media._ID
        };

        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projections,
                null,
                null,
                null);

        List<Track> tracks = new ArrayList<>();

        while (cursor.moveToNext()) {
            Track track = new Track();
            track.setId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            track.setTitle(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            track.setDuration(
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            track.setArtworkUrl(
                    getAlbumArt(
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));
            track.setStreamUrl(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            Publisher publisher = new Publisher();
            publisher.setArtist(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            track.setPublisher(publisher);
            tracks.add(track);
        }

        cursor.close();
        return tracks;
    }

    private String getAlbumArt(int albumId) {
        String[] projections = {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART};
        String selection = MediaStore.Audio.Albums._ID + "=?";
        String[] selectionArgs = {String.valueOf(albumId)};
        Cursor cursorAlbum = MyApplication.getInstance().getContentResolver()
                .query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        projections, selection,
                        selectionArgs,
                        null);
        if (cursorAlbum == null) return null;
        String artwork = TrackEntity.ARTWORK_URL;
        if (cursorAlbum.moveToFirst()) {
            artwork = cursorAlbum.getString(
                    cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        }
        if(artwork == null) artwork = TrackEntity.ARTWORK_URL;
        cursorAlbum.close();
        return artwork;
    }

}
