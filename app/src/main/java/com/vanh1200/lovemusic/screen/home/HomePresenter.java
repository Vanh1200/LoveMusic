package com.vanh1200.lovemusic.screen.home;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;

    public HomePresenter() {
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }
}
