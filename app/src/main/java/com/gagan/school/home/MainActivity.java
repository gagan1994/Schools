package com.gagan.school.home;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.gagan.school.R;
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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.imageProfile)
    ImageView imageProfile;
    private HashMap<String, BaseFragment> hashFragments = new HashMap<>();
    final BaseFragment fragment1 = new HomeFragment();
    final BaseFragment fragment2 = new GalleryFragment();
    final BaseFragment fragment4 = new TrackingFragment();
    final BaseFragment fragment5 = new NotificationFragment();

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    private int currentPosition;
    private boolean isProgramatically;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PicassoTrustAll.getInstance(this)
                .load("http://www.sarkarinaukrisearch.in/wp-content/uploads/2017/02/boy-profile-images.jpg")
                .transform(new CircleTransform())
                .into(imageProfile);
        initTabs();
        hashFragments.put(fragment1.getClass().getSimpleName(), fragment1);
        hashFragments.put(fragment2.getClass().getSimpleName(), fragment2);
        hashFragments.put(fragment4.getClass().getSimpleName(), fragment4);
        hashFragments.put(fragment5.getClass().getSimpleName(), fragment5);
        addMultipleFragment(hashFragments, getClass().getSimpleName(), R.id.main_container);
        displayFragment(fragment1);

    }

    private void initTabs() {
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_home)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_gallery)));
        tabLayout.addTab(tabLayout.newTab());
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
    }


    @OnClick(R.id.imageProfile)
    public void onClickProfile() {
        ProfileFragment profile = new ProfileFragment();
        addFragmet(profile, R.anim.slide_in_up, 0);

    }

    private void setSelectedItem(int position) {
        switch (position) {
            case 0:
                displayFragment(fragment1);
                break;
            case 1:
                displayFragment(fragment2);
                break;
            case 2:
                ProfileFragment profile = new ProfileFragment();
                addFragmet(profile, R.anim.slide_in_up, 0);
                resetCurrent();
                break;
            case 3:
                displayFragment(fragment4);
                break;
            case 4:
                displayFragment(fragment5);
                break;
        }
        if (position != 2) {
            currentPosition = position;
        }
    }

    private void resetCurrent() {
        new Handler().postDelayed(() -> runOnUiThread(() -> {
            isProgramatically = true;
            tabLayout.getTabAt(currentPosition).select();
        }), 100);
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
