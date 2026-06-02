package com.example.demo.coupon.domain;

import org.springframework.modulith.NamedInterface;

import java.math.BigDecimal;

@NamedInterface("public-api")
public record AppliedCouponSummary(BigDecimal discountAmount, BigDecimal originalAmount, DiscountType discountType) {
}
