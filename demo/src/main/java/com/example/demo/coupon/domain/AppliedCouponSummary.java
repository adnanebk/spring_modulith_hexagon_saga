package com.example.demo.coupon.domain;

import java.math.BigDecimal;

public record AppliedCouponSummary(BigDecimal discountAmount, BigDecimal originalAmount, DiscountType discountType) {
}
