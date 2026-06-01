package com.example.demo.common.data;

import com.example.demo.coupon.domain.DiscountType;

import java.math.BigDecimal;

public record AppliedCouponSummary(BigDecimal discountAmount, BigDecimal originalAmount, DiscountType discountType) {
}
