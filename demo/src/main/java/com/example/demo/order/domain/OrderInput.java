package com.example.demo.order.domain;

import java.util.List;

public record OrderInput(OrderStatus status, Double total, List<OrderItem> items) {
}
