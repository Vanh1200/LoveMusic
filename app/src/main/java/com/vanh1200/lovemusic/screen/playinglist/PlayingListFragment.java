package com.vanh1200.lovemusic.screen.playinglist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.screen.play.PlayActivity;
import com.vanh1200.lovemusic.screen.playinglist.adapter.PlayingTrackAdapter;
import com.vanh1200.lovemusic.service.PlayMusicListener;
import com.vanh1200.lovemusic.service.PlayMusicService;
import com.vanh1200.lovemusic.utils.StringUtils;

import java.util.List;

public class PlayingListFragment extends BaseFragment
        implements PlayingListContract.View,
        PlayingTrackAdapter.OnClickTrackListener,
        View.OnClickListener, PlayMusicListener {
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static PlayingListFragment sInstance;
    private PlayMusicService mService;
    private List<Track> mTracks;
    private Track mCurrentTrack;
    private RecyclerView mRecyclerPlayingList;
    private PlayingTrackAdapter mTrackAdapter;
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
        registerEvents();
        getPlayMusicService();
    }

    private void registerEvents() {
        mImageDown.setOnClickListener(this);
    }

    private void getPlayMusicService() {
        mService = ((PlayActivity) getActivity()).getService();
        mService.addPlayMusicListener(this);
        mTracks = mService.getTracks();
        mCurrentTrack = mService.getCurrentTrack();
        showPlayingList(mTracks);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mService.removePlayMusicListener(this);
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

    @Override
    public void showPlayingList(List<Track> tracks) {
        mTrackAdapter = new PlayingTrackAdapter(tracks, mCurrentTrack, this);
        mRecyclerPlayingList.setAdapter(mTrackAdapter);
        mTextCount.setText(StringUtils.merge(OPEN_BRACKET,
                String.valueOf(tracks.size()), CLOSE_BRACKET));
    }

    @Override
    public void onClickTrack(Track track) {
        mService.changeTrack(track);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_down:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPlayingStateListener(int state) {
    }

    @Override
    public void onTrackChangedListener(Track track) {
        mTrackAdapter.setCurrentTrack(track);
        mTrackAdapter.notifyDataSetChanged();
    }
}
