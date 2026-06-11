package com.example.demo.order.infra.dto;

import com.example.demo.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(Integer id, BigDecimal totalPrice, BigDecimal totalBeforeDiscount, String couponCode, List<OrderItemDto> items,
                       OrderStatus status) {
}
