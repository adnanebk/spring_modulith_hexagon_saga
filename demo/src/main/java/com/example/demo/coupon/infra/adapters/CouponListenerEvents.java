package com.example.demo.coupon.infra.adapters;


import com.example.demo.common.events.OrderCompletedEvent;
import com.example.demo.coupon.application.CouponService;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class CouponListenerEvents {

    private final CouponService couponService;

    public CouponListenerEvents(CouponService couponService) {
        this.couponService = couponService;
    }

    @ApplicationModuleListener
    public void handleOrderCompletedEvent(OrderCompletedEvent event) {
        couponService.saveCouponUsage(event.orderId(), event.userId(), event.couponCode());
    }
}
