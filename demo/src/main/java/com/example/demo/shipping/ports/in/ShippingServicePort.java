package com.example.demo.shipping.ports.in;

import com.example.demo.common.data.OrderDetails;

public interface ShippingServicePort {
    void shipOrder(OrderDetails orderDetails);
}
