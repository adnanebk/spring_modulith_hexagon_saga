package com.example.demo.coupon.ports;

import com.example.demo.coupon.domain.CouponUsage;

import java.util.Optional;

public interface CouponUsageRepositoryPort {
    Optional<CouponUsage> findLastUsageByUserIdAndCouponId(Integer userId, Integer couponId);

    void save(CouponUsage couponUsage);
}
