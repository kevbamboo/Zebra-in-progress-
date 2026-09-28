package main.java.com.zebra.payment;

public class PaymentMethodResponse {
    // public String id/token;
    public String pmId;
    public String lastFourDigits;
    // public String type;
    public String cardBrand;
    // public String cardIssuer;
    public int expMonth;
    public int expYear;

    public PaymentMethodResponse(String pmId, String lastFourDigits, String cardBrand, int expMonth,
            int expYear) {
        this.pmId = pmId;
        this.lastFourDigits = lastFourDigits;
        this.cardBrand = cardBrand;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }
}
