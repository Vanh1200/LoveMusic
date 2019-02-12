package com.vanh1200.lovemusic.screen.playmusic;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.Constants;
import com.vanh1200.lovemusic.utils.StringUtils;

public class PlayMusicFragment extends BaseFragment implements View.OnClickListener {
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
        if (getArguments() != null) {
            mCurrentTrack = getArguments().getParcelable(Constants.KEY_BUNDLE_TRACK);
            bindData(mCurrentTrack);
        }
        registerEvents();
    }

    private void registerEvents() {
        mImageBack.setOnClickListener(this);
        mImageAlarm.setOnClickListener(this);
        mImageFavorite.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
        mImageShare.setOnClickListener(this);
        mSeekBarPlay.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mImageShuffle.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImageLoop.setOnClickListener(this);
    }

    private void bindData(Track currentTrack) {
        mTextTitle.setText(currentTrack.getTitle());
        mTextArtist.setText(currentTrack.getPublisher().getArtist());
        Glide.with(getActivity())
                .load(currentTrack.getArtworkUrl())
                .apply(new RequestOptions().circleCrop())
                .into(mImageArtwork);
        mTextTotalDuration.setText(StringUtils
                .convertTimeInMilisToString(currentTrack.getDuration()));
        startAnimation();
    }

    private void startAnimation() {
        Animation rotateAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim);
        mImageArtwork.startAnimation(rotateAnim);
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {

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
                break;
            case R.id.image_back:
                getActivity().onBackPressed();
                break;
            case R.id.image_favorite:
                break;
            case R.id.image_download:
                break;
            case R.id.image_share:
                break;
            case R.id.image_shuffle:
                break;
            case R.id.image_previous:
                break;
            case R.id.image_play:
                break;
            case R.id.image_next:
                break;
            case R.id.image_loop:
                break;
            case R.id.seek_bar_play_music:
                break;
            default:
                break;
        }
    }
}
