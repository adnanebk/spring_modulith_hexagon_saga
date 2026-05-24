package com.example.demo.common.data;

import java.math.BigDecimal;

public record OrderItemWithPrice(Integer quantity, Integer productId, BigDecimal price) {

}
