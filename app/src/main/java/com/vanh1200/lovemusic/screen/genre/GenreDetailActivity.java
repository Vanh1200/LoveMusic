package com.vanh1200.lovemusic.screen.genre;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.vanh1200.lovemusic.screen.miniplayer.MiniPlayerFragment;
import com.vanh1200.lovemusic.screen.option.OptionDialogFragment;
import com.vanh1200.lovemusic.screen.play.PlayActivity;
import com.vanh1200.lovemusic.screen.search.SearchActivity;
import com.vanh1200.lovemusic.service.PlayMusicService;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenreDetailActivity extends BaseActivity implements GenreDetailContract.View,
        GenreTrackAdapter.OnClickTrackListener, View.OnClickListener, OptionDialogFragment.OnOptionClickListener {
    private static final String NOTIFICATION_FETCH_TRACKS_FAILED = "get tracks failed";
    private RecyclerView mRecyclerTrack;
    private TextView mTextGenre;
    private ImageView mImageGenre;
    private ImageView mImageSmallArtWork;
    private Button mButtonShuffle;
    private ProgressBar mProgressBarGenreDetail;
    private TrackRepository mTrackRepository;
    private GenreDetailPresenter mPresenter;
    private List<Track> mTracks;
    private GenreTrackAdapter mAdapterTrack;
    private Toolbar mToolbar;
    private AppBarLayout mAppBarGenre;
    private LinearLayout mLinearGenre;
    private PlayMusicService mService;
    private ServiceConnection mConnection;
    private FrameLayout mFrameMiniPlay;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_genre_detail;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mTextGenre = findViewById(R.id.text_genre);
        mImageGenre = findViewById(R.id.image_genre);
        mImageSmallArtWork = findViewById(R.id.image_artwork);
        mButtonShuffle = findViewById(R.id.button_shuffle);
        mToolbar = findViewById(R.id.toolbar_genre_detail);
        mRecyclerTrack = findViewById(R.id.recycler_genre_detail);
        mAppBarGenre = findViewById(R.id.app_bar_genre);
        mLinearGenre = findViewById(R.id.linear_genre_info);
        mProgressBarGenreDetail = findViewById(R.id.progress_bar_genre_detail);
        mFrameMiniPlay = findViewById(R.id.frame_mini_play);
        mTrackRepository = TrackRepository
                .getInstance(TrackLocalDataSource.getInstance(this),
                        TrackRemoteDataSource.getInstance());
        mPresenter = new GenreDetailPresenter(mTrackRepository);
        mPresenter.setView(this);
        getIncomingIntent();
        initToolbar();
        initServiceConnection();
        registerEvents();
    }

    @Override
    protected void onDestroy() {
        removeServiceConnection();
        super.onDestroy();
    }

    private void initServiceConnection() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((PlayMusicService.PlayBinder) service).getService();
                showMiniPlayer();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(PlayMusicService.getIntent(this), mConnection, BIND_AUTO_CREATE);
    }

    private void removeServiceConnection() {
        unbindService(mConnection);
    }

    private void registerEvents() {
        mButtonShuffle.setOnClickListener(this);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        makeFadeAnim();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.genre_toolbar_menu, menu);
        return true;
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
        if (genre != null)
            handleGenre(genre);
        Genre genre1 = intent.getParcelableExtra(Constants.KEY_INTENT_GENRE_LOCAL);
        if (genre1 != null)
            handleGenreLocal(genre1);
        Genre genre2 = intent.getParcelableExtra(Constants.KEY_INTENT_GENRE_DOWNLOAD);
        if (genre2 != null)
            handleGenreDownload(genre2);
        Genre genre3 = intent.getParcelableExtra(Constants.KEY_INTENT_GENRE_FAVORITE);
        if (genre3 != null)
            handleGenreFavorite(genre3);

    }

    private void handleGenreFavorite(Genre genre) {
        mTextGenre.setText(genre.getName());
        mImageGenre.setImageResource(genre.getImageUrl());
        Glide.with(this)
                .load(genre.getImageUrl())
                .into(mImageSmallArtWork);
        mToolbar.setTitle(genre.getName());
        mPresenter.loadFavoriteTracks();
    }

    private void handleGenreDownload(Genre genre) {
        mTextGenre.setText(genre.getName());
        mImageGenre.setImageResource(genre.getImageUrl());
        Glide.with(this)
                .load(genre.getImageUrl())
                .into(mImageSmallArtWork);
        mToolbar.setTitle(genre.getName());
        mPresenter.loadDownloadedTracks();
    }

    private void handleGenreLocal(Genre genre) {
        mTextGenre.setText(genre.getName());
        mImageGenre.setImageResource(genre.getImageUrl());
        Glide.with(this)
                .load(genre.getImageUrl())
                .into(mImageSmallArtWork);
        mToolbar.setTitle(genre.getName());
        mPresenter.loadLocalTracks();
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
            case R.id.menu_search:
                handleClickSearch();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleClickSearch() {
        startActivity(SearchActivity.getIntent(this));
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
        mAdapterTrack.setListener(this);
        mRecyclerTrack.setAdapter(mAdapterTrack);
    }

    @Override
    public void onGetTracksFailed(String error) {
        Toast.makeText(this, NOTIFICATION_FETCH_TRACKS_FAILED, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {
        mTracks = new ArrayList<>();
        mTracks.addAll(tracks);
        mProgressBarGenreDetail.setVisibility(View.INVISIBLE);
        mRecyclerTrack.setVisibility(View.VISIBLE);
        initRecycler(tracks);
    }

    @Override
    public void showLocalTracks(List<Track> tracks) {
        mTracks = new ArrayList<>();
        mTracks.addAll(tracks);
        mProgressBarGenreDetail.setVisibility(View.INVISIBLE);
        mRecyclerTrack.setVisibility(View.VISIBLE);
        initRecycler(tracks);
    }

    @Override
    public void showFavoriteTracks(List<Track> tracks) {
        mTracks = new ArrayList<>();
        mTracks.addAll(tracks);
        mProgressBarGenreDetail.setVisibility(View.INVISIBLE);
        mRecyclerTrack.setVisibility(View.VISIBLE);
        initRecycler(tracks);
    }

    @Override
    public void showDownloadedTracks(List<Track> tracks) {
        mTracks = new ArrayList<>();
        mTracks.addAll(tracks);
        mProgressBarGenreDetail.setVisibility(View.INVISIBLE);
        mRecyclerTrack.setVisibility(View.VISIBLE);
        initRecycler(tracks);
    }

    @Override
    public void onClickTrack(Track track) {
        mService.addTrack(track);
        mService.addTracks(mTracks);
        mService.changeTrack(track);
        showMiniPlayer();
        startActivity(PlayActivity.getIntent(this));
    }

    @Override
    public void onClickOption(Track track) {
        showOptionDialog(track);
    }

    private void showOptionDialog(Track track) {
        OptionDialogFragment optionDialogFragment = OptionDialogFragment.newInstance(track);
        optionDialogFragment.show(getSupportFragmentManager(), optionDialogFragment.getTag());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_shuffle:
                handleShuffle();
                break;
            default:
                break;
        }
    }

    private void handleShuffle() {
        Track track = getRandomTrack();
        mService.addTrack(track);
        mService.addTracks(mTracks);
        mService.changeTrack(track);
        showMiniPlayer();
        startActivity(PlayActivity.getIntent(this));
    }

    private Track getRandomTrack() {
        Random rd = new Random();
        int position = rd.nextInt(mTracks.size() - 1);
        return mTracks.get(position);
    }

    public void showMiniPlayer() {
        if (mService.getCurrentTrack() != null && mFrameMiniPlay.getVisibility() == View.GONE) {
            mFrameMiniPlay.setVisibility(View.VISIBLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_mini_play, MiniPlayerFragment.newInstance())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onClickAddToFavorite(Track track) {
        Toast.makeText(mService, "Added to favorite!", Toast.LENGTH_SHORT).show();
        mPresenter.addToFavorite(track);
    }

    @Override
    public void onClickAddToQueue(Track track) {
        Toast.makeText(mService, "Added to queue!", Toast.LENGTH_SHORT).show();
        mService.addTrack(track);
    }
}
