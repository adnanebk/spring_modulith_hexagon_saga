package com.example.demo.payment.domain.models;

import com.example.demo.common.enums.PaymentType;

public class Payment {
    private Integer id;
    private PaymentType type;

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
