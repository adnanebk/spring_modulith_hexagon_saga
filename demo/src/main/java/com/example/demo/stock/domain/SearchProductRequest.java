package com.example.demo.stock.domain;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public record SearchProductRequest(@Nullable  String searchTerm,@Nullable String category,@Nullable BigDecimal minPrice,@Nullable BigDecimal maxPrice,
                                   int page, int size, String sort, String direction) {
}
