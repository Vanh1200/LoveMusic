package com.vanh1200.lovemusic.screen.genre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseActivity;
import com.vanh1200.lovemusic.data.model.Genre;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.local.TrackLocalDataSource;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.screen.genre.adapter.GenreTrackAdapter;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.List;

public class GenreDetailActivity extends BaseActivity implements GenreDetailContract.View {
    private static final String NOTIFICATION_FETCH_TRACKS_FAILED = "get tracks failed";
    private RecyclerView mRecyclerTrack;
    private TextView mTextGenre;
    private ImageView mImageGenre;
    private ImageView mImageSmallArtWork;
    private ProgressBar mProgressBarGenreDetail;
    private TrackRepository mTrackRepository;
    private GenreDetailPresenter mPresenter;
    private List<Track> mTracks;
    private GenreTrackAdapter mAdapterTrack;
    private Toolbar mToolbar;
    private AppBarLayout mAppBarGenre;
    private LinearLayout mLinearGenre;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_genre_detail;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mTextGenre = findViewById(R.id.text_genre);
        mImageGenre = findViewById(R.id.image_genre);
        mImageSmallArtWork = findViewById(R.id.image_artwork);
        mToolbar = findViewById(R.id.toolbar_genre_detail);
        mRecyclerTrack = findViewById(R.id.recycler_genre_detail);
        mAppBarGenre = findViewById(R.id.app_bar_genre);
        mLinearGenre = findViewById(R.id.linear_genre_info);
        mProgressBarGenreDetail = findViewById(R.id.progress_bar_genre_detail);
        mTrackRepository = TrackRepository
                .getInstance(TrackLocalDataSource.getInstance(this),
                        TrackRemoteDataSource.getInstance());
        mPresenter = new GenreDetailPresenter(mTrackRepository);
        mPresenter.setView(this);
        getIncomingIntent();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        makeFadeAnim();
    }

    private void makeFadeAnim() {
        mAppBarGenre.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mLinearGenre.setAlpha(1.0f - Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));
            }
        });
    }

    private void getIncomingIntent() {
        Intent intent = getIntent();
        Genre genre = intent.getParcelableExtra(Constants.KEY_INTENT_GENRE);
        handleGenre(genre);
    }

    private void handleGenre(Genre genre) {
        mTextGenre.setText(genre.getName());
        mImageGenre.setImageResource(genre.getImageUrl());
        Glide.with(this)
                .load(genre.getImageUrl())
                .into(mImageSmallArtWork);
        mToolbar.setTitle(genre.getName());
        fetchTracksForGenre(genre.getKey());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchTracksForGenre(String genre) {
        mPresenter.fetchTracksByGenre(genre);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, GenreDetailActivity.class);
    }

    @Override
    public void initRecycler(List<Track> tracks) {
        mAdapterTrack = new GenreTrackAdapter(tracks);
        mRecyclerTrack.setAdapter(mAdapterTrack);
    }

    @Override
    public void onGetTracksFailed(String error) {
        Toast.makeText(this, NOTIFICATION_FETCH_TRACKS_FAILED, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {
        mProgressBarGenreDetail.setVisibility(View.INVISIBLE);
        mRecyclerTrack.setVisibility(View.VISIBLE);
        initRecycler(tracks);
    }
}
