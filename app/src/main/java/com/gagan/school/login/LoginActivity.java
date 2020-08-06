package com.gagan.school.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gagan.school.home.MainActivity;
import com.gagan.school.R;
import com.gagan.school.errors.ErrorWrappers;
import com.gagan.school.library.view.BaseActivity;
import com.gagan.school.library.view.BaseTextInputLayout;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;

import static com.gagan.school.library.utils.Utils.isCheckRole;
import static com.gagan.school.roles.ROLE.SUPER_ADMIN;

/**
 * Created by Gagan S Patil on 26/9/19.
 */
public class LoginActivity extends BaseActivity {
    public static final String USER_ROLE = "USER_ROLE";
    public static final String USER_EMAIL = "USER_EMAIL";
    @BindView(R.id.email)
    TextInputEditText emailEt;
    @BindView(R.id.password)
    TextInputEditText password;

    @BindView(R.id.emailLayout)
    BaseTextInputLayout emailLayout;

    @BindView(R.id.passwordLayout)
    BaseTextInputLayout passwordLayout;

    @BindView(R.id.logIn)
    View logIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logIn.setOnClickListener(this::onLoginClick);
    }

    public void onLoginClick(View view) {
        int emailErr = ErrorWrappers.validateEmail(emailEt.getText().toString());
        int passwordErr = ErrorWrappers.validatePassword(password.getText().toString());
        setErrorBottom(emailLayout, emailErr);
        setErrorBottom(passwordLayout, passwordErr);
        if (emailErr == 0 && passwordErr == 0) {
            openMainActivity(emailEt.getText().toString());
        }

    }

    private void openMainActivity(String email) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USER_ROLE, SUPER_ADMIN.getNumber());
        intent.putExtra(USER_EMAIL, email);
        startActivity(intent);
        finish();
    }

    @Override
    public int getLayout() {
        return R.layout.login_layout;
    }

}
