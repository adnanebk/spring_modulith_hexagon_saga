package com.example.demo.shipping.application;


import com.example.demo.common.events.OrderPayedEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.common.models.OrderShipping;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

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
        OrderShipping orderShipping = event.getOrder().getShipping();
            // save customer shipping info
            orderShipping.setId(1);
            notificationService.sendMessage(event.getOrder().getId(),"adnan", "Order Shipped", "Order Shipped");
            publisher.publishEvent(new OrderShippedEvent(event.getOrder()));
    }
}
