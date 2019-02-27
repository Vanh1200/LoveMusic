package com.vanh1200.lovemusic.screen.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.Constants;

public class SliderFragment extends BaseFragment implements View.OnClickListener {
    private static final int ROUND_CORNER = 15;
    private ImageView mImageSlider;
    private ImageView mImageSmallArt;
    private TextView mTextTrack;
    private TextView mTextArtist;
    private Track mTrack;

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
        mTrack = (Track) getArguments().getParcelable(Constants.KEY_BUNDLE_TRACK);
        setImageSlider(mTrack.getArtworkUrl());
        setImageSmallArt(mTrack.getArtworkUrl());
        setTextTrack(mTrack.getTitle());
        setTextArtist(mTrack.getPublisher().getArtist());
        registerEvents(view);
    }

    private void registerEvents(View view) {
        view.setOnClickListener(this);
    }

    public void setImageSlider(String imageSlider) {
        Glide.with(getActivity())
                .load(imageSlider)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.bg_gray)
                        .error(R.drawable.bg_gray)
                        .centerCrop())
                .into(mImageSlider);
    }

    public void setImageSmallArt(String imageSmallArt) {
        Glide.with(getActivity())
                .load(imageSmallArt)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.bg_gray)
                        .error(R.drawable.bg_gray)
                        .bitmapTransform(new RoundedCorners(ROUND_CORNER)))
                .into(mImageSmallArt);
    }

    public void setTextTrack(String textTrack) {
        mTextTrack.setText(textTrack);
    }

    public void setTextArtist(String textArtist) {
        mTextArtist.setText(textArtist);
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {
    }

    public static SliderFragment newInstance(Bundle argument) {
        SliderFragment fragment = new SliderFragment();
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if (getParentFragment() != null){
            ((HomeFragment) getParentFragment()).onClickSlide(mTrack);
        }
    }

    public interface OnClickItem {
        void onClickSlide(Track track);
    }
}
