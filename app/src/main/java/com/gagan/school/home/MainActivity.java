package com.gagan.school.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.gagan.school.R;
import com.gagan.school.home.adapters.MainViewPager;
import com.gagan.school.home.gallery.GalleryFragment;
import com.gagan.school.home.fragmets.HomeFragment;
import com.gagan.school.home.fragmets.NotificationFragment;
import com.gagan.school.home.fragmets.ProfileFragment;
import com.gagan.school.home.fragmets.TrackingFragment;
import com.gagan.school.library.view.BaseActivity;
import com.gagan.school.library.view.BaseFragment;
import com.gagan.school.picassos.CircleTransform;
import com.gagan.school.picassos.PicassoTrustAll;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.main_container)
    ViewPager viewPager;
    private int currentPosition;
    private boolean isProgramatically;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabs();
    }

    private void initTabs() {
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_home)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_gallery)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_tracking)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_notification)));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (isProgramatically) {
                    isProgramatically = false;
                    return;
                }
                setSelectedItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        List<BaseFragment> fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new GalleryFragment());
        fragments.add(new TrackingFragment());
        fragments.add(new NotificationFragment());

        viewPager.setAdapter(new MainViewPager(this,fragments));
    }


    private void setSelectedItem(int position) {
        viewPager.setCurrentItem(position);
        if (position != 2) {
            currentPosition = position;
        }
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.id.main_layout;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}
