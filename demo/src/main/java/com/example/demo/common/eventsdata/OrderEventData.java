package com.example.demo.common.eventsdata;

import com.example.demo.common.enums.PaymentType;

import java.util.List;

public class OrderEventData {

    private Integer  id;
    private Integer shippingId;
    private List<OrderItemEventData> items;
    private Integer paymentId;
    private PaymentType paymentType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }



    public List<OrderItemEventData> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEventData> items) {
        this.items = items;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
