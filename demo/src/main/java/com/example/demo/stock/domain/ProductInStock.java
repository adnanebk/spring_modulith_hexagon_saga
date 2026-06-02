package com.example.demo.stock.domain;

import org.springframework.modulith.NamedInterface;

import java.math.BigDecimal;

@NamedInterface("public-api")
public record ProductInStock(Integer productId, BigDecimal price, Integer quantity) {
}