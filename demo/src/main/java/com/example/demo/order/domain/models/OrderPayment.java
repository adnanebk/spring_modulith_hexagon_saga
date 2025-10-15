package com.example.demo.order.domain.models;

import com.example.demo.common.enums.PaymentType;

public class OrderPayment {
    private PaymentType type;

    public OrderPayment( PaymentType type) {
        this.type = type;
    }

    public OrderPayment() {
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }
}
