package com.gagan.school.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.gagan.school.R;
import com.gagan.school.home.MainActivity;
import com.gagan.school.library.view.BaseActivity;
import com.gagan.school.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rbddevs.splashy.Splashy;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

import static com.firebase.ui.auth.ErrorCodes.DEVELOPER_ERROR;
import static com.firebase.ui.auth.ErrorCodes.PROVIDER_ERROR;
import static com.gagan.school.login.LoginActivity.USER_EMAIL;
import static com.gagan.school.login.LoginActivity.USER_ROLE;
import static com.gagan.school.roles.ROLE.SUPER_ADMIN;

/**
 * Created by Gagan S Patil on 26/9/19.
 */
public class SplashActivity extends BaseActivity {

    private static final long DELAY = 2000;
    private int RC_SIGN_IN = 1234;
    @BindViews({R.id.signup})
    List<View> logOutViews;

    @Override
    public int getLayout() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_splash_screen;
    }

    @OnClick(R.id.signup)
    public void signUp() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            new Handler().postDelayed(() -> openMainAcitivity(user), DELAY);
        }
        updateLogOutUi(user == null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                updateUi(user);
            } else {
                if (response != null) {
                    if (response.getError().getErrorCode() == DEVELOPER_ERROR) {
                        displayMessage("You have already logged in to Twitter/Facebook/Google." +
                                " \n Please use the same account to login"
                        );
                        return;
                    }
                    if (response.getError().getErrorCode() == PROVIDER_ERROR) {
                        displayMessage("Add developer keystore"
                        );
                        return;
                    }
                    if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                        displayMessage("No network Connection");
                    }
                }
            }
        }
    }

    private void updateUi(FirebaseUser user) {
        if (user != null) {
            openMainAcitivity(user);
        }
        updateLogOutUi(user == null);
    }

    private void openMainAcitivity(FirebaseUser user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USER_ROLE, SUPER_ADMIN.getNumber());
        intent.putExtra(USER_EMAIL, user.getEmail());
        startActivity(intent);
        finish();
    }

    private void updateLogOutUi(boolean b) {
        for (View view : logOutViews) {
            view.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }
}
