package com.example.demo.coupon.domain;

import java.math.BigDecimal;

public record ApplyCouponRequest(Coupon coupon, java.util.List<CouponUsage> usageHistory, BigDecimal totalAmount) {
}