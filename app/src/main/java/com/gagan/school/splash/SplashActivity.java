package com.gagan.school.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.gagan.school.R;
import com.gagan.school.library.view.BaseActivity;
import com.gagan.school.login.LoginActivity;
import com.rbddevs.splashy.Splashy;

/**
 * Created by Gagan S Patil on 26/9/19.
 */
public class SplashActivity extends BaseActivity {

    @Override
    public int getLayout() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Splashy(this)
                .setLogo(R.drawable.splashy)
                .setAnimation(Splashy.Animation.SLIDE_LEFT_ENTER, 500)
                .setTitle("School Name")
                .setTitleSize(28f)
                .setTitleColor("#F9AA33")
                .setSubTitle("School Tag.")
                .setSubTitleColor("#FFFFFF")
                .setSubTitleSize(14f)
                .setProgressColor(R.color.white)
                .setBackgroundColor(R.color.colorPrimary)
                //.setBackgroundResource(getResources().getColor(R.color.colorPrimary))
                .setFullScreen(true)
                .setTime(5000)
                .show();


        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(() -> {
            Intent refresh = new Intent(SplashActivity.this,
                    LoginActivity.class);
            startActivity(refresh);
            finish();
        }, 3000);

    }
}
