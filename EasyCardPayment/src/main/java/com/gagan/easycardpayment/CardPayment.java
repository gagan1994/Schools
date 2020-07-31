package com.gagan.easycardpayment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

public class CardPayment {
    private final Context activity;
    private final Payment payment;

    public CardPayment(Context context, Payment payment) {
        this.activity = context;
        this.payment = payment;
    }

    public static class Builder {
        private Context context;
        private Payment payment;

        public Builder with(Context context) {
            this.context = context;
            payment = new Payment();
            return this;
        }

        public Builder setAmount(String toString) {
            payment.setAmount(Double.parseDouble(toString));
            return this;
        }

        public CardPayment build() {
            if (context == null) {
                throw new IllegalStateException("Activity must be specified using with() call before build()");
            }
            if (payment.getAmount() == 0) {
                throw new IllegalStateException("Must call setAmount() before build().");
            }
            return new CardPayment(context, payment);
        }

    }

    public void startPayment() {
        Intent payIntent = new Intent(activity, CardPaymentActivity.class);
        payIntent.putExtra(CardPaymentActivity.EXTRA_KEY_PAYMENT, payment);

        // Start Payment Activity
        activity.startActivity(payIntent);
    }

    public void setPaymentListener(PaymentStatusListener paymentCallBack) {
        Singleton singleton = Singleton.getInstance();
        singleton.setListener(paymentCallBack);    }
}
