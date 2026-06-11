package com.example.demo.order.domain;

import com.example.demo.common.data.OrderedItem;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private Integer id;
    private List<OrderedItem> items;
    private BigDecimal totalPrice;
    private BigDecimal totalBeforeDiscount;
    private Integer userId;
    private OrderShipping shipping;
    private OrderStatus status;
    private String couponCode;

    public static Order create(Integer userId, List<OrderedItem> items) {
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(items);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(order.calculateTotalPrice());

        return order;
    }

    public BigDecimal calculateTotalPrice() {
        return this.items.stream()
                .map(item->item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void applyDiscount(BigDecimal amountAfterDiscount, String couponCode) {
        this.totalBeforeDiscount = this.totalPrice;
        this.totalPrice = amountAfterDiscount;
        this.couponCode = couponCode;
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

    public BigDecimal getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public void setTotalBeforeDiscount(BigDecimal totalBeforeDiscount) {
        this.totalBeforeDiscount = totalBeforeDiscount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
