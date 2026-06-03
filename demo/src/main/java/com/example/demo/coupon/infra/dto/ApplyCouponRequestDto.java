package com.example.demo.coupon.infra.dto;

import java.math.BigDecimal;

public record ApplyCouponRequestDto(Integer userId, BigDecimal amount) {
}
