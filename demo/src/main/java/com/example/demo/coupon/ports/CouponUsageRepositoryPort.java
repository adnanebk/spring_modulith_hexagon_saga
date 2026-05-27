package com.example.demo.coupon.ports;

import com.example.demo.coupon.domain.CouponUsage;

import java.util.List;
import java.util.Optional;

public interface CouponUsageRepositoryPort {
    List<CouponUsage> findAllByUserIdAndCouponId(Integer userId, Integer couponId);

    void save(CouponUsage couponUsage);
}
