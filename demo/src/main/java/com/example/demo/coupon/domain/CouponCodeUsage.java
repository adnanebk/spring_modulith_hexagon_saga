package com.example.demo.coupon.domain;


import org.springframework.modulith.NamedInterface;

@NamedInterface("public-api")
public record CouponCodeUsage(Integer userId,Integer orderId, String couponCode) {


}
