package com.example.demo.common.data;

import java.math.BigDecimal;

public record OrderedItem(Integer quantity, Integer productId, BigDecimal price) {

}
