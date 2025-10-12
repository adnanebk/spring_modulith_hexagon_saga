package com.example.demo.order.application;


import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.common.models.Order;
import com.example.demo.order.domain.models.OrderInput;
import com.example.demo.common.enums.OrderStatus;
import com.example.demo.order.ports.in.OrderServicePort;
import com.example.demo.order.ports.out.OrderRepoPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService implements OrderServicePort {

    private final OrderRepoPort orderRepoPort;
    private final ApplicationEventPublisher publisher;

    public OrderService(OrderRepoPort orderRepoPort, ApplicationEventPublisher publisher) {
        this.orderRepoPort = orderRepoPort;
        this.publisher = publisher;
    }

    @Transactional
    @Override
    public Integer placeOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        Integer orderId = orderRepoPort.create(new OrderInput(order.getStatus(), order.getTotal(), order.getItems()));
        order.setId(orderId);
        publisher.publishEvent(new OrderPlacedEvent(order));
        return orderId;
    }
}
