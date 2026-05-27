package com.example.demo.coupon.domain;

import java.math.BigDecimal;

public record AppliedCouponSummary(BigDecimal totalAmount, BigDecimal discountAmount, DiscountType discountType,
                                   CouponUsage couponUsage) {
}
