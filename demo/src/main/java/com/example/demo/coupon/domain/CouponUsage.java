package com.example.demo.coupon.domain;

import java.time.LocalDate;

public class CouponUsage {

    private Integer couponId;
    private Integer orderId;
    private Integer userId;
    private LocalDate usedAt;

    public CouponUsage(Integer userId, Integer couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }

    public CouponUsage(Integer couponId, Integer orderId, Integer userId) {
        this.couponId = couponId;
        this.orderId = orderId;
        this.userId = userId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDate usedAt) {
        this.usedAt = usedAt;
    }
}
