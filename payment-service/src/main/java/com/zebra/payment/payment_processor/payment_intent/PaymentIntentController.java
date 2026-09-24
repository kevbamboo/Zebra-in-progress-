package com.zebra.payment.payment_processor.payment_intent;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-intents")
public class PaymentIntentController {
    private final PaymentIntentService service;

    public PaymentIntentController(PaymentIntentService service) {
        this.service = service;
    }

    @PostMapping
    public PaymentIntentResponse create(@RequestBody CreatePaymentIntentRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}")
    public PaymentIntentResponse update(@PathVariable String id,
            @RequestBody UpdatePaymentIntentRequest request) {
        return service.update(id, request);
    }

    @PostMapping("/{id}/confirm")
    public PaymentIntentResponse confirm(@PathVariable String id) {
        return service.confirm(id);
    }

    @PostMapping("/{id}/cancel")
    public PaymentIntentResponse cancel(@PathVariable String id) {
        return service.cancel(id);
    }
}
