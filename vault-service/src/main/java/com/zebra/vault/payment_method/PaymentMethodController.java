package com.zebra.vault.payment_method;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {
    private final PaymentMethodService service;

    public PaymentMethodController(PaymentMethodService service) {
        this.service = service;
    }

    @PostMapping()
    public PaymentMethodResponse create(@Valid @RequestBody CreatePaymentMethodRequest request) {
        return service.createPaymentMethod(request);
        // technically should check merchant api key/session/jwt to get merchant id
    }
}
