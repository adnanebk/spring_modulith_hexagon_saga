package com.example.demo.order.infra.dto;

import java.math.BigDecimal;

public record OrderItemDto(String productName, Integer quantity, BigDecimal price) {

}
