package com.example.demo.shipping.ports.in;

public interface ShippingServicePort {
    void shipOrder(Integer userId, Integer orderId);
}
