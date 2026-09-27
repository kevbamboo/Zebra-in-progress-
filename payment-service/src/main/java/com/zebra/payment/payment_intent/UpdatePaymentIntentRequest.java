package com.zebra.payment.payment_intent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class UpdatePaymentIntentRequest {
    @Positive
    private long amount;
    @NotBlank
    private String currency;
    // other fields

    public UpdatePaymentIntentRequest(long amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public long getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }
}
