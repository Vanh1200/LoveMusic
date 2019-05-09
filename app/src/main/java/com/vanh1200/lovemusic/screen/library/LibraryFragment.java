package com.vanh1200.lovemusic.screen.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.data.model.Genre;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.local.TrackLocalDataSource;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.screen.genre.GenreDetailActivity;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.List;

public class LibraryFragment extends BaseFragment implements LibraryContract.View, View.OnClickListener {
    private TrackRepository mTrackRepository;
    private LibraryContract.Presenter mLibraryPresenter;
    private TextView mTextNumberLocalTracks;
    private View mViewLocalTracks;
    private View mViewDownloadedTracks;
    private View mViewFavoriteTracks;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_library;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mTrackRepository = TrackRepository.getInstance(TrackLocalDataSource.getInstance(getActivity()),
                TrackRemoteDataSource.getInstance());
        mLibraryPresenter = new LibraryPresenter(mTrackRepository);
        mLibraryPresenter.setView(this);
        mTextNumberLocalTracks = view.findViewById(R.id.text_number_my_songs);
        mViewLocalTracks = view.findViewById(R.id.view_click_phone);
        mViewDownloadedTracks = view.findViewById(R.id.view_click_download);
        mViewFavoriteTracks = view.findViewById(R.id.view_click_favorite);
        registerEvents();
//        mLibraryPresenter.loadLocalTracks();
    }

    private void registerEvents() {
        mViewLocalTracks.setOnClickListener(this);
        mViewDownloadedTracks.setOnClickListener(this);
        mViewFavoriteTracks.setOnClickListener(this);
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLocalTracks(List<Track> tracks) {
        mTextNumberLocalTracks.setText(String.valueOf(tracks.size()));
    }

    @Override
    public void showFavoriteTracks(List<Track> tracks) {

    }

    @Override
    public void showDownloadedTracks(List<Track> tracks) {

    }

    @Override
    public void showPlayLists() {

    }

    public static LibraryFragment newInstance() {
        Bundle args = new Bundle();
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_click_playlist:
                break;
            case R.id.view_click_download:
                handleClickDownload();
                break;
            case R.id.view_click_phone:
                handleClickPhone();
                break;
            case R.id.view_click_favorite:
                handleClickFavorite();
                break;
            default:
                break;
        }
    }

    private void handleClickPhone() {
        Genre genre = new Genre(Constants.GENRES_LOCAL, Constants.GENRES_LOCAL, R.drawable.square_logo);
        Intent intent = GenreDetailActivity.getIntent(getActivity());
        intent.putExtra(Constants.KEY_INTENT_GENRE_LOCAL, genre);
        startActivity(intent);
    }

    private void handleClickDownload() {
        Genre genre = new Genre(Constants.GENRES_DOWNLOAD, Constants.GENRES_DOWNLOAD, R.drawable.square_logo);
        Intent intent = GenreDetailActivity.getIntent(getActivity());
        intent.putExtra(Constants.KEY_INTENT_GENRE_DOWNLOAD, genre);
        startActivity(intent);
    }

    private void handleClickFavorite() {
        Genre genre = new Genre(Constants.GENRES_FAVORITE, Constants.GENRES_FAVORITE, R.drawable.square_logo);
        Intent intent = GenreDetailActivity.getIntent(getActivity());
        intent.putExtra(Constants.KEY_INTENT_GENRE_FAVORITE, genre);
        startActivity(intent);
    }
}
