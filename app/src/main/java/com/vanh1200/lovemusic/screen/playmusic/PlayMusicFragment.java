package com.vanh1200.lovemusic.screen.playmusic;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.download.TrackDownloadManager;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerLoopType;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerShuffleType;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerStateType;
import com.vanh1200.lovemusic.screen.play.PlayActivity;
import com.vanh1200.lovemusic.screen.timer.OnTimerListener;
import com.vanh1200.lovemusic.screen.timer.TimerDialogFragment;
import com.vanh1200.lovemusic.service.PlayMusicListener;
import com.vanh1200.lovemusic.service.PlayMusicService;
import com.vanh1200.lovemusic.utils.Constants;
import com.vanh1200.lovemusic.utils.StringUtils;
import com.vanh1200.lovemusic.utils.TrackEntity;

public class PlayMusicFragment extends BaseFragment implements
        View.OnClickListener,
        PlayMusicContract.View,
        PlayMusicListener,
        SeekBar.OnSeekBarChangeListener,
        OnTimerListener {

    private static final long TIME_DELAY = 1000;
    private static final String ANIMATION_TYPE = "rotation";
    private static final float FROM_DEGREE = 0;
    private static final float TO_DEGREE = 360;
    private static final int REQUEST_FRAGMENT_TIMER = 1;
    private static final String SHARE_TYPE = "text/plain";
    private static final long TIME_ROTATE = 15000;
    private static final int REQUEST_PERMISSION_CODE = 0;
    private static PlayMusicFragment sInstance;
    private Track mCurrentTrack;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private ImageView mImageAlarm;
    private ImageView mImageBack;
    private ImageView mImageFavorite;
    private ImageView mImageDownload;
    private ImageView mImageShare;
    private SeekBar mSeekBarPlay;
    private TextView mTextCurrentDuration;
    private TextView mTextTotalDuration;
    private ImageView mImageShuffle;
    private ImageView mImageLoop;
    private ImageView mImagePrevious;
    private ImageView mImageNext;
    private ImageView mImagePlay;
    private ImageView mImageArtwork;
    private PlayMusicService mService;
    private ObjectAnimator mObjectAnimator;
    private Handler mHandlerSyncTime;
    private ProgressBar mProgressLoading;
    private static String[] sPermission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public PlayMusicFragment() {
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_play_music;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mTextTitle = view.findViewById(R.id.text_title);
        mTextArtist = view.findViewById(R.id.text_artist);
        mTextCurrentDuration = view.findViewById(R.id.text_current_duration);
        mTextTotalDuration = view.findViewById(R.id.text_duration);
        mImageAlarm = view.findViewById(R.id.image_alarm);
        mImageBack = view.findViewById(R.id.image_back);
        mImageFavorite = view.findViewById(R.id.image_favorite);
        mImageDownload = view.findViewById(R.id.image_download);
        mImageShare = view.findViewById(R.id.image_share);
        mImageShuffle = view.findViewById(R.id.image_shuffle);
        mImagePrevious = view.findViewById(R.id.image_previous);
        mImagePlay = view.findViewById(R.id.image_play);
        mImageNext = view.findViewById(R.id.image_next);
        mImageLoop = view.findViewById(R.id.image_loop);
        mSeekBarPlay = view.findViewById(R.id.seek_bar_play_music);
        mImageArtwork = view.findViewById(R.id.image_artwork);
        mProgressLoading = view.findViewById(R.id.progress_loading);
        registerEvents();
        initRotateAnimator();
    }

    private void initHandlerSyncTime() {
        mHandlerSyncTime = new Handler();
        mHandlerSyncTime.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mService.getMediaPlayerState() == MediaPlayerStateType.PLAY) {
                    long currentTime = mService.getCurrentDuration();
                    mSeekBarPlay.setProgress((int) currentTime);
                    mTextCurrentDuration.setText(StringUtils.convertTimeInMilisToString(currentTime));
                }
                mHandlerSyncTime.postDelayed(this, TIME_DELAY);
            }
        }, TIME_DELAY);
    }

    private void initRotateAnimator() {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageArtwork,
                ANIMATION_TYPE, FROM_DEGREE, TO_DEGREE);
        mObjectAnimator.setDuration(TIME_ROTATE);
        mObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mService = ((PlayActivity) getActivity()).getService();
        mService.addPlayMusicListener(this);
        updateTrackInformation(mService.getCurrentTrack());
        updateLoopMusicSetting();
        updateShuffleMusicSetting();
        updateStateMediaPlayer();
        initHandlerSyncTime();
    }

    private void updateStateMediaPlayer() {
        if (mService.getMediaPlayerState() == MediaPlayerStateType.PREPARE) {
            mImagePlay.setVisibility(View.INVISIBLE);
            mProgressLoading.setVisibility(View.VISIBLE);
        } else if (mService.getMediaPlayerState() == MediaPlayerStateType.PAUSE) {
            mImagePlay.setVisibility(View.VISIBLE);
            mProgressLoading.setVisibility(View.INVISIBLE);
            mImagePlay.setImageResource(R.drawable.ic_play);
        } else {
            mImagePlay.setVisibility(View.VISIBLE);
            mProgressLoading.setVisibility(View.INVISIBLE);
            mImagePlay.setImageResource(R.drawable.ic_pause);
        }
    }

    private void updateLoopMusicSetting() {
        if (mService.getPlayerManager().getLoop() == MediaPlayerLoopType.NONE) {
            mImageLoop.setImageResource(R.drawable.ic_not_loop);
        } else if (mService.getPlayerManager().getLoop() == MediaPlayerLoopType.ONE) {
            mImageLoop.setImageResource(R.drawable.ic_loop_one);
        } else {
            mImageLoop.setImageResource(R.drawable.ic_loop_all);
        }
    }

    private void updateShuffleMusicSetting() {
        if (mService.getPlayerManager().getShuffle() == MediaPlayerShuffleType.OFF) {
            mImageShuffle.setImageResource(R.drawable.ic_not_shuffle);
        } else {
            mImageShuffle.setImageResource(R.drawable.ic_shuffle);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mService.removePlayMusicListener(this);
    }

    private void registerEvents() {
        mImageBack.setOnClickListener(this);
        mImageAlarm.setOnClickListener(this);
        mImageFavorite.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
        mImageShare.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mImageShuffle.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImageLoop.setOnClickListener(this);
        mSeekBarPlay.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static PlayMusicFragment getInstance() {
        if (sInstance == null) {
            sInstance = new PlayMusicFragment();
        }
        return sInstance;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_alarm:
                handleTimer();
                break;
            case R.id.image_back:
                getActivity().onBackPressed();
                break;
            case R.id.image_favorite:
                break;
            case R.id.image_download:
                handleDownload();
                break;
            case R.id.image_share:
                handleShare();
                break;
            case R.id.image_shuffle:
                handleShuffle();
                break;
            case R.id.image_previous:
                handlePrevious();
                break;
            case R.id.image_play:
                handlePlayAndPause();
                break;
            case R.id.image_next:
                handleNext();
                break;
            case R.id.image_loop:
                handleLoop();
                break;
            default:
                break;
        }
    }

    private void handleDownload() {
        if (checkPermission()) {
            TrackDownloadManager.getInstance(getActivity()).downloadTrack(mService.getCurrentTrack());
            Toast.makeText(mService, "Downloading...", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            for (String permission : sPermission) {
                if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(sPermission, REQUEST_PERMISSION_CODE);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            TrackDownloadManager.getInstance(mService).downloadTrack(mService.getCurrentTrack());
            Toast.makeText(mService, "Downloading...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mService, "Can not download without your permission", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void handleShare() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(SHARE_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mService.getCurrentTrack().getStreamUrl());
        startActivity(Intent.createChooser(intent, mService.getCurrentTrack().getTitle()));
    }

    private void handleLoop() {
        switch (mService.getPlayerManager().getLoop()) {
            case MediaPlayerLoopType.NONE:
                mService.getPlayerManager().setLoop(MediaPlayerLoopType.ALL);
                break;
            case MediaPlayerLoopType.ALL:
                mService.getPlayerManager().setLoop(MediaPlayerLoopType.ONE);
                break;
            case MediaPlayerLoopType.ONE:
                mService.getPlayerManager().setLoop(MediaPlayerLoopType.NONE);
                break;
            default:
                break;
        }
        updateLoopMusicSetting();
    }

    private void handleTimer() {
        TimerDialogFragment timerDialogFragment = TimerDialogFragment.newInstance(mService.getTimer());
        timerDialogFragment.setTargetFragment(this, REQUEST_FRAGMENT_TIMER);
        timerDialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }

    private void handleShuffle() {
        switch (mService.getPlayerManager().getShuffle()) {
            case MediaPlayerShuffleType.OFF:
                mService.getPlayerManager().setShuffle(MediaPlayerShuffleType.ON);
                break;
            case MediaPlayerShuffleType.ON:
                mService.getPlayerManager().setShuffle(MediaPlayerShuffleType.OFF);
                break;
            default:
                break;
        }
        updateShuffleMusicSetting();
    }

    private void handlePrevious() {
        mService.previousTrack();
    }

    private void handleNext() {
        mService.nextTrack();
    }

    private void handlePlayAndPause() {
        if (mService.getMediaPlayerState() == MediaPlayerStateType.PAUSE) {
            mService.startTrack();
            mImagePlay.setImageResource(R.drawable.ic_pause);
            mObjectAnimator.resume();
        } else if (mService.getMediaPlayerState() == MediaPlayerStateType.PLAY) {
            mService.pauseTrack();
            mImagePlay.setImageResource(R.drawable.ic_play);
            mObjectAnimator.pause();
        } else {
            mObjectAnimator.pause();
        }
    }

    @Override
    public void updateTrackInformation(Track track) {
        mTextTitle.setText(track.getTitle());
        mTextArtist.setText(track.getPublisher().getArtist());
        mTextCurrentDuration.setText(StringUtils.convertTimeInMilisToString(mService.getCurrentDuration()));
        mTextTotalDuration.setText(StringUtils.convertTimeInMilisToString(track.getDuration()));
        updateSeekBar();
        if (!mObjectAnimator.isStarted()) mObjectAnimator.start();
        if (mService.getMediaPlayerState() == MediaPlayerStateType.PLAY) {
            mObjectAnimator.resume();
            mImagePlay.setImageResource(R.drawable.ic_pause);
        } else {
            mObjectAnimator.pause();
            mImagePlay.setImageResource(R.drawable.ic_play);
        }
        if (!track.getArtworkUrl().equals(TrackEntity.ARTWORK_URL)) {
            Glide.with(getActivity())
                    .load(track.getArtworkUrl())
                    .apply(new RequestOptions().circleCrop())
                    .into(mImageArtwork);
        } else {
            Glide.with(getActivity())
                    .load(R.drawable.square_logo)
                    .apply(new RequestOptions().circleCrop())
                    .into(mImageArtwork);
        }
    }

    private void updateSeekBar() {
        mSeekBarPlay.setMax((int) mService.getCurrentTrack().getDuration());
        mSeekBarPlay.setProgress((int) mService.getCurrentDuration());
        mSeekBarPlay.setEnabled(false);
    }

    @Override
    public void onPlayingStateListener(int state) {
        if (!mObjectAnimator.isStarted()) mObjectAnimator.start();
        if (state == MediaPlayerStateType.PREPARE) {
            mImagePlay.setVisibility(View.INVISIBLE);
            mProgressLoading.setVisibility(View.VISIBLE);
            mSeekBarPlay.setEnabled(false);

        } else if (state == MediaPlayerStateType.PAUSE) {
            mProgressLoading.setVisibility(View.INVISIBLE);
            mImagePlay.setVisibility(View.VISIBLE);
            mImagePlay.setImageResource(R.drawable.ic_play);
            mObjectAnimator.pause();
            mSeekBarPlay.setEnabled(true);
        } else {
            mProgressLoading.setVisibility(View.INVISIBLE);
            mImagePlay.setVisibility(View.VISIBLE);
            mImagePlay.setImageResource(R.drawable.ic_pause);
            mObjectAnimator.resume();
            mSeekBarPlay.setEnabled(true);
        }
    }

    @Override
    public void onTrackChangedListener(Track track) {
        updateTrackInformation(track);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mService.seek(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mTextCurrentDuration.setText(StringUtils.convertTimeInMilisToString(seekBar.getProgress()));
    }

    @Override
    public void onSetTimer(int minute) {
        mService.setTimer(minute);
    }

    @Override
    public void onUnSetTimer() {
        mService.unSetTimer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FRAGMENT_TIMER) {
            int timer = data.getIntExtra(Constants.KEY_TIMER, 0);
            if (timer == 0) {
                onUnSetTimer();
            } else {
                onSetTimer(timer);
            }
        }
    }
}
