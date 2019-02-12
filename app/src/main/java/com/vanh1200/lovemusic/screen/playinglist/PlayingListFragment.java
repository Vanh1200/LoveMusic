package com.vanh1200.lovemusic.screen.playinglist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Track;

import java.util.List;

public class PlayingListFragment extends BaseFragment {
    private static PlayingListFragment sInstance;
    private List<Track> mTracks;
    private int mCurrentTrackPosition;
    private RecyclerView mRecyclerPlayingList;
    private ImageView mImageDown;
    private TextView mTextCount;

    public PlayingListFragment() {
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_playing_list;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mRecyclerPlayingList = view.findViewById(R.id.recycler_playing_list);
        mImageDown = view.findViewById(R.id.image_down);
        mTextCount = view.findViewById(R.id.text_count);
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {
    }

    public static PlayingListFragment getInstance() {
        if (sInstance == null) {
            sInstance = new PlayingListFragment();
        }
        return sInstance;
    }
}
