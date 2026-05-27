package com.example.demo.order.domain;

import com.example.demo.common.data.OrderedItem;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private List<OrderedItem> items;
    private BigDecimal totalPrice;
    private BigDecimal totalWithDiscount;
    private Integer userId;
    private OrderShipping shipping;
    private OrderStatus status;

    public void calculateTotalPrice() {
        this.totalPrice = this.items.stream()
                .map(item->item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    public List<OrderedItem> getItems() {
        return items;
    }

    public void setItems(List<OrderedItem> items) {
        this.items = items;
    }


    public OrderShipping getShipping() {
        return shipping;
    }


    public void setShipping(OrderShipping shipping) {
        this.shipping = shipping;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(BigDecimal totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }
}
