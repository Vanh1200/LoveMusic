package com.vanh1200.lovemusic.screen.playinglist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseRecyclerViewAdapter;
import com.vanh1200.lovemusic.data.model.Track;

import java.util.Collections;
import java.util.List;

public class PlayingTrackAdapter extends BaseRecyclerViewAdapter<Track, PlayingTrackAdapter.ViewHolder>
        implements ItemTouchListener {
    private List<Track> mTracks;
    private Track mCurrentTrack;
    private OnTrackListener mListener;

    public PlayingTrackAdapter(List<Track> tracks, Track track, OnTrackListener listener) {
        mCurrentTrack = track;
        mTracks = tracks;
        mListener = listener;
    }

    public void setCurrentTrack(Track currentTrack) {
        mCurrentTrack = currentTrack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_playing_track, viewGroup, false);
        return new ViewHolder(viewGroup.getContext(), view, mTracks, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mTracks.get(i), mCurrentTrack);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    @Override
    public void onMove(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(mTracks, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(mTracks, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void swipe(int position, int direction) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
        private static final int OFFSET_LIST = 1;
        private static final String TAG = "ViewHolder";
        private TextView mTextTitle;
        private TextView mTextArtist;
        private ImageView mImageArrange;
        private ImageView mImagePlaying;
        private TextView mTextSequenceNumber;
        private List<Track> mTracks;
        private OnTrackListener mListener;
        private Context mContext;

        public ViewHolder(Context context, @NonNull View itemView, List<Track> tracks,
                          OnTrackListener listener) {
            super(itemView);
            mContext = context;
            mTracks = tracks;
            mListener = listener;
            initViews(itemView);
            registerEvents(itemView);
        }

        private void registerEvents(View itemView) {
            itemView.setOnClickListener(this);
            mImageArrange.setOnTouchListener(this);
        }

        private void initViews(View itemView) {
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mImageArrange = itemView.findViewById(R.id.image_arrange);
            mImagePlaying = itemView.findViewById(R.id.image_playing_track);
            mTextSequenceNumber = itemView.findViewById(R.id.text_sequence_number);
        }

        private void bindData(Track track, Track currentTrack) {
            mTextTitle.setText(track.getTitle());
            mTextArtist.setText(track.getPublisher().getArtist());
//            mTextSequenceNumber.setText(String.valueOf(mTracks.indexOf(track) + OFFSET_LIST));
            if (track.getTitle().equals(currentTrack.getTitle())) {
//                mTextSequenceNumber.setVisibility(View.INVISIBLE);
                mImagePlaying.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(R.drawable.gif_playing)
                        .into(mImagePlaying);
                mTextArtist.setTextColor(mContext.getResources().getColor(R.color.color_violet));
                mTextTitle.setTextColor(mContext.getResources().getColor(R.color.color_violet));
            } else {
//                mTextSequenceNumber.setVisibility(View.VISIBLE);
                mImagePlaying.setVisibility(View.INVISIBLE);
                mTextArtist.setTextColor(mContext.getResources().getColor(R.color.color_white));
                mTextTitle.setTextColor(mContext.getResources().getColor(R.color.color_light_gray));
            }
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                mListener.onClickTrack(mTracks.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getActionMasked() == MotionEvent.ACTION_DOWN && mListener != null){
                mListener.onStartDrag(this);
            }
            return false;
        }
    }

    public interface OnTrackListener {
        void onClickTrack(Track track);

        void onStartDrag(RecyclerView.ViewHolder holder);
    }

}
