package com.gagan.school.library.utils;

public class SetErrorFlag {
    private SetErrorFlag(){

    }

    public static int getErrorFlag(boolean isValid, int stringId) {
        return isValid ? 0 : stringId;
    }
}
