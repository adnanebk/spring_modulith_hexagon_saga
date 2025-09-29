package com.example.demo.common.models;

import com.example.demo.common.enums.PaymentType;

public class OrderPayment {
    private Integer id;
    private PaymentType type;

    public OrderPayment( PaymentType type) {
        this.type = type;
    }

    public OrderPayment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }
}
