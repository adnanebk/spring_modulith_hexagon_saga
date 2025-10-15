package com.example.demo.order.domain.models;

import com.example.demo.common.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderInput(OrderStatus status, Double total, List<OrderItem> items) {
}
