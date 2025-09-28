package com.example.demo.order.domain.models;

import com.example.demo.common.enums.OrderStatus;

import java.util.List;

public class OrderInput {

    private Integer  id;
    private List<OrderItem> items;
    private Double total;
    private OrderStatus status;
    private OrderShipping orderShipping;
    private OrderPayment paymentInfo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderShipping getOrderShipping() {
        return orderShipping;
    }

    public OrderPayment getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(OrderPayment paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public void setOrderShipping(OrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

}
