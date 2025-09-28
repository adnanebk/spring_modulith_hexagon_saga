package com.example.demo.shipping.application;


import com.example.demo.common.events.OrderPayedEvent;
import com.example.demo.common.events.OrderShippedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingListener {

    private ApplicationEventPublisher publisher;

    public ShippingListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void handle(OrderPayedEvent event){
        if(event.getOrder().getShippingId() ==null)
        // save customer shipping info
           event.getOrder().setShippingId(1);
        publisher.publishEvent(new OrderShippedEvent(event.getOrder()));
    }
}
