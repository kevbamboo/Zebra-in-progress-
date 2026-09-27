package com.zebra.vault.payment_method;

// is this even needed?

public class PaymentMethod {
    private String merchantId;
    private int encryptionKeyVersion;
    private byte[] encryptedPan;
    private String lastFourDigits;
    private int expMonth;
    private int expYear;
    private String cardBrand;

    public PaymentMethod(String merchantId, int encryptionKeyVersion, byte[] encryptedPan, String lastFourDigits,
            int expMonth,
            int expYear, String cardBrand) {
        this.merchantId = merchantId;
        this.encryptionKeyVersion = encryptionKeyVersion;
        this.encryptedPan = encryptedPan;
        this.lastFourDigits = lastFourDigits;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cardBrand = cardBrand;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public int getEncryptionKeyVersion() {
        return this.encryptionKeyVersion;
    }

    public byte[] getEncryptedPan() {
        return this.encryptedPan;
    }

    public String getLastFourDigits() {
        return this.lastFourDigits;
    }

    public int getExpMonth() {
        return this.expMonth;
    }

    public int getExpYear() {
        return this.expYear;
    }

    public String getCardBrand() {
        return this.cardBrand;
    }
}
