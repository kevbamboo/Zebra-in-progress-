package com.zebra.payment.payment_processor.payment_intent;

public class PaymentIntentResponse {
    private String id;
    private long amount; // in cents
    private String currency;
    private String status;
    // other metadata in PaymentIntent class?

    public PaymentIntentResponse(String id, long amount, String currency, String status) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public long getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getStatus() {
        return this.status;
    }

}
