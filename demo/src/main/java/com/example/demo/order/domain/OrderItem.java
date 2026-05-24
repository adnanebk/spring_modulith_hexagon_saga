package com.example.demo.order.domain;

public class OrderItem {

    private Integer quantity;
    private Integer productId;

    public OrderItem(Integer productId,Integer quantity) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public OrderItem() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

}
