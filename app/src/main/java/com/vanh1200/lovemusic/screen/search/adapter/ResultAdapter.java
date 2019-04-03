package com.vanh1200.lovemusic.screen.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseRecyclerViewAdapter;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.TrackEntity;

import java.util.List;

public class ResultAdapter extends BaseRecyclerViewAdapter<Track, ResultAdapter.ViewHolder> {
    private OnResultListener mListener;

    public void setListener(OnResultListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_track,
                viewGroup, false);
        return new ViewHolder(view, mData, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private List<Track> mTracks;
        private OnResultListener mListener;
        private ImageView mImageArtwork;
        private TextView mTextTitle;
        private TextView mTextArtist;
        private ImageView mImageOption;

        public ViewHolder(@NonNull View itemView, List<Track> tracks, OnResultListener listener) {
            super(itemView);
            mTracks = tracks;
            mListener = listener;
            initViews(itemView);
            registerEvents(itemView);
        }

        private void registerEvents(View itemView) {
            itemView.setOnClickListener(this);
            mImageOption.setOnClickListener(this);
        }

        private void initViews(View itemView) {
            mImageArtwork = itemView.findViewById(R.id.image_artwork);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mImageOption = itemView.findViewById(R.id.image_option);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) return;
            switch (v.getId()) {
                case R.id.image_option:
                    mListener.onOptionClicked(mTracks.get(getAdapterPosition()));
                    break;
                default:
                    mListener.onResultClicked(mTracks.get(getAdapterPosition()));
            }
        }

        private void bindData(Track track) {
            mTextArtist.setText(track.getPublisher().getArtist());
            mTextTitle.setText(track.getTitle());
            if (track.getArtworkUrl().equals(TrackEntity.ARTWORK_URL) || track.getArtworkUrl().isEmpty()) {
                Glide.with(mImageArtwork)
                        .load(R.drawable.square_logo)
                        .into(mImageArtwork);

            } else {
                Glide.with(mImageArtwork)
                        .load(track.getArtworkUrl())
                        .into(mImageArtwork);
            }
        }
    }

    public interface OnResultListener {
        void onResultClicked(Track track);

        void onOptionClicked(Track track);
    }
}
