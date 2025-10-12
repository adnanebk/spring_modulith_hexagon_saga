package com.example.demo.order.infra.dto;


import com.example.demo.common.enums.PaymentType;

import java.util.List;

public record OrderInputDto(
    List<OrderItemInputDto> items,
    Double total,
    String customerName,
    String customerEmail,
    String customerAdress,
    String customerPhone,
    PaymentType paymentType
) {
}
