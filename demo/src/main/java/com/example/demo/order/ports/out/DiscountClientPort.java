package com.example.demo.order.ports.out;



import java.math.BigDecimal;

public interface DiscountClientPort {


    BigDecimal discount(
            Integer userId,
            String couponCode,
            BigDecimal totalAmount
    );
}
