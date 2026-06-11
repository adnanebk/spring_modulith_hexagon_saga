package com.example.demo.common.events;


import jakarta.annotation.Nullable;

public record OrderCompletedEvent(Integer orderId, Integer userId, @Nullable String couponCode)  {
}
