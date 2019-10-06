package com.gagan.school.library.interfaces;

import android.os.Bundle;

import com.gagan.school.library.BaseApplication;
import com.gagan.school.library.view.BaseActivity;
import com.gagan.school.library.view.BaseFragment;

import java.util.HashMap;

/**
 * Created by Gagan S Patil on 29/8/19.
 */
public interface IActivityHelper {
    int getLayout();
    int getColorFromResource(int color);
    void displayMessage(String text);
    BaseActivity getActivityContext();
    BaseApplication getBaseApplicationContext();
    void onTokenUpdated();

    void addFragmet(BaseFragment baseFragment, int layoutid, Bundle bundle, boolean isBackStack);
    void addFragmet(BaseFragment baseFragment, Bundle bundle);
    void addFragmet(BaseFragment baseFragment);

    public void addMultipleFragment(HashMap<String, BaseFragment> fragment, String key, int layout);
    public void displayFragment(BaseFragment fragment, String key);
}
