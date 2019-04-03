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
    }

    interface Presenter extends BasePresenter<View> {
        void loadHistory();

        void loadResult(String query);
    }
}
