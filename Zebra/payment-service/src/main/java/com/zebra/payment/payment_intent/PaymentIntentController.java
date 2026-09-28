package com.zebra.payment.payment_intent;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/payment-intents")
@Validated
public class PaymentIntentController {
    private final PaymentIntentService service;

    public PaymentIntentController(PaymentIntentService service) {
        this.service = service;
    }

    @PostMapping
    public PaymentIntentResponse create(@Valid @RequestBody CreatePaymentIntentRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}")
    public PaymentIntentResponse update(@NotBlank @PathVariable String id,
            @Valid @RequestBody UpdatePaymentIntentRequest request) {
        return service.update(id, request);
    }

    @PostMapping("/{id}/confirm")
    public PaymentIntentResponse confirm(@NotBlank @PathVariable String id) {
        return service.confirm(id);
    }

    @PostMapping("/{id}/cancel")
    public PaymentIntentResponse cancel(@NotBlank @PathVariable String id) {
        return service.cancel(id);
    }
}
