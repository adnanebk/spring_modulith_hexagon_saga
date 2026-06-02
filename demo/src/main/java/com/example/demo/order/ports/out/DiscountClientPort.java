package com.example.demo.order.ports.out;


import com.example.demo.coupon.domain.CouponCodeUsage;

import java.math.BigDecimal;

public interface DiscountClientPort {

     void saveCouponUsage(CouponCodeUsage couponCodeUsage);

    BigDecimal discount(
            Integer userId,
            String couponCode,
            BigDecimal totalAmount
    );
}
