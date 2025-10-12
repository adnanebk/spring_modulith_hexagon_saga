package com.example.demo.order.infra.adapters.api;

import com.example.demo.order.infra.adapters.mappers.OrderMapper;
import com.example.demo.order.infra.dto.OrderInputDto;
import com.example.demo.order.ports.in.OrderServicePort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/orders")
public class OrderController {

    private OrderServicePort orderService;
    private OrderMapper orderMapper;

    @PostMapping
    public Integer placeOrder(@RequestBody OrderInputDto orderInputDto) {
        return orderService.placeOrder(orderMapper.toDomain(orderInputDto));
    }
}
