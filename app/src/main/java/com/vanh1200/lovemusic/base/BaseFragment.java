package com.vanh1200.lovemusic.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewsOnCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        initViewsOnCreateView(view, savedInstanceState);
        return view;
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void initViewsOnCreateView(View view, Bundle saveInstanceState);

    protected abstract void initViewsOnCreate(Bundle saveInstanceState);

}
