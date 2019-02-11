package com.vanh1200.lovemusic.screen.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vanh1200.lovemusic.screen.home.SliderFragment;

import java.util.List;

public class SliderAdapter extends FragmentPagerAdapter {
    public static final int COUNT = 5;
    private static final int FIRST_FRAGMENT = 0;
    private static final int SECOND_FRAGMENT = 1;
    private static final int THIRD_FRAGMENT = 2;
    private static final int FOURTH_FRAGMENT = 3;
    private static final int FIFTH_FRAGMENT = 4;
    private List<SliderFragment> mFragments;

    public List<SliderFragment> getFragmentSlider() {
        return mFragments;
    }

    public void setFragmentSlider(List<SliderFragment> fragmentSlider) {
        mFragments = fragmentSlider;
    }

    public SliderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case FIRST_FRAGMENT:
                return mFragments.get(FIRST_FRAGMENT);
            case SECOND_FRAGMENT:
                return mFragments.get(SECOND_FRAGMENT);
            case THIRD_FRAGMENT:
                return mFragments.get(THIRD_FRAGMENT);
            case FOURTH_FRAGMENT:
                return mFragments.get(FOURTH_FRAGMENT);
            case FIFTH_FRAGMENT:
                return mFragments.get(FIFTH_FRAGMENT);
            default:
                return mFragments.get(FIRST_FRAGMENT);
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

}
