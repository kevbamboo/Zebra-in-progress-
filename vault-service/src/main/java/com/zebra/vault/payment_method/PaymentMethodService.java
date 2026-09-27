package com.zebra.vault.payment_method;

import com.zebra.vault.PanEncryptionService;
import com.zebra.vault.VaultService;

import java.time.YearMonth;

import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {
    private final VaultService vaultService;
    private final PanEncryptionService panEncryptionService;

    public PaymentMethodService(VaultService vaultService, PanEncryptionService panEncryptionService) {
        this.vaultService = vaultService;
        this.panEncryptionService = panEncryptionService;
    }

    private String normalizePan(String pan) {
        return pan.replaceAll("[ -]", "");
    }

    private boolean hasValidPanFormat(String pan) {
        if (pan == null)
            return false;

        return pan.matches("\\d{13,19}");
    }

    private boolean passesLuhnCheck(String pan) {
        int sum = 0;
        boolean doubleDigit = false;

        for (int i = pan.length() - 1; i >= 0; i--) {
            int digit = pan.charAt(i) - '0';

            if (doubleDigit) {
                digit *= 2;
                if (digit > 9)
                    digit -= 9;
            }

            sum += digit;
            doubleDigit = !doubleDigit;
        }

        return sum % 10 == 0;
    }

    private boolean isValidExpiration(int expMonth, int expYear) {
        if (expMonth < 1 || expMonth > 12) {
            return false;
        } // shouldn't happen because it already checked in endpoint handling?

        if (YearMonth.of(expYear, expMonth).isBefore(YearMonth.now())) {
            return false;
        }
        return true;
    }

    private String getCardBrand(String pan) {
        if (pan.startsWith("4")) {
            return "VISA";
        }

        if (pan.startsWith("34") || pan.startsWith("37")) {
            return "AMEX";
        }

        int firstTwo = Integer.parseInt(pan.substring(0, 2));

        if (firstTwo >= 51 && firstTwo <= 55) {
            return "MASTERCARD";
        }

        int firstFour = Integer.parseInt(pan.substring(0, 4));

        if (firstFour >= 2221 && firstFour <= 2720) {
            return "MASTERCARD";
        }

        return "UNKNOWN";
    }

    private boolean hasValidCvvFormat(String cvv, String brand) {
        if (cvv == null) {
            return false;
        }

        if (brand.equalsIgnoreCase("AMEX")) {
            return cvv.matches("\\d{4}");
        }

        return cvv.matches("\\d{3}");
    }

    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest request) {
        // check validity with vault:
        // PAN right format/length, Luhn check, exp date not in past, cvv valid
        // format
        // required fields present

        if (request.getPan() == null) {
            throw new IllegalArgumentException("PAN is required");
        }

        String pan = normalizePan(request.getPan());

        if (!hasValidPanFormat(pan)) {
            throw new IllegalArgumentException("Invalid PAN format");
        }

        if (!passesLuhnCheck(pan)) {
            throw new IllegalArgumentException("Invalid PAN");
        }

        if (!isValidExpiration(request.getExpMonth(), request.getExpYear())) {

            throw new IllegalArgumentException("Invalid expiration date");
        }

        String cardBrand = getCardBrand(pan);

        if (cardBrand.equals("UNKNOWN")) {
            throw new IllegalArgumentException("Unsupported card brand");
        }

        if (!hasValidCvvFormat(request.getCvv(), cardBrand)) {

            throw new IllegalArgumentException("Invalid CVV");
        }

        // Next:
        // encrypt PAN
        // generate payment method ID
        // store payment method
        // do NOT store CVV
        // return safe PaymentMethodResponse
        byte[] encryptedPan = panEncryptionService.encrypt(pan);
        int version = panEncryptionService.getActiveKeyVersion(); // maybe encrypt should return these together?
        String lastFourDigits = pan.substring(pan.length() - 4);
        String paymentMethodId = vaultService.savePaymentMethod(new PaymentMethod(
                request.getMerchantId(), version, encryptedPan,
                lastFourDigits, request.getExpMonth(), request.getExpYear(), cardBrand));

        return new PaymentMethodResponse(paymentMethodId, lastFourDigits, cardBrand,
                request.getExpMonth(), request.getExpYear());
    }
}
