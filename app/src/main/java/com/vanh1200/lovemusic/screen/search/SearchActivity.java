package com.vanh1200.lovemusic.screen.search;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseActivity;
import com.vanh1200.lovemusic.base.BaseRecyclerViewAdapter;
import com.vanh1200.lovemusic.base.LoadMoreListener;
import com.vanh1200.lovemusic.data.model.History;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.data.repository.TrackRepository;
import com.vanh1200.lovemusic.data.source.local.TrackLocalDataSource;
import com.vanh1200.lovemusic.data.source.remote.TrackRemoteDataSource;
import com.vanh1200.lovemusic.screen.miniplayer.MiniPlayerFragment;
import com.vanh1200.lovemusic.screen.option.OptionDialogFragment;
import com.vanh1200.lovemusic.screen.play.PlayActivity;
import com.vanh1200.lovemusic.screen.search.adapter.HistoryAdapter;
import com.vanh1200.lovemusic.screen.search.adapter.ResultAdapter;
import com.vanh1200.lovemusic.service.PlayMusicService;
import com.vanh1200.lovemusic.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener,
        SearchContract.View,
        ResultAdapter.OnResultListener,
        BaseRecyclerViewAdapter.ItemListener<History>,
        TextView.OnEditorActionListener, TextWatcher, OptionDialogFragment.OnOptionClickListener {
    private static final long TIME_DELAY_SEARCH = 600;
    private ImageView mImageBack;
    private ImageView mImageMic;
    private EditText mTextSearch;
    private RecyclerView mRecyclerHistory;
    private RecyclerView mRecyclerResult;
    private List<History> mHistories;
    private HistoryAdapter mHistoryAdapter;
    private ResultAdapter mResultAdapter;
    private SearchContract.Presenter mPresenter;
    private ProgressBar mProgressLoading;
    private ProgressBar mProgressLoadMore;
    private FrameLayout mFrameMiniPlay;
    private Group mGroupResult;
    private PlayMusicService mService;
    private ServiceConnection mConnection;
    private Handler mHandlerSearch;
    private String mCurrentQuery;
    private int mCurrentOffset = Constants.OFFSET;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mImageBack = findViewById(R.id.image_back);
        mImageMic = findViewById(R.id.image_history);
        mTextSearch = findViewById(R.id.text_search);
        mRecyclerHistory = findViewById(R.id.recycler_history);
        mRecyclerResult = findViewById(R.id.recycler_result);
        mGroupResult = findViewById(R.id.group_search);
        mProgressLoadMore = findViewById(R.id.progress_load_more);
        mProgressLoading = findViewById(R.id.progress_loading);
        mFrameMiniPlay = findViewById(R.id.frame_mini_play);
        mPresenter = new SearchPresenter(TrackRepository.getInstance(
                TrackLocalDataSource.getInstance(this),
                TrackRemoteDataSource.getInstance()));
        mHandlerSearch = new Handler();
        mPresenter.setView(this);
        initServiceConnection();
        registerEvent();
        initRecyclerHistory();
        initRecyclerTrack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeServiceConnection();
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

    private void initRecyclerTrack() {
        mResultAdapter = new ResultAdapter();
        mResultAdapter.setListener(this);
        mRecyclerResult.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerResult.setAdapter(mResultAdapter);
        mRecyclerResult.addOnScrollListener(new LoadMoreListener() {
            @Override
            public void loadMore() {
                mCurrentOffset += Constants.LIMIT;
                mPresenter.loadMore(mCurrentQuery, Constants.LIMIT, mCurrentOffset);
                mProgressLoadMore.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initRecyclerHistory() {
        mHistoryAdapter = new HistoryAdapter();
        mHistoryAdapter.setItemListener(this);
        mRecyclerHistory.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerHistory.setAdapter(mHistoryAdapter);
    }

    private void registerEvent() {
        mImageBack.setOnClickListener(this);
        mImageMic.setOnClickListener(this);
        mTextSearch.setOnEditorActionListener(this);
        mTextSearch.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.image_history:
                break;
            default:
                break;
        }
    }

    @Override
    public void showHistorySuccess(List<History> histories) {

    }

    @Override
    public void showHistoryFailed(String error) {

    }

    @Override
    public void showResultSuccess(List<Track> tracks) {
        showResultFrame();
        mResultAdapter.setData(tracks);
        mProgressLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showResultFailed(String error) {
        mProgressLoading.setVisibility(View.INVISIBLE);
        Toast.makeText(this, getString(R.string.mess_search_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMore(List<Track> tracks) {
        mResultAdapter.addItems(tracks);
        mProgressLoadMore.setVisibility(View.INVISIBLE);
    }

    private void showResultFrame() {
        mGroupResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResultClicked(Track track) {
        mService.addTrack(track);
        List<Track> tracks = new ArrayList<>();
        tracks.add(track);
        mService.addTracks(tracks);
        mService.changeTrack(track);
        showMiniPlayer();
        startActivity(PlayActivity.getIntent(this));
    }

    @Override
    public void onOptionClicked(Track track) {
        showOptionDialog(track);
    }

    @Override
    public void onItemClicked(History history, int position) {

    }

    @Override
    public void onItemLongClicked(History history, int position) {

    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            /// TODO: 18/04/2019 add to search history
            hideKeyboard(mTextSearch);
            return true;
        }
        return false;
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    private void showOptionDialog(Track track) {
        OptionDialogFragment optionDialogFragment = OptionDialogFragment.newInstance(track);
        optionDialogFragment.show(getSupportFragmentManager(), optionDialogFragment.getTag());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCurrentQuery = s.toString();
        mHandlerSearch.removeCallbacksAndMessages(null);
        if (s.toString().isEmpty()) return;
        final CharSequence s1 = s;
        mHandlerSearch.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadResult(s1.toString(), Constants.LIMIT, Constants.OFFSET);
                mProgressLoading.setVisibility(View.VISIBLE);
            }
        }, TIME_DELAY_SEARCH);

    }

    @Override
    public void afterTextChanged(Editable s) {

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
