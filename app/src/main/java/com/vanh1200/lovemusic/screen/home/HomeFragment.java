package com.vanh1200.lovemusic.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Genre;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.local.TrackLocalDataSource;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.screen.genre.GenreDetailActivity;
import com.vanh1200.lovemusic.screen.home.adapter.SliderAdapter;
import com.vanh1200.lovemusic.screen.home.adapter.SuggestedTracksAdapter;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeContract.View,
        SuggestedTracksAdapter.OnClickSuggestedTracks, View.OnClickListener
        , SliderFragment.OnClickItem {
    public static HomeFragment sInstance;
    private ViewPager mViewPager;
    private SliderAdapter mSliderAdapter;
    private SuggestedTracksAdapter mSuggestedTracksAdapter;
    private RecyclerView mRecyclerSuggestedTracks;
    private Toolbar mToolbar;
    private TextView mTextSuggested;
    private TextView mTextPopularPlaylists;
    private ImageView mImageAllMusic;
    private ImageView mImageAllAudio;
    private ImageView mImageAmbient;
    private ImageView mImageRock;
    private ImageView mImageClassical;
    private ImageView mImageCountry;
    private TextView mTextSearchTracks;
    private HomePresenter mPresenter;
    private TrackRepository mTrackRepository;
    private List<SliderFragment> mFragments;
    private Genre mGenreAllMusic;
    private Genre mGenreAllAudio;
    private Genre mGenreRock;
    private Genre mGenreAmbient;
    private Genre mGenreClassical;
    private Genre mGenreCountry;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mViewPager = view.findViewById(R.id.view_pager);
        mRecyclerSuggestedTracks = view.findViewById(R.id.recycle_suggested_tracks);
        mTextSuggested = view.findViewById(R.id.text_suggested);
        mTextPopularPlaylists = view.findViewById(R.id.text_popular_playlist);
        mImageAllMusic = view.findViewById(R.id.image_all_music);
        mImageAllAudio = view.findViewById(R.id.image_all_audio);
        mImageRock = view.findViewById(R.id.image_rock);
        mImageAmbient = view.findViewById(R.id.image_ambient);
        mImageClassical = view.findViewById(R.id.image_classical);
        mImageCountry = view.findViewById(R.id.image_country);
        mTextSearchTracks = view.findViewById(R.id.text_search);
        initGenres();
        initToolbar(view);
        mTrackRepository = TrackRepository.getInstance(TrackLocalDataSource.getInstance(getActivity()),
                TrackRemoteDataSource.getInstance());
        mPresenter = new HomePresenter(mTrackRepository);
        mPresenter.setView(this);
        registerEvents();
        initDataForSlider();
        initDataForSuggestedTracks();
    }

    private void initGenres() {
        mGenreAllMusic = new Genre(Constants.GENRES_ALL_MUSIC,
                getString(R.string.text_all_music), R.drawable.all_music);
        mGenreAllAudio = new Genre(Constants.GENRES_ALL_AUDIO,
                getString(R.string.text_all_audio), R.drawable.all_audio);
        mGenreAmbient = new Genre(Constants.GENRES_AMBIENT,
                getString(R.string.text_ambient), R.drawable.ambient);
        mGenreRock = new Genre(Constants.GENRES_ROCK,
                getString(R.string.text_rock), R.drawable.rock);
        mGenreClassical = new Genre(Constants.GENRES_CLASSICAL,
                getString(R.string.text_classical), R.drawable.classical);
        mGenreCountry = new Genre(Constants.GENRES_COUNTRY,
                getString(R.string.text_country), R.drawable.country);
    }

    private void registerEvents() {
        mTextSuggested.setOnClickListener(this);
        mTextPopularPlaylists.setOnClickListener(this);
        mImageAllMusic.setOnClickListener(this);
        mImageAllAudio.setOnClickListener(this);
        mImageAmbient.setOnClickListener(this);
        mImageRock.setOnClickListener(this);
        mImageClassical.setOnClickListener(this);
        mImageCountry.setOnClickListener(this);
        mTextSearchTracks.setOnClickListener(this);
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
        mSliderAdapter = new SliderAdapter(getChildFragmentManager());
        mFragments = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.KEY_BUNDLE_TRACK, tracks.get(i));
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
        mSuggestedTracksAdapter.setListener(this);
        mRecyclerSuggestedTracks.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        mRecyclerSuggestedTracks.setAdapter(mSuggestedTracksAdapter);
    }

    @Override
    public void onFetchDataForSuggestedFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickTracks(Track track) {
        Toast.makeText(getActivity(), track.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_search:
                break;
            case R.id.text_suggested:
                break;
            case R.id.text_popular_playlist:
                break;
            case R.id.image_all_music:
                openGenreDetail(mGenreAllMusic);
                break;
            case R.id.image_all_audio:
                openGenreDetail(mGenreAllAudio);
                break;
            case R.id.image_rock:
                openGenreDetail(mGenreRock);
                break;
            case R.id.image_ambient:
                openGenreDetail(mGenreAmbient);
                break;
            case R.id.image_classical:
                openGenreDetail(mGenreClassical);
                break;
            case R.id.image_country:
                openGenreDetail(mGenreCountry);
                break;
            default:
                break;
        }
    }

    private void openGenreDetail(Genre genre) {
        Intent intent = GenreDetailActivity.getIntent(getActivity());
        intent.putExtra(Constants.KEY_INTENT_GENRE, genre);
        startActivity(intent);
    }

    @Override
    public void onClickSlide(Track track) {
        Toast.makeText(getActivity(), track.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
