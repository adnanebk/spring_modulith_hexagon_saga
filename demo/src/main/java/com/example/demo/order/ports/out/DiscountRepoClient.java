package com.example.demo.order.ports.out;


import com.example.demo.common.data.CouponCodeUsage;

import java.math.BigDecimal;

public interface DiscountRepoClient {

     void saveCouponUsage(CouponCodeUsage couponCodeUsage);

    BigDecimal discount(
            Integer userId,
            String couponCode,
            BigDecimal totalAmount
    );
}
