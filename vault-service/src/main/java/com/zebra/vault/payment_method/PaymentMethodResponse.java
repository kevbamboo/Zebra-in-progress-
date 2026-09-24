package com.zebra.vault.payment_method;

public class PaymentMethodResponse {
    // public String id/token;
    public String lastFourDigits;
    public String type;
    public String cardIssuer; // optional?

    public PaymentMethodResponse(PaymentMethod pm) {
        // this.lastFourDigits;;
    }
}
