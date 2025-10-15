package com.example.demo.order.application;

import com.example.demo.common.enums.GeneratedId;
import com.example.demo.common.enums.OrderStatus;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.common.events.data.OrderData;
import com.example.demo.order.ports.out.OrderRepoPort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class OrderListener {

    private final OrderRepoPort orderRepoPort;

    public OrderListener(OrderRepoPort orderRepoPort) {
        this.orderRepoPort = orderRepoPort;
    }

    @ApplicationModuleListener
    public void handle(OrderCanceledEvent event){
        OrderData order = event.getData();
        orderRepoPort.updateStatus(order.id(), OrderStatus.CANCELLED);

    }


    @ApplicationModuleListener
    public void handle(OrderShippedEvent event){
        OrderData order = event.getOrder();
        Map<GeneratedId, UUID> generatedIds = order.generatedIds();
        orderRepoPort.update(order.id(), OrderStatus.COMPLETED,generatedIds.get(GeneratedId.LOCATION_ID),generatedIds.get(GeneratedId.PAYMENT_ID));
    }

}
