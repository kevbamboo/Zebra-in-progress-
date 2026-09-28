package com.zebra.vault.payment_method;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreatePaymentMethodRequest {
    @NotBlank
    private String merchantId; // should not actually be sent, should be api key or somthing and decoded to
                               // check
    @NotBlank
    private String pan;
    @Min(1)
    @Max(12)
    private int expMonth;
    @Min(1)
    @Max(9999)
    private int expYear;
    private String cvv;
    // not including name right now, because apparently it's not strongly verified

    public CreatePaymentMethodRequest(String merchantId, String pan, int expMonth, int expYear, String cvv) {
        this.merchantId = merchantId;
        this.pan = pan;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvv = cvv;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public String getPan() {
        return this.pan;
    }

    public int getExpMonth() {
        return this.expMonth;
    }

    public int getExpYear() {
        return this.expYear;
    }

    public String getCvv() {
        return this.cvv;
    }
}
