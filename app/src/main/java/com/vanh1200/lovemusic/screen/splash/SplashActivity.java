package com.vanh1200.lovemusic.screen.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vanh1200.lovemusic.screen.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private static final long TIME_DELAY_SPLASH = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goToMain();
    }

    private void goToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.getIntent(SplashActivity.this));
                finish();
            }
        }, TIME_DELAY_SPLASH);
    }
}
