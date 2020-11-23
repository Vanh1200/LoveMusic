package com.vanh1200.lovemusic.data.source.remote;

import android.os.AsyncTask;

import com.vanh1200.lovemusic.data.model.Publisher;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.ApiEntity;
import com.vanh1200.lovemusic.utils.Constants;
import com.vanh1200.lovemusic.utils.PublisherEntity;
import com.vanh1200.lovemusic.utils.StringUtils;
import com.vanh1200.lovemusic.utils.TrackEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchTracksAsync extends AsyncTask<String, Void, List<Track>> {
    private static final String METHOD_GET = "GET";
    public static final String NOTIFY_FETCH_FAILED = "Get tracks failed!";
    private static final String SYMBOL_NEW_LINE = "\n";
    private TrackRemoteDataSource.OnGetTracksByGenre mCallbackGenre;
    private TrackRemoteDataSource.OnGetSuggestedTracks mCallbackSuggested;


    public FetchTracksAsync(TrackRemoteDataSource.OnGetTracksByGenre callbackGenre,
                            TrackRemoteDataSource.OnGetSuggestedTracks callbackSuggested) {
        mCallbackGenre = callbackGenre;
        mCallbackSuggested = callbackSuggested;
    }

    @Override
    protected List<Track> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(METHOD_GET);
            InputStream inputStream = connection.getInputStream();
            return getTracks(inputStream);
        } catch (IOException e) {
            return null;
        }
    }

    private List<Track> getTracks(InputStream inputStream) {
        List<Track> tracks = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append(SYMBOL_NEW_LINE);
            }
            JSONArray jsonArray = new JSONArray(builder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectTrack = jsonArray.getJSONObject(i);
                Track track = new Track();
                if (!jsonObjectTrack.isNull(TrackEntity.ARTWORK_URL)) {
                    track.setArtworkUrl(StringUtils.reformatImageUrl(jsonObjectTrack.
                            getString(TrackEntity.ARTWORK_URL)));
                } else {
                    track.setArtworkUrl(TrackEntity.ARTWORK_URL);
                }
                track.setTitle(jsonObjectTrack.getString(TrackEntity.TITLE));
                track.setId(jsonObjectTrack.getLong(TrackEntity.ID));
                track.setGenre(jsonObjectTrack.getString(TrackEntity.GENRE));
                track.setDownloadable(jsonObjectTrack.getBoolean(TrackEntity.DOWNLOADABLE));
                track.setStreamable(jsonObjectTrack.getBoolean(TrackEntity.STREAMABLE));
                track.setDuration(jsonObjectTrack.getLong(TrackEntity.DURATION));
                if (!jsonObjectTrack.isNull(TrackEntity.DESCRIPTION)) {
                    track.setDescription(jsonObjectTrack.getString(TrackEntity.DESCRIPTION));
                }
                if (!jsonObjectTrack.isNull(TrackEntity.PLAYBACK_COUNT)) {
                    track.setPlaybackCount(jsonObjectTrack.getLong(TrackEntity.PLAYBACK_COUNT));
                }
                if (!jsonObjectTrack.isNull(TrackEntity.LIKES_COUNT)) {
                    track.setLikesCount(jsonObjectTrack.getLong(TrackEntity.LIKES_COUNT));
                }
                if (!jsonObjectTrack.isNull(TrackEntity.DOWNLOAD_COUNT)) {
                    track.setDownloadCount(jsonObjectTrack.getLong(TrackEntity.DOWNLOAD_COUNT));
                }
                Publisher publisher = new Publisher();
                publisher.setArtist(Constants.UNKNOWN);
                if (!jsonObjectTrack.isNull(TrackEntity.PUBLISHER_METADATA)) {
                    JSONObject jsonObjectPublisher = jsonObjectTrack
                            .getJSONObject(TrackEntity.PUBLISHER_METADATA);
                    publisher.setId(jsonObjectPublisher.getLong(PublisherEntity.ID));
                    if (!jsonObjectPublisher.isNull(PublisherEntity.ARTIST)) {
                        publisher.setArtist(jsonObjectPublisher.getString(PublisherEntity.ARTIST));
                    }
                }
                track.setPublisher(publisher);
                tracks.add(track);
            }
        } catch (Exception e) {
            return null;
        }
        return tracks;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        if (mCallbackGenre != null) {
            if (tracks == null) {
                mCallbackGenre.onGetTracksFailed(NOTIFY_FETCH_FAILED);
                return;
            }
            mCallbackGenre.onGetTracksSuccess(tracks);
            return;
        }
        if (mCallbackSuggested != null) {
            if (tracks == null) {
                mCallbackSuggested.onGetSuggestedTracksFailed(NOTIFY_FETCH_FAILED);
                return;
            }
            mCallbackSuggested.onGetSuggestedTracksSuccess(tracks);
        }
    }
}
