package com.vanh1200.lovemusic.screen.option;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.Constants;
import com.vanh1200.lovemusic.utils.TrackEntity;

public class OptionDialogFragment extends BottomSheetDialogFragment implements OptionContract.View,
        View.OnClickListener {
    private static final int ROUNDING_RADIUS = 15;
    private ImageView mImageSmallArtwork;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private TextView mTextAddToFavorite;
    private TextView mTextAddToPlaylist;
    private TextView mTextAddToQueuePlayNext;
    private OnOptionClickListener mListener;
    private Track mCurrentTrack;

    public OptionDialogFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnOptionClickListener) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_option, container, false);
        initViews(view);
        displayInformation();
        registerEvents();
        return view;
    }

    private void registerEvents() {
        mTextAddToFavorite.setOnClickListener(this);
        mTextAddToPlaylist.setOnClickListener(this);
        mTextAddToQueuePlayNext.setOnClickListener(this);
    }

    private void displayInformation() {
        Track track = getArguments().getParcelable(Constants.KEY_BUNDLE_TRACK);
        mCurrentTrack = track;
        if (track != null) {
            mTextTitle.setText(track.getTitle());
            mTextArtist.setText(track.getPublisher().getArtist());
            if (track.getArtworkUrl() != null && track.getArtworkUrl() != TrackEntity.ARTWORK_URL) {
                Glide.with(this)
                        .load(track.getArtworkUrl())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.bg_gray)
                                .error(R.drawable.bg_gray)
                                .bitmapTransform(new RoundedCorners(ROUNDING_RADIUS)))
                        .into(mImageSmallArtwork);
            } else {
                Glide.with(this)
                        .load(R.drawable.square_logo)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.bg_gray)
                                .error(R.drawable.bg_gray)
                                .bitmapTransform(new RoundedCorners(ROUNDING_RADIUS)))
                        .into(mImageSmallArtwork);
            }
        }
    }

    private void initViews(View view) {
        mImageSmallArtwork = view.findViewById(R.id.image_small_artwork);
        mTextTitle = view.findViewById(R.id.text_title);
        mTextArtist = view.findViewById(R.id.text_artist);
        mTextAddToFavorite = view.findViewById(R.id.text_add_to_favorite);
        mTextAddToPlaylist = view.findViewById(R.id.text_add_to_playlist);
        mTextAddToQueuePlayNext = view.findViewById(R.id.text_add_to_queue_play_next);
    }

    public static OptionDialogFragment newInstance(Track track) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_BUNDLE_TRACK, track);
        OptionDialogFragment fragment = new OptionDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_add_to_favorite:
                handleAddToFavorite();
                break;
            case R.id.text_add_to_playlist:
                break;
            case R.id.text_add_to_queue_play_next:
                handleAddToQueue();
                break;
            default:
                break;
        }
    }

    private void handleAddToQueue() {
        if(mListener != null && mCurrentTrack != null){
            mListener.onClickAddToQueue(mCurrentTrack);
        }
    }

    private void handleAddToFavorite() {
        if(mListener != null && mCurrentTrack != null){
            mListener.onClickAddToFavorite(mCurrentTrack);
        }
    }

    public interface OnOptionClickListener{
        void onClickAddToFavorite(Track track);

        void onClickAddToQueue(Track track);
    }
}
