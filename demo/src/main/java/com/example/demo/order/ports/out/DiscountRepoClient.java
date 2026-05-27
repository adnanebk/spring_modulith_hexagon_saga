package com.example.demo.order.ports.out;


import java.math.BigDecimal;

public interface DiscountRepoClient {

     void saveCouponUsage(Integer userId, Integer orderId, String couponCode);

    BigDecimal discount(
            Integer userId,
            String couponCode,
            BigDecimal totalAmount
    );
}
