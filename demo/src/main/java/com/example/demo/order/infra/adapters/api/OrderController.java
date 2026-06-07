package com.example.demo.order.infra.adapters.api;

import com.example.demo.common.data.OrderedItem;
import com.example.demo.order.domain.Order;
import com.example.demo.order.infra.adapters.mappers.OrderMapper;
import com.example.demo.order.infra.dto.OrderDto;
import com.example.demo.order.infra.dto.OrderInputDto;
import com.example.demo.order.ports.in.OrderRequest;
import com.example.demo.order.ports.in.OrderServicePort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private OrderServicePort orderService;
    private OrderMapper orderMapper;

    public OrderController(OrderServicePort orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public OrderDto placeOrder(@RequestBody OrderInputDto orderInputDto) {
        Integer userId = 1; // TODO: get user id from security context
        List<OrderedItem> orderItems = orderInputDto.items().stream()
                .map(item -> new OrderedItem(item.productId(), item.quantity())).toList();
        Order order = orderService.placeOrder(new OrderRequest(userId, orderItems, orderInputDto.paymentToken(), orderInputDto.couponCode()));
     return  orderMapper.toDto(order);
    }


    @GetMapping
    public List<OrderDto> getOrder() {
        Integer userId = 1; // TODO: get user id from security context
        return orderService.getOrderByUserId(userId).stream().map(orderMapper::toDto).toList();
    }
}
