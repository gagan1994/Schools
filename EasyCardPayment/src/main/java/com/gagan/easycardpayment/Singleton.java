package com.gagan.easycardpayment;

import androidx.annotation.NonNull;

public class Singleton {
    private static Singleton instance = null;

    private PaymentStatusListener listener;

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    @NonNull
    public PaymentStatusListener getListener() {
        return instance.listener;
    }

    void setListener(@NonNull PaymentStatusListener listener) {
        instance.listener = listener;
    }

    public void detachListener() {
        instance.listener = null;
    }

    public boolean isListenerRegistered() {
        return (instance.listener != null);
    }
}