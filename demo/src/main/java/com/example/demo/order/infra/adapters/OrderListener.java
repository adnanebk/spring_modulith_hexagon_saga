package com.example.demo.order.infra.adapters;

import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.common.events.OrderStockFailedEvent;
import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.ports.in.OrderServicePort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class OrderListener {

    private final OrderServicePort orderService;

    public OrderListener(OrderServicePort orderService) {
        this.orderService = orderService;
    }


    @ApplicationModuleListener
    public void handle(OrderStockFailedEvent event){
        orderService.cancelOrder(event.orderId(), event.message());
    }


    @ApplicationModuleListener
    public void handle(OrderShippedEvent event){
        orderService.completeOrder(event.orderDetails().orderId(),event.orderDetails().userId());
    }


}
