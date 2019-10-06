package com.gagan.school.library.view;

import android.Manifest;
import android.content.Intent;

/**
 * Created by Gagan S Patil on 10/9/19.
 */
public abstract class onCustomActivityResult {
    public final int ReqCode;
    public final static int SIGN_UP_REQ_CODE=1;
    public static final int BATTLE_REQ = 3;
    public static final int REQUEST_VIDEO_PERMISSIONS = 4;
    public static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public onCustomActivityResult(int reqCode) {
        ReqCode = reqCode;
    }
    public abstract void onSuccess(Intent data);
    public abstract void onError();
}
