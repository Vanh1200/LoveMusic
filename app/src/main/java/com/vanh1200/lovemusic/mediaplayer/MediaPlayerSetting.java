package com.vanh1200.lovemusic.mediaplayer;

public class MediaPlayerSetting {
    private int mLoop;
    private int mShuffle;

    public int getLoop() {
        return mLoop;
    }

    public void setLoop(@MediaPlayerLoopType int loop) {
        mLoop = loop;
    }

    public int getShuffle() {
        return mShuffle;
    }

    public void setShuffle(@MediaPlayerShuffleType int shuffle) {
        mShuffle = shuffle;
    }
}
