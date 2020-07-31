package com.gagan.easycardpayment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class CardPaymentActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_PAYMENT = "EXTRA_KEY_PAYMENT";
    private CardInputWidget cardInputWidget;
    private String paymentIntentClientSecret = "pi_1HAAGTEeAVTTYm4p7GShuXq9_secret_XLJ9big9XFQecc64WI7U3cU0t";
    private Stripe stripe;
    private TextView payAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);
        Intent intent = getIntent();
        Payment payment = (Payment) intent.getSerializableExtra(EXTRA_KEY_PAYMENT);


        PaymentConfiguration.init(
                this,
                "pk_test_D1SycXapSA98JZLkaDl9cgMt00ohwn1ilr\n"
        );
        payAmount = findViewById(R.id.payAmount);
        payAmount.setText("₹ " + payment.getAmount());
        cardInputWidget = findViewById(R.id.cardInputWidget);
        findViewById(R.id.payButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                    final Context context = getApplicationContext();
                    stripe = new Stripe(
                            context,
                            PaymentConfiguration.getInstance(context).getPublishableKey()
                    );
                    stripe.confirmPayment(CardPaymentActivity.this, confirmParams);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<CardPaymentActivity> activityRef;

        PaymentResultCallback(@NonNull CardPaymentActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final CardPaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent),
                        true
                );
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed
                activity.displayAlert(
                        "Payment failed",
                        paymentIntent.getLastPaymentError().getMessage(),
                        false
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final CardPaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString(), false);
        }
    }

    private void displayAlert(String message, String fullMessage, boolean b) {
        Toast.makeText(this, "message: " + message + " body: " + fullMessage, Toast.LENGTH_LONG).show();
        if (b) {
            Singleton.getInstance().getListener().onSuccess(fullMessage);
        } else {
            Singleton.getInstance().getListener().onError(fullMessage);
        }
        finish();
    }
}