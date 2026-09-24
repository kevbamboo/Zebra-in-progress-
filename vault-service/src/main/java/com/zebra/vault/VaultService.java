package com.zebra.vault;

import com.zebra.vault.payment_method.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public class VaultService {

    public String getId(PaymentMethod paymentMethod) {
        return "vault-" + paymentMethod.getCardNumber()
                .replaceAll("\\D", "")
                .substring(Math.max(0, paymentMethod.getCardNumber().replaceAll("\\D", "").length() - 4));
    }
}
