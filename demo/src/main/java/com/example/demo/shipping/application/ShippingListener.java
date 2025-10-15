package com.example.demo.shipping.application;


import com.example.demo.common.enums.GeneratedId;
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

    private NotificationService notificationService;


    public ShippingListener(ApplicationEventPublisher publisher, NotificationService notificationService) {
        this.publisher = publisher;
        this.notificationService = notificationService;
    }

    @ApplicationModuleListener
    public void handle(OrderPayedEvent event){
        OrderShippingData orderShipping = event.getData().shipping();
            // save customer shipping info
            event.getData().generatedIds().put(GeneratedId.LOCATION_ID, UUID.randomUUID());
            notificationService.sendMessage(event.getData().id(),"adnan", "Order Shipped", "Order Shipped");
            publisher.publishEvent(new OrderShippedEvent(event.getData()));
    }
}
