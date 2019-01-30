package com.vanh1200.lovemusic.screen.main;

import com.vanh1200.lovemusic.base.BasePresenter;

public interface MainContract {
    interface View {
        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter extends BasePresenter<View> {
        void navigateHome();

        void navigateLibrary();

        void navigateSetting();
    }
}
