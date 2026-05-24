package com.example.demo.order.infra.adapters.mappers;


import com.example.demo.order.domain.Order;
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

}
