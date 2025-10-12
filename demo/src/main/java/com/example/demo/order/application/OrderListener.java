package com.example.demo.order.application;

import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.common.models.OrderInput;
import com.example.demo.order.domain.models.OrderStatus;
import com.example.demo.order.ports.out.OrderRepoPort;
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
        OrderInput order = event.getOrder();
        orderRepoPort.update(order.getId(), OrderStatus.CANCELLED, order.getPaymentInfo().getId(), order.getShipping().getId());


    }

    @ApplicationModuleListener
    public void handle(OrderShippedEvent event){
        OrderInput order = event.getOrder();
        orderRepoPort.update(order.getId(), OrderStatus.COMPLETED,order.getPaymentInfo().getId(), order.getShipping().getId());
    }
}
