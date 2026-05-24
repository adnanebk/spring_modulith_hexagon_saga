package com.example.demo.payment.ports.in;

import com.example.demo.common.data.OrderDetails;

public interface PaymentServicePort {
    void confirmPayment(Integer userId, OrderDetails orderDetails);
}
