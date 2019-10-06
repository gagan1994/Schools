package com.gagan.school.library.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.gagan.school.R;
import com.gagan.school.library.interfaces.IActivityHelper;
import com.gagan.school.library.utils.Utils;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Gagan S Patil on 20/8/19.
 */
public class ProgressStatusBarAdapter {
    static HashMap<IActivityHelper, ProgressStatusBarAdapter> instance=new HashMap<>();
    private final IActivityHelper helper;
    private ProgressDialog mProgressStatusBar;
    private String TAG = ProgressStatusBarAdapter.class.getSimpleName();
    private boolean hidden;


    private ProgressStatusBarAdapter(IActivityHelper context) {
        this.helper = context;
    }

    Stack<Object> callbackList = new Stack<>();

    public static final ProgressStatusBarAdapter getInstance(IActivityHelper helper) {
        return instance.get(helper.getActivityContext());
    }

    public static void remove(IActivityHelper baseActivity) {
        instance.remove(baseActivity);
    }


    private void error() {
        callbackList.clear();
        mProgressStatusBar.dismiss();
    }

    public void notifyStatusChanged() {
        try {
            if (mProgressStatusBar != null && (callbackList.size() == 0 ||
                    Utils.isInBackground(mProgressStatusBar.getContext()))) {
                helper.getActivityContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hide();
                    }
                });
            } else {
                show();
            }
        } catch (Exception e) {
            error();
            Log.e(TAG, "notifyStatusChanged : " + e.getMessage());
        }
    }

    private void hide() {
        mProgressStatusBar.hide();
        hidden = true;
    }


    public void show() {
        helper.getActivityContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressStatusBar == null) {
                    mProgressStatusBar = getProgressBar(helper.getActivityContext());
                }
                if (hidden) mProgressStatusBar.show();
                hidden = false;
            }
        });
    }

    private ProgressDialog getProgressBar(Context context) {
        mProgressStatusBar = ProgressDialog.show(context,
                null, null, true, true);
        mProgressStatusBar.dismiss();
        mProgressStatusBar.setContentView(R.layout.progress_layout);

        mProgressStatusBar.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return mProgressStatusBar;
    }


    public static void init(IActivityHelper activity) {
        instance.put(activity.getActivityContext(),
                new ProgressStatusBarAdapter(activity));
    }


    public void push() {
        callbackList.push(null);
        notifyStatusChanged();
    }

    public void pop() {
        try {
            callbackList.pop();
            notifyStatusChanged();
        } catch (Exception e) {
            error();
            Log.e(TAG, "pop : " + e.getMessage());
        }
    }

}
