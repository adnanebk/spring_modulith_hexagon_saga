package com.example.demo.order.application;


import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.common.models.OrderInput;
import com.example.demo.order.domain.models.OrderOutput;
import com.example.demo.order.domain.models.OrderStatus;
import com.example.demo.order.domain.ports.out.OrderRepoPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepoPort orderRepoPort;
    private final ApplicationEventPublisher publisher;

    public OrderService(OrderRepoPort orderRepoPort, ApplicationEventPublisher publisher) {
        this.orderRepoPort = orderRepoPort;
        this.publisher = publisher;
    }

    @Transactional
    public OrderOutput placeOrder(OrderInput order) {
        OrderOutput orderOutput = orderRepoPort.save(order);
        orderOutput.setStatus(OrderStatus.PENDING);
        order.setId(orderOutput.getId());
        publisher.publishEvent(new OrderPlacedEvent(order));
        return orderOutput;
    }
}
