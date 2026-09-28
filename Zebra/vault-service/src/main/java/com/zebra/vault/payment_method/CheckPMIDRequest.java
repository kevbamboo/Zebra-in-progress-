package com.zebra.vault.payment_method;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CheckPMIDRequest {
    @NotBlank
    private String pmId;
    @NotBlank
    private String merchantId;
    @Positive
    private long amount; // in cents
    @NotBlank
    private String currency;
    @NotBlank
    private String paymentIntentId;

    public CheckPMIDRequest(String pmId, String merchantId, long amount, String currency,
            String paymentIntentId) {
        this.pmId = pmId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.currency = currency;
        this.paymentIntentId = paymentIntentId;
    }

    public String getPMId() {
        return this.pmId;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public long getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getPaymentIntentId() {
        return this.paymentIntentId;
    }
}
