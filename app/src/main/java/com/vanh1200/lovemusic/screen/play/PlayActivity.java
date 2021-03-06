package com.vanh1200.lovemusic.screen.play;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rd.PageIndicatorView;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseActivity;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.screen.play.adapter.PlayAdapter;
import com.vanh1200.lovemusic.service.PlayMusicListener;
import com.vanh1200.lovemusic.service.PlayMusicService;
import com.vanh1200.lovemusic.utils.BlurTransformation;
import com.vanh1200.lovemusic.utils.TrackEntity;

public class PlayActivity extends BaseActivity implements PlayMusicListener {
    private static final int FRAGMENT_PLAY_MUSIC = 1;
    private ViewPager mViewPagerPlay;
    private ImageView mImageBackground;
    private ImageView mImageDarkCover;
    private PlayAdapter mPlayAdapter;
    private ServiceConnection mConnection;
    private PlayMusicService mService;
    private PageIndicatorView mPageIndicatorView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_play;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeServiceConnection();
        removePlayMusicListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void removePlayMusicListener() {
        mService.removePlayMusicListener(this);
    }

    private void removeServiceConnection() {
        unbindService(mConnection);
    }

    private void initServiceConnection() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayMusicService.PlayBinder playBinder = (PlayMusicService.PlayBinder) service;
                mService = playBinder.getService();
                changeBackground(mService.getCurrentTrack().getArtworkUrl());
                initViewPager();
                addPlayMusicListener();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(PlayMusicService
                .getIntent(this), mConnection, BIND_AUTO_CREATE);
    }

    private void addPlayMusicListener() {
        mService.addPlayMusicListener(this);
    }

    private void changeBackground(String artworkUrl) {
        if(artworkUrl == null){
            mImageDarkCover.setVisibility(View.INVISIBLE);
            Glide.with(this)
                    .load(R.drawable.bg_play_song_default)
                    .into(mImageBackground);
            return;
        }


        if (artworkUrl.equals(TrackEntity.ARTWORK_URL) || artworkUrl.isEmpty()) {
            mImageDarkCover.setVisibility(View.INVISIBLE);
            Glide.with(this)
                    .load(R.drawable.bg_play_song_default)
                    .into(mImageBackground);

        } else {
            mImageDarkCover.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(artworkUrl)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.bg_black_transparent)
                            .transform(new BlurTransformation(this)))
                    .into(mImageBackground);
        }
    }

    public PlayMusicService getService() {
        return mService;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mViewPagerPlay = findViewById(R.id.view_pager_play);
        mImageBackground = findViewById(R.id.image_back_ground);
        mImageDarkCover = findViewById(R.id.image_dark_cover);
        mPageIndicatorView = findViewById(R.id.page_indicator_view);
        setMarginStatusBar();
        initServiceConnection();
    }

    private void setMarginStatusBar() {
        //status bar height
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        // action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize}
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        setMargins(mPageIndicatorView, 0, statusBarHeight + actionBarHeight, 0, 0);
    }

    private void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    private void initViewPager() {
        mPlayAdapter = new PlayAdapter(getSupportFragmentManager());
        mViewPagerPlay.setAdapter(mPlayAdapter);
        mViewPagerPlay.setCurrentItem(FRAGMENT_PLAY_MUSIC);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, PlayActivity.class);
    }

    @Override
    public void onPlayingStateListener(int state) {

    }

    @Override
    public void onTrackChangedListener(Track track) {
        changeBackground(track.getArtworkUrl());
    }

}
