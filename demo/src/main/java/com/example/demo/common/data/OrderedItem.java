package com.example.demo.common.data;

import java.math.BigDecimal;

public record OrderedItem(Integer quantity, Integer productId, BigDecimal price) {

    public OrderedItem(Integer productId, int quantity) {
        this(quantity, productId, BigDecimal.ZERO);
    }

    public OrderedItem withPrice(BigDecimal price) {
        return new OrderedItem(this.quantity, this.productId, price);
    }
}
