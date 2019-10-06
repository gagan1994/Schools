package com.gagan.school.library;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.gagan.school.library.view.BaseFragment;
import com.gagan.school.library.view.BaseTextInputLayout;

/**
 * Created by Gagan S Patil on 29/8/19.
 */
public class BaseApplication extends Application {
    private boolean isUserLoggedIn = false;


    public void addFragment(BaseFragment baseFragment,
                            FragmentManager fragmentManager, int layoutid, Bundle bundle,
                            boolean backStack) {
        //If any data available in bundle then this loop will execute
        if (bundle != null && bundle.size() > 0) {
            baseFragment.setArguments(bundle);
        }

        if (backStack) {
            fragmentManager.beginTransaction().add(layoutid, baseFragment)
                    .addToBackStack(null).commitAllowingStateLoss();
        } else {
            fragmentManager.beginTransaction().add(layoutid, baseFragment)
                    .commitAllowingStateLoss();
        }
    }

    public void setErrorBottom(BaseTextInputLayout layout, int errrId) {
        layout.setErrorMessage(errrId == 0 ? null : getString(errrId));
    }
}