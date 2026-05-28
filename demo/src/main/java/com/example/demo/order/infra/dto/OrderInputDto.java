package com.example.demo.order.infra.dto;



import java.util.List;

public record OrderInputDto(
    List<OrderItemInputDto> items,
    Integer userId,
    String paymentToken,
    String couponCode
) {
}
