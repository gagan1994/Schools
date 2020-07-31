package com.gagan.school.home.adapters.model;

import com.gagan.school.model.payment.PaymentNotif;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.UUID;

public class PaymentModel implements Serializable {

    private final String amount;
    private final String vpa;
    private final String transactionId;

    public PaymentModel(NotificationModel model) {
        PaymentNotif paymentNotif = new Gson().fromJson(model.getData(), PaymentNotif.class);
        amount = paymentNotif.getAmount();
        vpa = paymentNotif.getVpa();
        transactionId = "TID-" + UUID.randomUUID();

    }

    public String getAmount() {
        return amount;
    }

    public String getVpa() {
        return vpa;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionRefId() {
        return transactionId;
    }
}
