package com.example.demo.order.infra.adapters.api;

import com.example.demo.common.data.OrderedItem;
import com.example.demo.order.infra.adapters.mappers.OrderMapper;
import com.example.demo.order.infra.dto.OrderInputDto;
import com.example.demo.order.ports.in.OrderRequest;
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
        List<OrderedItem> orderItems = orderInputDto.items().stream()
                .map(item -> new OrderedItem(item.productId(), item.quantity())).toList();
        return orderService.placeOrder(new OrderRequest(orderInputDto.userId(), orderItems, orderInputDto.paymentToken(), orderInputDto.couponCode()));
    }
}
