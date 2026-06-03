package com.example.demo.coupon.infra.adapters.api;


import com.example.demo.coupon.domain.AppliedCouponSummary;
import com.example.demo.coupon.infra.dto.ApplyCouponRequestDto;
import com.example.demo.coupon.ports.CouponServicePort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final CouponServicePort couponService;

    public CouponController(CouponServicePort couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/{couponCode}/applications")
    public AppliedCouponSummary applyCoupon(@PathVariable  String couponCode,@RequestBody ApplyCouponRequestDto request) {
        return couponService.applyCoupon(request.userId(),couponCode,request.amount());
    }

}
