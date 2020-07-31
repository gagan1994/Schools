package com.gagan.school.home.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.gagan.school.home.MainActivity;
import com.gagan.school.library.view.BaseFragment;

import java.util.List;

public class MainViewPager extends FragmentStatePagerAdapter {

    private final List<BaseFragment> fragments;
    private Context mContext;

    public MainViewPager(MainActivity context, List<BaseFragment> fragments) {
        super(context.getSupportFragmentManager());
        mContext = context;
        this.fragments=fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
