package com.vanh1200.lovemusic.screen.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseActivity;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.screen.miniplayer.MiniPlayerFragment;
import com.vanh1200.lovemusic.service.PlayMusicListener;
import com.vanh1200.lovemusic.service.PlayMusicService;

public class MainActivity extends BaseActivity implements MainContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener, PlayMusicListener {
    private FrameLayout mFrameMain;
    private BottomNavigationView mBottomViewMain;
    private MainContract.Presenter mPresenter;
    private FrameLayout mFrameMiniPlay;
    private PlayMusicService mService;
    private ServiceConnection mConnection;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mFrameMain = findViewById(R.id.frame_main);
        mBottomViewMain = findViewById(R.id.bottom_view_main);
        mFrameMiniPlay = findViewById(R.id.frame_mini_play);
        mPresenter = new MainPresenter();
        mPresenter.setView(this);
        mBottomViewMain.setOnNavigationItemSelectedListener(this);
        mBottomViewMain.setSelectedItemId(R.id.item_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            setStatsTextBarColor();
        startService(PlayMusicService.getIntent(this));
        initServiceConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeServiceConnection();
    }

    private void removeServiceConnection() {
        unbindService(mConnection);
        mService.removePlayMusicListener(this);
    }

    private void initServiceConnection() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((PlayMusicService.PlayBinder) service).getService();
                mService.addPlayMusicListener(MainActivity.this);
                showMiniPlayer();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(PlayMusicService.getIntent(this), mConnection, BIND_AUTO_CREATE);
    }

    public void showMiniPlayer() {
        if (mService.getCurrentTrack() != null && mFrameMiniPlay.getVisibility() == View.GONE) {
            mFrameMiniPlay.setVisibility(View.VISIBLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_mini_play, MiniPlayerFragment.newInstance())
                    .commitAllowingStateLoss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setStatsTextBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_home:
                mPresenter.navigateHome();
                break;
            case R.id.item_library:
                mPresenter.navigateLibrary();
                break;
            case R.id.item_setting:
                mPresenter.navigateSetting();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onPlayingStateListener(int state) {

    }

    @Override
    public void onTrackChangedListener(Track track) {
        showMiniPlayer();
    }
}
