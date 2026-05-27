package com.example.demo.shipping.application;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.shipping.ports.in.ShippingServicePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ShippingService implements ShippingServicePort {

    private ApplicationEventPublisher publisher;

    public ShippingService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void shipOrder(OrderDetails orderDetails) {
        publisher.publishEvent(new OrderShippedEvent(orderDetails));
    }
}
