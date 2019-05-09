package com.vanh1200.lovemusic.screen.search;

import com.vanh1200.lovemusic.base.BasePresenter;
import com.vanh1200.lovemusic.data.model.History;
import com.vanh1200.lovemusic.data.model.Track;

import java.util.List;

public interface SearchContract {
    interface View {
        void showHistorySuccess(List<History> histories);

        void showHistoryFailed(String error);

        void showResultSuccess(List<Track> tracks);

        void showResultFailed(String error);

        void showMore(List<Track> tracks);
    }

    interface Presenter extends BasePresenter<View> {
        void loadHistory();

        void loadResult(String query, int limit, int offset);

        void loadMore(String query, int limit, int offset);

        void addToFavorite(Track track);

    }
}
