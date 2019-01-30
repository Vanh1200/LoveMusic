package com.vanh1200.lovemusic.screen.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout mFrameMain;
    private BottomNavigationView mBottomViewMain;
    private MainContract.Presenter mPresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        mFrameMain = findViewById(R.id.frame_main);
        mBottomViewMain = findViewById(R.id.bottom_view_main);
        mBottomViewMain = findViewById(R.id.bottom_view_main);
        mPresenter = new MainPresenter();
        mPresenter.setView(this);
        mBottomViewMain.setOnNavigationItemSelectedListener(this);
        mBottomViewMain.setSelectedItemId(R.id.item_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            setStatsTextBarColor();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setStatsTextBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_home:
                mPresenter.navigateHome();
                break;
            case R.id.item_library:
                mPresenter.navigateLibrary();
                break;
            case R.id.item_setting:
                mPresenter.navigateSetting();
                break;
            default:
                break;
        }
        return true;
    }
}
