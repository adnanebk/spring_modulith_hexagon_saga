package com.example.demo.shipping.application;

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
    public void shipOrder(Integer userId, Integer orderId) {
        publisher.publishEvent(new OrderShippedEvent(orderId, userId));
    }
}
