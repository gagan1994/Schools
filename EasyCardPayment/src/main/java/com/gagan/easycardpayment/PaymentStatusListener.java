package com.gagan.easycardpayment;

public interface PaymentStatusListener {
    void onSuccess(String text);

    void onError(String text);
}
