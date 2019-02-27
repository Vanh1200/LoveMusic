package com.vanh1200.lovemusic.screen.miniplayer;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerStateType;
import com.vanh1200.lovemusic.screen.play.PlayActivity;
import com.vanh1200.lovemusic.service.PlayMusicListener;
import com.vanh1200.lovemusic.service.PlayMusicService;
import com.vanh1200.lovemusic.utils.TrackEntity;

public class MiniPlayerFragment extends BaseFragment implements View.OnClickListener, PlayMusicListener {
    private static final float FROM_DEGREE = 0;
    private static final float TO_DEGREE = 360;
    private static final long INTERVAL_TIME = 1000;
    private ImageView mImageArtwork;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private ImageView mImagePrevious;
    private ImageView mImagePlay;
    private ImageView mImageNext;
    private ProgressBar mProgressBarMiniPlay;
    private ObjectAnimator mObjectAnimator;
    private PlayMusicService mService;
    private ServiceConnection mConnection;
    private Handler mHandler;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mini_player;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mImageArtwork = view.findViewById(R.id.image_artwork);
        mTextTitle = view.findViewById(R.id.text_title);
        mTextArtist = view.findViewById(R.id.text_artist);
        mImagePrevious = view.findViewById(R.id.image_previous);
        mImagePlay = view.findViewById(R.id.image_play);
        mImageNext = view.findViewById(R.id.image_next);
        mProgressBarMiniPlay = view.findViewById(R.id.progress_bar_mini_play);
        initRotateAnimator();
        registerEvents(view);
    }

    @Override
    public void onStart() {
        initConnectionToService();
        super.onStart();
    }

    @Override
    public void onStop() {
        getActivity().unbindService(mConnection);
        super.onStop();
    }

    private void initConnectionToService() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayMusicService.PlayBinder playBinder = (PlayMusicService.PlayBinder) service;
                mService = playBinder.getService();
                mService.addPlayMusicListener(MiniPlayerFragment.this);
                mProgressBarMiniPlay.setMax((int) mService.getCurrentTrack().getDuration());
                initHandlerUpdateTime();
                showTrackInformation(mService.getCurrentTrack());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        getActivity().bindService(PlayMusicService.getIntent(getActivity()), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    private void initHandlerUpdateTime() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mService.getMediaPlayerState() == MediaPlayerStateType.PLAY) {
                    mProgressBarMiniPlay.setProgress((int) mService.getCurrentDuration());
                }
                mHandler.postDelayed(this, INTERVAL_TIME);
            }
        }, INTERVAL_TIME);
    }

    private void showTrackInformation(Track track) {
        if (getActivity() == null) return;
        mTextTitle.setText(track.getTitle());
        mTextArtist.setText(track.getPublisher().getArtist());
        mProgressBarMiniPlay.setProgress((int) mService.getCurrentDuration());
        if (mService.getMediaPlayerState() == MediaPlayerStateType.PLAY) {
            mObjectAnimator.start();
            mImagePlay.setImageResource(R.drawable.ic_notify_pause);
        } else {
            mObjectAnimator.pause();
            mImagePlay.setImageResource(R.drawable.ic_notify_play);
        }
        if (track.getArtworkUrl().equals(TrackEntity.ARTWORK_URL)) {
            Glide.with(this)
                    .load(R.drawable.square_logo)
                    .apply(new RequestOptions().circleCrop())
                    .into(mImageArtwork);
        } else {
            Glide.with(this)
                    .load(track.getArtworkUrl())
                    .apply(new RequestOptions().circleCrop())
                    .into(mImageArtwork);
        }
    }

    private void initRotateAnimator() {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageArtwork, getString(R.string.animation_rotation),
                FROM_DEGREE, TO_DEGREE);
        mObjectAnimator.setDuration(15000);
        mObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
    }

    private void registerEvents(View view) {
        mImageNext.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        view.setOnClickListener(this);
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {

    }

    public static MiniPlayerFragment newInstance() {
        Bundle args = new Bundle();
        MiniPlayerFragment fragment = new MiniPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_previous:
                handlePrevious();
                break;
            case R.id.image_play:
                handlePlay();
                break;
            case R.id.image_next:
                handleNext();
                break;
            default:
                openPlayActivity();
                break;
        }
    }

    private void openPlayActivity() {
        startActivity(PlayActivity.getIntent(getActivity()));
    }

    private void handleNext() {
        mService.nextTrack();
    }

    private void handlePlay() {
        if (mService.getMediaPlayerState() == MediaPlayerStateType.PAUSE) {
            mService.startTrack();
            mImagePlay.setImageResource(R.drawable.ic_notify_pause);
            mObjectAnimator.resume();
        } else {
            mService.pauseTrack();
            mImagePlay.setImageResource(R.drawable.ic_notify_play);
            mObjectAnimator.pause();
        }
    }

    private void handlePrevious() {
        mService.previousTrack();
    }

    @Override
    public void onPlayingStateListener(int state) {
        if (!mObjectAnimator.isStarted()) mObjectAnimator.start();
        if (state == MediaPlayerStateType.PAUSE) {
            mImagePlay.setImageResource(R.drawable.ic_notify_play);
            mObjectAnimator.pause();
        } else {
            mImagePlay.setImageResource(R.drawable.ic_notify_pause);
            mObjectAnimator.resume();
        }
    }

    @Override
    public void onTrackChangedListener(Track track) {
        showTrackInformation(track);
    }
}
