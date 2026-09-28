package com.zebra.vault.payment_method;

public class PaymentMethodResponse {
    // public String id/token;
    public String paymentMethodId;
    public String lastFourDigits;
    // public String type;
    public String cardBrand;
    // public String cardIssuer;
    public int expMonth;
    public int expYear;

    public PaymentMethodResponse(String paymentMethodId, String lastFourDigits, String cardBrand, int expMonth,
            int expYear) {
        this.paymentMethodId = paymentMethodId;
        this.lastFourDigits = lastFourDigits;
        this.cardBrand = cardBrand;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }
}
