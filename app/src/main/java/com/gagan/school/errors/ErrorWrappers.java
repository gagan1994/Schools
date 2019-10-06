package com.gagan.school.errors;

import com.gagan.school.R;
import com.gagan.school.library.utils.ValidationClass;

/**
 * Created by Gagan S Patil on 26/9/19.
 */
public class ErrorWrappers {
    public static int validateEmail(String email) {
        return ValidationClass.validateEmail(email) ? 0 : R.string.error_valid_email;
    }
    public static int validatePassword(String password){
     return ValidationClass.validatePassword(password)?0:R.string.passwordErr;
    }
}
