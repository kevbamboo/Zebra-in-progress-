package com.zebra.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class VaultClient {
    private final RestClient vaultClient;

    public VaultClient(@Value("${vault.base_url}") String baseUrl) {
        this.vaultClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public createPaymentMethod() {}

    public PaymentMethodResponse usePaymentMethod(UsePaymentMethodRequest request) {
        return vaultClient.get().uri("/payment-methods").body(request).retrieve(PaymentMethodResponse.class);
    }
}