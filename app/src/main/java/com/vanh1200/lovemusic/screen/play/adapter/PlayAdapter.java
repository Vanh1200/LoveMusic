package com.vanh1200.lovemusic.screen.play.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vanh1200.lovemusic.screen.playinglist.PlayingListFragment;
import com.vanh1200.lovemusic.screen.playmusic.PlayMusicFragment;

public class PlayAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 2;
    private static final int FRAGMENT_PLAYING_LIST = 0;
    private static final int FRAGMENT_PLAY_MUSIC = 1;

    public PlayAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case FRAGMENT_PLAYING_LIST:
                return PlayingListFragment.getInstance();
            case FRAGMENT_PLAY_MUSIC:
                return PlayMusicFragment.getInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
