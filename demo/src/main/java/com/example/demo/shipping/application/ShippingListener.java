package com.example.demo.shipping.application;


import com.example.demo.common.enums.GeneratedId;
import com.example.demo.common.events.NotificationEvent;
import com.example.demo.common.events.OrderPayedEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.common.events.data.OrderShippingData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ShippingListener {

    private ApplicationEventPublisher publisher;



    public ShippingListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @ApplicationModuleListener
    public void handle(OrderPayedEvent event){
        OrderShippingData orderShipping = event.getData().shipping();
            // save customer shipping info
        UUID shippingId = UUID.randomUUID();
        event.getData().generatedIds().put(GeneratedId.LOCATION_ID, shippingId);
            publisher.publishEvent(new NotificationEvent(UUID.randomUUID(),"adnan", "Order Shipped", "Order Shipped"));
            publisher.publishEvent(new OrderShippedEvent(event.getData()));
    }
}
