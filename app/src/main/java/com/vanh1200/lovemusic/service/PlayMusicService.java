package com.vanh1200.lovemusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerLoopType;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerManager;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerShuffleType;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerStateType;
import com.vanh1200.lovemusic.notification.PlayMusicNotification;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PlayMusicService extends Service
        implements BasePlayMusic,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener {
    private IBinder mBinder;
    private MediaPlayerManager mPlayerManager;
    private PlayMusicNotification mNotification;
    private List<PlayMusicListener> mListeners;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new PlayBinder();
        mPlayerManager = MediaPlayerManager.getIntance(this);
        mNotification = new PlayMusicNotification(this);
        mListeners = new ArrayList<>();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, getString(R.string.notify_play_track_error),
                Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (mPlayerManager.getLoop()) {
            case MediaPlayerLoopType.ONE:
                changeTrack(getCurrentTrack());
                break;
            case MediaPlayerLoopType.ALL:
                nextTrack();
                break;
            case MediaPlayerLoopType.NONE:
                if (isLastTracks(getCurrentTrack())) {
                    stopTrack();
                } else {
                    nextTrack();
                }
                break;
            default:
                break;
        }
    }

    private boolean isLastTracks(Track currentTrack) {
            return mPlayerManager.getTracks().indexOf(currentTrack) == mPlayerManager
                    .getTracks().size() - 1;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mNotification.createDefaultNotification();
        mNotification.updateTrackInfoDefaultNotification(mPlayerManager.getCurrentTrack());
        startTrack();
    }

    public class PlayBinder extends Binder {
        public PlayMusicService getService() {
            return PlayMusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    private void handleIntent(Intent intent) {
        if (intent.getAction() == null) return;
        switch (intent.getAction()) {
            case Constants.ACTION_PLAY_AND_PAUSE:
                playAndPauseTrack();
                break;
            case Constants.ACTION_NEXT:
                nextTrack();
                break;
            case Constants.ACTION_PREVIOUS:
                previousTrack();
                break;
            case Constants.ACTION_CLOSE:
                break;
            default:
                break;
        }
    }

    private void playAndPauseTrack() {
        if (getMediaPlayerState() == MediaPlayerStateType.PAUSE) {
            startTrack();
        } else {
            pauseTrack();
        }
        notifyStateChange();
    }

    private void notifyStateChange() {
        for (PlayMusicListener listener : mListeners) {
            listener.onPlayingStateListener(getMediaPlayerState());
        }
    }

    private void notifyTrackChange() {
        notifyStateChange();
        for (PlayMusicListener listener : mListeners) {
            listener.onTrackChangedListener(getCurrentTrack());
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @Override
    public void startTrack() {
        mPlayerManager.start();
        notifyStateChange();
        mNotification.updateIconPlay(getMediaPlayerState());
    }

    @Override
    public void changeTrack(Track track) {
        mPlayerManager.changeTrack(track);
        notifyTrackChange();
    }

    @Override
    public void pauseTrack() {
        mPlayerManager.pause();
        mNotification.updateIconPlay(getMediaPlayerState());
        notifyStateChange();
    }

    @Override
    public void previousTrack() {
        mPlayerManager.previous();
        notifyTrackChange();
        mNotification.updateIconPlay(getMediaPlayerState());
    }

    @Override
    public void nextTrack() {
        mPlayerManager.next();
        notifyTrackChange();
        mNotification.updateIconPlay(getMediaPlayerState());
    }

    @Override
    public void stopTrack() {
        mPlayerManager.stop();
    }

    @Override
    public void seek(int milis) {
        mPlayerManager.seek(milis);
    }

    @Override
    public long getDuration() {
        return mPlayerManager.getDuration();
    }

    @Override
    public long getCurrentDuration() {
        return mPlayerManager.getCurrentDuration();
    }

    @Override
    public void addTrack(Track track) {
        mPlayerManager.addTrack(track);
    }

    @Override
    public void removeTrack(Track track) {
        mPlayerManager.removeTrack(track);
    }

    @Override
    public void addTracks(List<Track> tracks) {
        mPlayerManager.addTracks(tracks);
    }

    @Override
    public void shuffleTracks() {
        mPlayerManager.shuffleTracks();
    }

    @Override
    public void unShuffleTracks() {
        mPlayerManager.unShuffleTracks();
    }

    @Override
    public int getMediaPlayerState() {
        return mPlayerManager.getState();
    }

    @Override
    public void setTimer(int minute) {
        mPlayerManager.setTimer(minute);
    }

    @Override
    public int getTimer() {
        return mPlayerManager.getTimer();
    }

    @Override
    public void unSetTimer() {
        mPlayerManager.unSetTimer();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, PlayMusicService.class);
    }

    public MediaPlayerManager getPlayerManager() {
        return mPlayerManager;
    }

    public Track getCurrentTrack() {
        return mPlayerManager.getCurrentTrack();
    }

    public List<Track> getTracks() {
        return mPlayerManager.getTracks();
    }

    public void addPlayMusicListener(PlayMusicListener musicListener) {
        mListeners.add(musicListener);
    }

    public void removeAllPlayMusicListener() {
        mListeners.clear();
    }

    public void removePlayMusicListener(PlayMusicListener musicListener) {
        mListeners.remove(musicListener);
    }

}
