package com.example.demo.order.infra.dto;

import java.math.BigDecimal;

public record OrderDto(Integer id, BigDecimal totalAmount,BigDecimal amountAfterDiscount) {
}
