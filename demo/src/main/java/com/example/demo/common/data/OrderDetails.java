package com.example.demo.common.data;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetails(Integer orderId, String paymentToken, List<OrderItemWithPrice> items, BigDecimal totalPrice) {

}
