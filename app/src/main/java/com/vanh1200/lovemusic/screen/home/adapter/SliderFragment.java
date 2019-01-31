package com.vanh1200.lovemusic.screen.home.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;

public class SliderFragment extends BaseFragment {
    private ImageView mImageSlider;
    private ImageView mImageSmallArt;
    private TextView mTextTrack;
    private TextView mTextArtist;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_slider;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mImageSlider = view.findViewById(R.id.image_slider);
        mImageSmallArt = view.findViewById(R.id.image_small_artwork);
        mTextArtist = view.findViewById(R.id.text_artist);
        mTextTrack = view.findViewById(R.id.text_track);
    }

    public void setImageSlider(String imageSlider) {
    }

    public void setImageSmallArt(String imageSmallArt) {
    }

    public void setTextTrack(String textTrack) {
    }

    public void setTextArtist(String textArtist) {
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {
    }

    public static SliderFragment newInstance() {
        return new SliderFragment();
    }
}
