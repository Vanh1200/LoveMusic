package com.vanh1200.lovemusic.screen.home;


import com.vanh1200.lovemusic.base.BasePresenter;
import com.vanh1200.lovemusic.data.model.Track;

import java.util.List;

public interface HomeContract {
    interface View {
        void onFetchDataForSliderSuccess(List<Track> tracks);
        void onFetchDataForSliderFailed(String mess);
    }

    interface Presenter extends BasePresenter<View> {
        void initDataForSlider(String genre);
    }
}

