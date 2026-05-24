package com.example.demo.payment.application;

import com.example.demo.common.events.OrderPayedEvent;
import com.example.demo.common.events.OrderPaymentFailedEvent;
import com.example.demo.common.data.OrderDetails;
import com.example.demo.payment.ports.in.PaymentServicePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements PaymentServicePort {

    private final ApplicationEventPublisher publisher;

    public PaymentService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void confirmPayment(Integer userId, OrderDetails orderDetails) {
        // handle payment
        if(orderDetails.paymentToken().isEmpty()) {
            publisher.publishEvent(new OrderPaymentFailedEvent(orderDetails, "Payment token is empty"));
            return;
        }
        publisher.publishEvent(new OrderPayedEvent(orderDetails.orderId(),userId));
    }


}
