package com.example.demo.coupon.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum DiscountType {

    FIXED {
        @Override
        BigDecimal makeDiscount(BigDecimal originalPrice,BigDecimal discount) {
          return originalPrice.subtract(discount).max(BigDecimal.ZERO);
        }
    },
    PERCENTAGE {
        @Override
        BigDecimal makeDiscount(BigDecimal originalPrice,BigDecimal discount) {
            BigDecimal discountAmount = originalPrice.multiply(discount)
                    .divide(BigDecimal.valueOf(100), SCALE, RoundingMode.HALF_UP);
            return originalPrice.subtract(discountAmount);        }

    };

    public static final int SCALE = 2;
    abstract BigDecimal makeDiscount(BigDecimal originalPrice, BigDecimal discount);
}
