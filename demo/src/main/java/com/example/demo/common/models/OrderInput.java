package com.example.demo.common.models;


import java.util.List;

public class OrderInput {

    private Integer  id;
    private List<OrderItem> items;
    private Double total;
    private OrderShipping shipping;
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
