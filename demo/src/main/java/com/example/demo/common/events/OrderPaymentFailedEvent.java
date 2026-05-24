package com.example.demo.common.events;


import com.example.demo.common.data.OrderDetails;

public record OrderPaymentFailedEvent(OrderDetails orderDetails,String message) {
}
