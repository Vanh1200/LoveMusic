package com.vanh1200.lovemusic.screen.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseFragment;
import com.vanh1200.lovemusic.screen.home.adapter.SliderAdapter;
import com.vanh1200.lovemusic.screen.home.adapter.SliderFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    public static HomeFragment sInstance;
    private ViewPager mViewPager;
    private SliderAdapter mSliderAdapter;
    private Toolbar mToolbar;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewsOnCreateView(View view, Bundle saveInstanceState) {
        mViewPager = view.findViewById(R.id.view_pager);
        initToolbar(view);
        initViewPager();
    }

    private void initToolbar(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }

    private void initViewPager() {
        mSliderAdapter = new SliderAdapter(getFragmentManager());
        SliderFragment firstSlide = SliderFragment.newInstance();
        SliderFragment secondSlide = SliderFragment.newInstance();
        SliderFragment thirdSlide = SliderFragment.newInstance();
        SliderFragment fourthSlide = SliderFragment.newInstance();
        SliderFragment fifthSlide = SliderFragment.newInstance();
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(firstSlide);
        mFragments.add(secondSlide);
        mFragments.add(thirdSlide);
        mFragments.add(fourthSlide);
        mFragments.add(fifthSlide);
        mSliderAdapter.setFragmentSlider(mFragments);
        mViewPager.setAdapter(mSliderAdapter);
    }

    @Override
    protected void initViewsOnCreate(Bundle saveInstanceState) {
    }

    public static HomeFragment getInstance() {
        if (sInstance == null) {
            sInstance = new HomeFragment();
        }
        return sInstance;
    }
}
