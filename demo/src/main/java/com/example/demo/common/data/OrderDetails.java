package com.example.demo.common.data;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetails(Integer orderId, Integer userId, String paymentToken, List<OrderedItem> items, @Nullable BigDecimal totalPrice,
                           @Nullable String couponCode) {

}
