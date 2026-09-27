package com.zebra.payment.payment_intent;

import org.springframework.stereotype.Service;

// make both payment intent and payment intent response?
@Service
public class PaymentIntentService {
    public PaymentIntentResponse create(CreatePaymentIntentRequest request) {
        String id = "placeholder";
        return new PaymentIntentResponse(id, request.getAmount(), request.getCurrency(), "REQUIRES_PAYMENT_METHOD");
    }

    public PaymentIntentResponse update(String id, UpdatePaymentIntentRequest request) {
        // check id exists
        return null;
        // return new PaymentIntentResponse(id, request.getAmount(),
        // request.getCurrency(), "REQUIRES_PAYMENT_METHOD");
    }

    public PaymentIntentResponse confirm(String id) {
        // check exists, confirm
        return null;
    }

    public PaymentIntentResponse cancel(String id) {
        // check exists, cancel;
        return null;
    }
}
