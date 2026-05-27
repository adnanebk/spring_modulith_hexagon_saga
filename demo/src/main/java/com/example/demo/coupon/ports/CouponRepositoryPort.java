package com.example.demo.coupon.ports;

import com.example.demo.coupon.domain.Coupon;

import java.util.Optional;

public interface CouponRepositoryPort {
    Optional<Coupon> findByCode(String couponCode);

}
