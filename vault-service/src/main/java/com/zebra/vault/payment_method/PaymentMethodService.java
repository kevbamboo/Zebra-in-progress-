package com.zebra.vault.payment_method;

import com.zebra.vault.VaultService;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {
    VaultService vaultService;

    public PaymentMethodService(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest request) {
        PaymentMethod pm = new PaymentMethod(request.getCardNumber(), request.getExpMonth(), request.getExpYear(),
                request.getCvv());

        // check validity with vault
        vaultService.getId(pm);
        // PaymentMethodResponse pmResponse = new PaymentMethodResponse();
        // make payment method
        // turn into response (make token/id? + last 4 digits? + type + card issuer /
        // bank?)
        return null;
    }
}
