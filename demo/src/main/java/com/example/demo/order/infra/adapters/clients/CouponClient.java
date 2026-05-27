package com.example.demo.order.infra.adapters.clients;

import com.example.demo.common.data.CouponCodeUsage;
import com.example.demo.coupon.ports.CouponServicePort;
import com.example.demo.order.ports.out.DiscountRepoClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class CouponClient implements DiscountRepoClient {

    private final CouponServicePort couponServicePort;

    public CouponClient(CouponServicePort couponServicePort) {
        this.couponServicePort = couponServicePort;
    }

    @Override
    public void saveCouponUsage(CouponCodeUsage couponCodeUsage) {
        couponServicePort.saveCouponUsage(couponCodeUsage);
    }

    @Override
    public BigDecimal discount(Integer userId,String couponCode, BigDecimal totalAmount) {
        return couponServicePort.applyCoupon(userId, couponCode, totalAmount).discountAmount();
    }
}
