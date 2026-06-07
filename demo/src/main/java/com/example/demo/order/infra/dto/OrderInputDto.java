package com.example.demo.order.infra.dto;



import java.util.List;

public record OrderInputDto(
    List<OrderItemInputDto> items,
    String paymentToken,
    String couponCode
) {
}
