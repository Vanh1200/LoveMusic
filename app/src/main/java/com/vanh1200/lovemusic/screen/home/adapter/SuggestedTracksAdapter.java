package com.vanh1200.lovemusic.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseRecyclerViewAdapter;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.Constants;
import com.vanh1200.lovemusic.utils.TrackEntity;

import java.util.List;

public class SuggestedTracksAdapter extends BaseRecyclerViewAdapter<Track,
        SuggestedTracksAdapter.ViewHolder> {

    private Context mContext;
    private OnClickSuggestedTracks mListener;

    public SuggestedTracksAdapter(List<Track> tracks) {
        super();
        mData = tracks;
    }

    public void setListener(OnClickSuggestedTracks listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_suggested_track, viewGroup, false);
        return new ViewHolder(view, mData, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mData.get(i), mContext);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final int RADIUS_CORNER = 15;
        private ImageView mImageArtwork;
        private TextView mTextTitle;
        private TextView mTextArtist;
        private OnClickSuggestedTracks mListener;
        private List<Track> mTracks;

        public ViewHolder(@NonNull View itemView, List<Track> tracks, OnClickSuggestedTracks listener) {
            super(itemView);
            mListener = listener;
            mTracks = tracks;
            initViews(itemView);
        }

        private void initViews(View itemView) {
            mImageArtwork = itemView.findViewById(R.id.image_suggested_image);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextArtist = itemView.findViewById(R.id.text_artist);
        }

        private void bindData(Track track, Context context) {
            if (track.getArtworkUrl() != null
                    && !track.getArtworkUrl().equals(TrackEntity.ARTWORK_URL)) {
                Glide.with(context)
                        .load(track.getArtworkUrl())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.bg_gray)
                                .error(R.drawable.bg_gray)
                                .centerCrop()
                                .bitmapTransform(new RoundedCorners(RADIUS_CORNER)))
                        .into(mImageArtwork);
            } else {
                Glide.with(context)
                        .load(R.drawable.square_logo)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.bg_gray)
                                .error(R.drawable.bg_gray)
                                .centerCrop()
                                .bitmapTransform(new RoundedCorners(RADIUS_CORNER)))
                        .into(mImageArtwork);
            }
            if (track.getPublisher() != null) {
                mTextArtist.setText(track.getPublisher().getArtist());
            } else {
                mTextArtist.setText(Constants.UNKNOWN);
            }
            mTextTitle.setText(track.getTitle());
            mImageArtwork.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_suggested_image:
                    if (mListener != null){
                        mListener.onClickTracks(mTracks.get(getAdapterPosition()));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public interface OnClickSuggestedTracks {
        void onClickTracks(Track track);
    }
}
