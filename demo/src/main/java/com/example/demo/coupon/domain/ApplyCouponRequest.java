package com.example.demo.coupon.domain;

import java.math.BigDecimal;

public record ApplyCouponRequest(Coupon coupon, CouponUsage couponUsage, BigDecimal totalAmount) {
}