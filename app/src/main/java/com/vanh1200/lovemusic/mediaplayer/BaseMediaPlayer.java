package com.vanh1200.lovemusic.mediaplayer;

import com.vanh1200.lovemusic.data.model.Track;

import java.util.List;

public interface BaseMediaPlayer {
    void create(Track track);

    void start();

    void changeTrack(Track track);

    void pause();

    void previous();

    void next();

    void stop();

    void release();

    void reset();

    void seek(int milis);

    long getDuration();

    long getCurrentDuration();

    void addTrack(Track track);

    void addTracks(List<Track> tracks);

    void removeTrack(Track track);

    void shuffleTracks();

    void unShuffleTracks();

    void setTimer(int minute);

    int getTimer();

    void unSetTimer();

    @MediaPlayerStateType
    int getState();
}
