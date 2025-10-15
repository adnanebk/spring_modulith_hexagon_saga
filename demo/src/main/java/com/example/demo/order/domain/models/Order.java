package com.example.demo.order.domain.models;


import com.example.demo.common.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private List<OrderItem> items;
    private Double total;
    private OrderShipping shipping;
    private OrderPayment paymentInfo;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }


    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }


    public OrderShipping getShipping() {
        return shipping;
    }

    public OrderPayment getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(OrderPayment paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public void setShipping(OrderShipping shipping) {
        this.shipping = shipping;
    }

}
