package com.example.demo.common.data;

import java.math.BigDecimal;

public record ProductInStock(Integer productId, BigDecimal price, Integer quantity) {
}