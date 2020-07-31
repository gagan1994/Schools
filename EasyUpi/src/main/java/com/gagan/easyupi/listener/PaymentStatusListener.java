package com.gagan.easyupi.listener;

import com.gagan.easyupi.model.TransactionDetails;

public interface PaymentStatusListener {
    void onTransactionCompleted(TransactionDetails transactionDetails);

    void onTransactionSuccess();

    void onTransactionSubmitted();

    void onTransactionFailed();

    void onTransactionCancelled();

    void onAppNotFound();
}
