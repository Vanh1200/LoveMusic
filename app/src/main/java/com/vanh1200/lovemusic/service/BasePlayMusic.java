package com.vanh1200.lovemusic.service;

import com.vanh1200.lovemusic.data.model.Track;

import java.util.List;

public interface BasePlayMusic {

    void startTrack();

    void changeTrack(Track track);

    void pauseTrack();

    void previousTrack();

    void nextTrack();

    void stopTrack();

    void seek(int milis);

    long getDuration();

    long getCurrentDuration();

    void addTrack(Track track);

    void removeTrack(Track track);

    void addTracks(List<Track> tracks);

    void shuffleTracks();

    void unShuffleTracks();

    int getMediaPlayerState();

    void setTimer(int minute);

    int getTimer();

    void unSetTimer();
}
