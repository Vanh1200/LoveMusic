package com.vanh1200.lovemusic.screen.play;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseActivity;
import com.vanh1200.lovemusic.screen.play.adapter.PlayAdapter;

public class PlayActivity extends BaseActivity {
    private static final int FRAGMENT_PLAY_MUSIC = 1;
    private ViewPager mViewPagerPlay;
    private PlayAdapter mPlayAdapter;
    private ConstraintLayout mLayoutPlay;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_play;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mViewPagerPlay = findViewById(R.id.view_pager_play);
        mLayoutPlay = findViewById(R.id.constraint_play);
        initViewPager();
    }

    private void initViewPager() {
        mPlayAdapter = new PlayAdapter(getSupportFragmentManager());
        mViewPagerPlay.setAdapter(mPlayAdapter);
        mViewPagerPlay.setCurrentItem(FRAGMENT_PLAY_MUSIC);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, PlayActivity.class);
    }
}
