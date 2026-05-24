package com.example.demo.order.infra.adapters.api;

import com.example.demo.order.domain.OrderItem;
import com.example.demo.order.infra.adapters.mappers.OrderMapper;
import com.example.demo.order.infra.dto.OrderInputDto;
import com.example.demo.order.ports.in.OrderServicePort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/orders")
public class OrderController {

    private OrderServicePort orderService;
    private OrderMapper orderMapper;

    @PostMapping
    public Integer placeOrder(@RequestBody OrderInputDto orderInputDto) {
        List<OrderItem> orderItems = orderInputDto.items().stream()
                .map(i -> new OrderItem(i.productId(), i.quantity())).toList();
        return orderService.placeOrder(orderInputDto.userId(), orderItems,orderInputDto.paymentToken());
    }
}
