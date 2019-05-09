package com.vanh1200.lovemusic.screen.home.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.TrackEntity;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter implements View.OnClickListener {
    private List<Track> mTracks = new ArrayList<>();
    private OnClickSlideListener mListener;
    private ImageView mImageBackGround;
    private ImageView mImageArtwork;
    private TextView mTextTitle;
    private TextView mTextArtist;

    public void setTracks(List<Track> tracks) {
        mTracks.clear();
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    public void setListener(OnClickSlideListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_slider, container, false);
        initViews(view);
        registerEvents();
        bindData(mTracks.get(position));
        container.addView(view);
        return view;
    }

    private void bindData(Track track) {
        if(!track.getArtworkUrl().equals(TrackEntity.ARTWORK_URL)){
            Glide.with(mImageArtwork)
                    .load(track.getArtworkUrl())
                    .into(mImageArtwork);
        } else {
            Glide.with(mImageArtwork)
                    .load(R.drawable.square_logo)
                    .into(mImageArtwork);
        }
        if(!track.getArtworkUrl().equals(TrackEntity.ARTWORK_URL)){
            Glide.with(mImageBackGround)
                    .load(track.getArtworkUrl())
                    .into(mImageBackGround);
        } else {
            Glide.with(mImageBackGround)
                    .load(R.drawable.square_logo)
                    .into(mImageBackGround);
        }
        mTextArtist.setText(track.getPublisher().getArtist());
        mTextTitle.setText(track.getTitle());

    }

    private void registerEvents() {
        mImageBackGround.setOnClickListener(this);
    }

    private void initViews(View view) {
        mImageBackGround = view.findViewById(R.id.image_slider);
        mImageArtwork = view.findViewById(R.id.image_small_artwork);
        mTextTitle = view.findViewById(R.id.text_track);
        mTextArtist = view.findViewById(R.id.text_artist);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public void onClick(View v) {
        if(mListener != null){
            mListener.onClickSlide();
        }
    }

    public interface OnClickSlideListener {
        void onClickSlide();
    }
}
