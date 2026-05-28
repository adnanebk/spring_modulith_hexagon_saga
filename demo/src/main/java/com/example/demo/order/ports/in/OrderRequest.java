package com.example.demo.order.ports.in;

import com.example.demo.common.data.OrderedItem;
import jakarta.annotation.Nullable;

import java.util.List;

public record OrderRequest(Integer userId, List<OrderedItem> orderItems, String paymentToken,@Nullable String couponCode) {

    public OrderRequest(Integer userId, List<OrderedItem> orderItems, String paymentToken) {
        this(userId, orderItems, paymentToken, "");
    }
}