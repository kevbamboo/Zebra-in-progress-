package com.zebra.vault.payment_method;

public class CreatePaymentMethodRequest {
    private String cardNumber;
    private int expMonth;
    private int expYear;
    private int cvv;

    public CreatePaymentMethodRequest(String cardNumber, int expMonth, int expYear, int cvv) {
        this.cardNumber = cardNumber;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public int getExpMonth() {
        return this.expMonth;
    }

    public int getExpYear() {
        return this.expYear;
    }

    public int getCvv() {
        return this.cvv;
    }
}
