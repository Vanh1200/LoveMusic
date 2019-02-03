package com.vanh1200.lovemusic.screen.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.local.TrackLocalDataSource;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.screen.home.adapter.SliderAdapter;
import com.vanh1200.lovemusic.screen.home.adapter.SuggestedTracksAdapter;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeContract.View {
    public static HomeFragment sInstance;
    private ViewPager mViewPager;
    private SliderAdapter mSliderAdapter;
    private SuggestedTracksAdapter mSuggestedTracksAdapter;
    private RecyclerView mRecyclerSuggestedTracks;
    private Toolbar mToolbar;
    private HomeContract.Presenter mPresenter;
    private TrackRepository mTrackRepository;
    List<SliderFragment> mFragments;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mViewPager = view.findViewById(R.id.view_pager);
        mRecyclerSuggestedTracks = view.findViewById(R.id.recycle_suggested_tracks);
        initToolbar(view);
        mTrackRepository = TrackRepository.getInstance(TrackLocalDataSource.getInstance(getActivity()),
                TrackRemoteDataSource.getInstance());
        mPresenter = new HomePresenter(mTrackRepository);
        mPresenter.setView(this);
        initDataForSlider();
        initDataForSuggestedTracks();
    }

    private void initDataForSuggestedTracks() {
        mPresenter.initDataForSuggestedTracks();
    }

    private void initDataForSlider() {
        mPresenter.initDataForSlider(Constants.GENRES_ALL_MUSIC);
    }

    private void initToolbar(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {
    }

    public static HomeFragment getInstance() {
        if (sInstance == null) {
            sInstance = new HomeFragment();
        }
        return sInstance;
    }

    @Override
    public void onFetchDataForSliderSuccess(List<Track> tracks) {
        mSliderAdapter = new SliderAdapter(getFragmentManager());
        mFragments = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_BUNDLE_IMAGE_URL, tracks.get(i).getArtworkUrl());
            bundle.putString(Constants.KEY_BUNDLE_TITLE, tracks.get(i).getTitle());
            bundle.putString(Constants.KEY_BUNDLE_ARTIST, tracks.get(i).getPublisher().getArtist());
            SliderFragment sliderFragment = SliderFragment.newInstance(bundle);
            mFragments.add(sliderFragment);
        }
        mSliderAdapter.setFragmentSlider(mFragments);
        mViewPager.setAdapter(mSliderAdapter);
    }

    @Override
    public void onFetchDataForSliderFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchDataForSuggestedSuccess(List<Track> tracks) {
        mSuggestedTracksAdapter = new SuggestedTracksAdapter(tracks);
        mRecyclerSuggestedTracks.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        mRecyclerSuggestedTracks.setAdapter(mSuggestedTracksAdapter);
    }

    @Override
    public void onFetchDataForSuggestedFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
