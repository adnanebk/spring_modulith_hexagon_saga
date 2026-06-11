package com.example.demo.coupon.ports;

import com.example.demo.coupon.domain.AppliedCouponSummary;

import java.math.BigDecimal;

public interface CouponServicePort {
    AppliedCouponSummary applyCoupon(Integer userId, String couponCode, BigDecimal totalAmount);


    void saveCouponUsage(Integer orderId, Integer userId,String couponCode);
}
