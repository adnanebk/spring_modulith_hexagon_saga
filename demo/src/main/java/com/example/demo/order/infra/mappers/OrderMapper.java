package com.example.demo.order.infra.mappers;


import com.example.demo.order.domain.models.OrderInput;
import com.example.demo.order.domain.models.OrderOutput;
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

    public OrderEntity toEntity(OrderOutput order){
       OrderEntity orderEntity = new OrderEntity();
       BeanUtils.copyProperties(order, orderEntity);
       return orderEntity;
   }

   public OrderOutput toModel(OrderEntity orderEntity){
       OrderOutput order = new OrderOutput();
       BeanUtils.copyProperties(orderEntity, order);
       order.setItems(orderEntity.getItems().stream().map(itemMapper::toModel).collect(Collectors.toList()));
       return order;
   }

    public OrderEntity toEntity(OrderInput order) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);
        orderEntity.setItems(order.getItems().stream().map(itemMapper::toEntity).collect(Collectors.toList()));
        return orderEntity;
    }
}
