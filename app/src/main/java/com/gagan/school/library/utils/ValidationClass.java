package com.gagan.school.library.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Gagan S Patil on 20/7/19.
 */
public class ValidationClass {
    public static boolean validatePhoneNum(String phNumber) {
        try {
            if (phNumber != null) {
                if (phNumber.trim().length() == 10 ) {
                    return true;
                } else {
                    if (phNumber.trim().length() == 11)
                        return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean validateUserName(String name) {
        return name != null &&
                name.trim().length() > 2;
    }
    public static boolean validateNull(String text){
        return !TextUtils.isEmpty(text);

    }
    public static boolean validateEmail(String email){
        Pattern pattern =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        return email != null && pattern
                .matcher(email).matches();
    }
    public static boolean validatePassword(String password) {
        Pattern textPattern = Pattern.compile(".{4,}");
        return password != null && textPattern
                .matcher(password).matches();
    }
}
