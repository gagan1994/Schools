package com.gagan.school.library.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gagan.school.R;
import com.gagan.school.library.BaseApplication;
import com.gagan.school.library.interfaces.IActivityHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gagan S Patil on 29/8/19.
 */
public abstract class BaseActivity extends AppCompatActivity implements IActivityHelper {
    onCustomActivityResult onCustomActivityResult;
    HashMap<String, HashMap<String, BaseFragment>> hashMapHashMap = new HashMap<>();
    HashMap<String, BaseFragment> activeFragments = new HashMap<>();
    public static final List<IActivityHelper> list = new ArrayList<>();
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        onBeforeSetContent();
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        ProgressStatusBarAdapter.init(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    protected void onBeforeSetContent() {
        /*
         * override if need  to do anything before setting content
         * */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        ProgressStatusBarAdapter.remove(this);
    }

    @Override
    public int getColorFromResource(int color) {
        return getResources().getColor(color);
    }

    @Override
    public void displayMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenUpdated() {

    }

    @Override
    public BaseApplication getBaseApplicationContext() {
        return (BaseApplication) getApplication();
    }

    @Override
    public BaseActivity getActivityContext() {
        return this;
    }

    public void addFragmet(BaseFragment profile, int enterAnim, int exitAnim) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                enterAnim,0,0,
                exitAnim);
        transaction.addToBackStack(null);
        transaction.add(getFragmentLayoutId(), profile);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void addFragmet(BaseFragment baseFragment, int layoutid, Bundle bundle, boolean isBackStack) {
        getBaseApplicationContext().addFragment(baseFragment, getSupportFragmentManager(),
                layoutid, bundle, isBackStack);
    }

    @Override
    public void addFragmet(BaseFragment baseFragment, Bundle bundle) {
        if (getFragmentLayoutId() != 0)
            getBaseApplicationContext().addFragment(baseFragment, getSupportFragmentManager()
                    , getFragmentLayoutId(), bundle, true);
    }

    protected int getFragmentLayoutId() {
        return 0;
    }

    public void setErrorBottom(BaseTextInputLayout layout, int errrId) {
        getBaseApplicationContext().setErrorBottom(layout, errrId);

    }

    @Override
    public void addFragmet(BaseFragment baseFragment) {
        addFragmet(baseFragment, null);
    }

    @Override
    public void addMultipleFragment(HashMap<String, BaseFragment> fragment, String key, int layout) {
        hashMapHashMap.put(key, fragment);
        Set<String> keySet = fragment.keySet();
        if (activeFragments.containsKey(key)) {
            activeFragments.remove(key);
        }
        BaseFragment active = null;
        for (String keys : keySet) {
            getSupportFragmentManager().beginTransaction()
                    .add(layout, fragment.get(keys), keys)
                    .hide(fragment.get(keys))
                    .commit();
            active = fragment.get(keys);
        }
        activeFragments.put(key, active);
    }

    @Override
    public void displayFragment(BaseFragment fragment, String key) {
        BaseFragment activeFragment = activeFragments.get(key);
        if (activeFragment == fragment) return;
        getSupportFragmentManager().beginTransaction()
                .hide(activeFragment)
                .show(fragment)
                .commit();
        activeFragments.put(key, fragment);
    }

    protected void displayFragment(BaseFragment fragment) {
        displayFragment(fragment, getClass().getSimpleName());
    }

    public void removeFragment(int count) {
        for (int i = 0; i < count; i++) {
            getSupportFragmentManager().popBackStack();
        }
        refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (onCustomActivityResult != null &&
                requestCode == onCustomActivityResult.ReqCode) {
            if (resultCode == RESULT_OK) {
                onCustomActivityResult.onSuccess(data);
                onCustomActivityResult = null;
            } else {
                onCustomActivityResult.onError();
                onCustomActivityResult = null;
            }
        }
    }

    private void refresh() {

    }

    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        final BaseFragment fragment = getCurrentFragment();
        if (fragment != null) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
            if (fragment.allowBackPressed()) super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private BaseFragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getFragments().size() >= 1
                && fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1) instanceof BaseFragment
        )
            return (BaseFragment) fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1);
        return null;
    }

    @CallSuper
    public void logOut() {

    }

    public boolean isOffline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        Log.w("NetworkChangeReceiver", "is3g " + is3g);
        Log.w("NetworkChangeReceiver", "isWifi" + isWifi);

        if (!is3g && !isWifi) {
            Log.w("NetworkChangeReceiver", "true");

            return true;
        } else {
            Log.w("NetworkChangeReceiver", "false");

            return false;
        }
    }

}
