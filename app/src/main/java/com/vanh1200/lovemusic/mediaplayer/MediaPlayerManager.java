package com.vanh1200.lovemusic.mediaplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.service.PlayMusicService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediaPlayerManager extends MediaPlayerSetting
        implements BaseMediaPlayer {

    private static final int NUMBER_A_THOUSAND = 1000;
    private static final int NUMBER_A_MINUTE = 60;
    private static MediaPlayerManager sIntance;
    private List<Track> mTracks;
    private Track mCurrentTrack;
    private MediaPlayer mMediaPlayer;
    private PlayMusicService mService;
    @MediaPlayerStateType
    private int mState;
    private int mTimer;
    private Handler mHandlerTimer;
    private Runnable mRunnableTimer;

    public MediaPlayerManager(PlayMusicService musicService) {
        mTracks = new ArrayList<>();
        super.setLoop(MediaPlayerLoopType.NONE);
        super.setShuffle(MediaPlayerShuffleType.OFF);
        mState = MediaPlayerStateType.PAUSE;
        mService = musicService;
        mMediaPlayer = new MediaPlayer();
        mHandlerTimer = new Handler();
        mRunnableTimer = new Runnable() {
            @Override
            public void run() {
                mTimer = 0;
                mService.pauseTrack();
            }
        };
    }

    public static MediaPlayerManager getIntance(PlayMusicService musicService) {
        if (sIntance == null) {
            sIntance = new MediaPlayerManager(musicService);
        }
        return sIntance;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    public Track getCurrentTrack() {
        return mCurrentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        mCurrentTrack = currentTrack;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    @Override
    public void create(Track track) {
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(mService, Uri.parse(track.getStreamUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnErrorListener(mService);
        mMediaPlayer.setOnCompletionListener(mService);
        mMediaPlayer.setOnPreparedListener(mService);
        mMediaPlayer.prepareAsync();
        setState(MediaPlayerStateType.PREPARE);
    }

    @Override
    public void start() {
        setState(MediaPlayerStateType.PLAY);
        mMediaPlayer.start();
    }

    @Override
    public void changeTrack(Track track) {
        mCurrentTrack = track;
        create(track); //firstly fall into preparing state then playing state
    }

    @Override
    public void pause() {
        setState(MediaPlayerStateType.PAUSE);
        mMediaPlayer.pause();
    }

    @Override
    public void previous() {
        if (getShuffle() == MediaPlayerShuffleType.ON) {
            changeTrack(getPreviousTrack());
        } else {
            changeTrack(getPreviousTrack());
        }
    }

    private Track getPreviousTrack() {
        int position = mTracks.indexOf(mCurrentTrack);
        if (position == 0) {
            return mTracks.get(mTracks.size() - 1);
        }
        return mTracks.get(position - 1);
    }

    private Track getNextTrack() {
        int position = mTracks.indexOf(mCurrentTrack);
        if (position == mTracks.size() - 1) {
            return mTracks.get(0);
        }
        return mTracks.get(position + 1);
    }

    @Override
    public void next() {
        if (getShuffle() == MediaPlayerShuffleType.OFF) {
            changeTrack(getNextTrack());
        } else {
            changeTrack(getRandomTrack());
        }
    }

    private Track getRandomTrack() {
        Random random = new Random();
        return mTracks.get(random.nextInt(mTracks.size()));
    }

    @Override
    public void stop() {
        mMediaPlayer.stop();
        setState(MediaPlayerStateType.PAUSE);
    }

    @Override
    public void release() {
        mMediaPlayer.release();
    }

    @Override
    public void reset() {
        mMediaPlayer.reset();
    }

    @Override
    public void seek(int milis) {
        mMediaPlayer.seekTo(milis);
    }

    @Override
    public long getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public long getCurrentDuration() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void addTrack(Track track) {
        mTracks.add(track);
    }

    @Override
    public void addTracks(List<Track> tracks) {
        mTracks.clear();
        mTracks.addAll(tracks);
    }

    @Override
    public void removeTrack(Track track) {
        mTracks.remove(track);
    }

    @Override
    public void shuffleTracks() {
        super.setShuffle(MediaPlayerShuffleType.ON);
    }

    @Override
    public void unShuffleTracks() {
        super.setShuffle(MediaPlayerShuffleType.OFF);
    }

    @Override
    public void setTimer(int minute) {
        mTimer = minute;
        mHandlerTimer.postDelayed(mRunnableTimer, convertToMilis(minute));
    }

    private long convertToMilis(int minute) {
        return minute * NUMBER_A_MINUTE * NUMBER_A_THOUSAND;
    }

    @Override
    public int getTimer() {
        return mTimer;
    }

    @Override
    public void unSetTimer() {
        mTimer = 0;
        mHandlerTimer.removeCallbacks(mRunnableTimer);
    }

    @MediaPlayerStateType
    @Override
    public int getState() {
        return mState;
    }

    public void setState(@MediaPlayerStateType int state) {
        mState = state;
    }

    @Override
    public int getShuffle() {
        return super.getShuffle();
    }

    @Override
    public void setShuffle(int shuffle) {
        super.setShuffle(shuffle);
    }

    @Override
    public int getLoop() {
        return super.getLoop();
    }

    @Override
    public void setLoop(int loop) {
        super.setLoop(loop);
    }
}
