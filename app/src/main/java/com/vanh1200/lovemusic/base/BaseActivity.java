package com.vanh1200.lovemusic.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        initViews(savedInstanceState);
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void initViews(Bundle saveInstanceState);

}
