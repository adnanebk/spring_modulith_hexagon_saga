package com.example.demo.order.domain.models;

import com.example.demo.common.enums.OrderStatus;
import com.example.demo.common.models.OrderItem;

import java.util.List;

public record OrderInput(OrderStatus status, Double total, List<OrderItem> items) {
}
