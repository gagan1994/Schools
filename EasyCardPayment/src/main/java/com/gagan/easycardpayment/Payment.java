package com.gagan.easycardpayment;

import java.io.Serializable;

public class Payment implements Serializable {
    private double amount;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
