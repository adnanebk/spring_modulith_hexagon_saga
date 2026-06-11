package com.example.demo.order.infra.adapters.mappers;


import com.example.demo.order.domain.Order;
import com.example.demo.order.infra.dto.OrderDto;
import com.example.demo.order.infra.entities.OrderEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private ItemMapper itemMapper;

    public OrderMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public OrderEntity toEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);
        orderEntity.setItems(order.getItems().stream().map(itemMapper::toEntity).collect(Collectors.toList()));
        return orderEntity;
    }

    public Order toModel(OrderEntity orderEntity) {
        Order order = new Order();
        BeanUtils.copyProperties(orderEntity, order);
        order.setItems(orderEntity.getItems().stream().map(itemMapper::toOrderedItem).collect(Collectors.toList()));
        return order;
    }

    public OrderDto toDto(Order order) {
        // todo reafactor the mapper
        return new OrderDto(order.getId(), order.getTotalPrice(), order.getTotalBeforeDiscount(), order.getCouponCode(),
                order.getItems().stream().map(itemMapper::toDto).collect(Collectors.toList()), order.getStatus());
    }
}
