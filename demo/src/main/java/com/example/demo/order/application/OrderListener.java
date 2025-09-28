package com.example.demo.order.application;

import com.example.demo.common.enums.OrderStatus;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.common.eventsdata.OrderEventData;
import com.example.demo.order.domain.ports.out.OrderRepoPort;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    private final OrderRepoPort orderRepoPort;

    public OrderListener(OrderRepoPort orderRepoPort) {
        this.orderRepoPort = orderRepoPort;
    }

    @ApplicationModuleListener
    public void handle(OrderCanceledEvent event){
        OrderEventData order = event.getOrder();
        orderRepoPort.update(order.getId(), OrderStatus.CANCELLED, order.getPaymentId(), order.getShippingId());


    }

    @ApplicationModuleListener
    public void handle(OrderShippedEvent event){
        OrderEventData order = event.getOrder();
        orderRepoPort.update(order.getId(), OrderStatus.COMPLETED, order.getPaymentId(), order.getShippingId());
    }
}
