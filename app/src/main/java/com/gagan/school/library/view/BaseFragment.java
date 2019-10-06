package com.gagan.school.library.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gagan.school.library.BaseApplication;
import com.gagan.school.library.interfaces.IActivityHelper;
import com.gagan.school.library.interfaces.IFragmentHelper;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Gagan S Patil on 29/8/19.
 */
public abstract class BaseFragment extends Fragment implements IFragmentHelper {


    private Unbinder unbinder;

    public void updateApi() {
        Log.w("APiCalls", "Upload api " + getClass().getSimpleName());

        /**
         * the below condition will be true when the view is visible for the
         * user for interaction
         * */
    }

    @Override
    public void onTokenUpdated() {
        /*
         * Api call on token updated
         * */
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        onCreatedView(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateApi();
    }

    protected abstract void onCreatedView(View view);

    protected String getStringResource(int text) {
        return text == 0 ? null : getResources().getString(text);
    }

    protected IActivityHelper getIActivityHelper() {
        return (IActivityHelper) getActivity();
    }

    @Override
    public int getColorFromResource(int color) {
        return getIActivityHelper().getColorFromResource(color);
    }

    @Override
    public void displayMessage(String text) {
        getIActivityHelper().displayMessage(text);
    }

    @Override
    public BaseActivity getActivityContext() {
        return (BaseActivity) getActivity();
    }

    @Override
    public BaseApplication getBaseApplicationContext() {
        return getIActivityHelper().getBaseApplicationContext();
    }

    @Override
    public void addFragmet(BaseFragment baseFragment, int layoutid, Bundle bundle, boolean isBackStack) {
        getIActivityHelper().addFragmet(baseFragment, layoutid, bundle, isBackStack);
    }

    @Override
    public void addFragmet(BaseFragment baseFragment, Bundle bundle) {
        getIActivityHelper().addFragmet(baseFragment, bundle);
    }

    @Override
    public void addFragmet(BaseFragment baseFragment) {
        getIActivityHelper().addFragmet(baseFragment);
    }

    @Override
    public void addMultipleFragment(HashMap<String, BaseFragment> fragment, String key, int layout) {
        getIActivityHelper().addMultipleFragment(fragment, key, layout);
    }

    protected void addMultipleFragment(HashMap<String, BaseFragment> fragment, int layout) {
        addMultipleFragment(fragment, getClass().getSimpleName(), layout);
    }

    @Override
    public void displayFragment(BaseFragment fragment, String key) {
        getIActivityHelper().displayFragment(fragment, key);
    }

    protected void displayFragment(BaseFragment fragment) {
        displayFragment(fragment, getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null)
            unbinder.unbind();
    }

    public boolean allowBackPressed() {
        return true;
    }
}
