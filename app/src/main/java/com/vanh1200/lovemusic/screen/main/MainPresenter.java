package com.vanh1200.lovemusic.screen.main;

import android.support.v4.app.FragmentManager;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.screen.home.HomeFragment;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;

    public MainPresenter() {
    }


    @Override
    public void navigateHome() {
        FragmentManager manager = ((MainActivity) mView).getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.frame_main, HomeFragment.getInstance())
                .commit();
    }

    @Override
    public void navigateLibrary() {
    }

    @Override
    public void navigateSetting() {
    }

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }
}
