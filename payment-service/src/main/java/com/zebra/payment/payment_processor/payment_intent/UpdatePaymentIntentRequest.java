package com.zebra.payment.payment_processor.payment_intent;

public class UpdatePaymentIntentRequest {
    private long amount;
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
