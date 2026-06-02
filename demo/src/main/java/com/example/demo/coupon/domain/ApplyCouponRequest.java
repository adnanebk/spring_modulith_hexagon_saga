package com.example.demo.coupon.domain;

import org.springframework.modulith.NamedInterface;

import java.math.BigDecimal;

@NamedInterface("public-api")
public record ApplyCouponRequest(Coupon coupon, java.util.List<CouponUsage> usageHistory, BigDecimal totalAmount) {
}